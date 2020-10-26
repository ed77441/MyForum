package com.ed77441.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;

public class ClientHandler {
	
	private class MyAsyncListener implements AsyncListener {
		String user, id;
			
		MyAsyncListener(String user, String id) {
			this.user = user;
			this.id = id;
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			terminate();
		}
		
		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			terminate();
		}
		
		@Override
		public void onComplete(AsyncEvent event) throws IOException {}
		
		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {}
		
		private void terminate() {
			Map<String, AsyncContext> contexts = ongoingRequests.get(user);
			synchronized(contexts) {
				if (contexts.containsKey(id)) {
					System.out.println(user + " " + id + " terminate!");

					AsyncContext context = contexts.remove(id);
					ServletResponse resp = context.getResponse();
					try {
						resp.getWriter().write("no data");
					}
					catch(IOException ignored) {}
					context.complete();
				}
			}
		}
	}
	
	private static ClientHandler handler;

	public static ClientHandler getInstance() {
		if (handler == null) {
			handler = new ClientHandler();
		}
		
		return handler;
	}
	
	private final Map<String, Map<String, AsyncContext>> ongoingRequests = new HashMap<>();
	
	private ClientHandler () {}
	
	public void response(String user, String msg) {
		Map<String, AsyncContext> contexts = ongoingRequests.get(user);
		
		if (contexts != null) {
			synchronized(contexts) {
				System.out.println(user + " responsed!");

				for (Map.Entry<String, AsyncContext> entry : contexts.entrySet()) {
					AsyncContext ac = entry.getValue(); 
					ServletResponse resp = ac.getResponse();
					try {
						resp.setContentType("text/html; charset=UTF-8");
						resp.getWriter().write(msg);
		            } 
					catch (IOException ignored) {}
					finally {
						ac.complete();
					}
				}
			
				contexts.clear();
			}
		}
	}
	
	public void register(String user, String id, AsyncContext context, long timeout) {
		context.addListener(new MyAsyncListener(user, id));
		context.setTimeout(timeout);

		synchronized(ongoingRequests) {
			System.out.println(user + " " + id + " created");

			if (ongoingRequests.containsKey(user)) {
				Map<String, AsyncContext> contexts = ongoingRequests.get(user);
				synchronized(contexts) {
					contexts.put(id, context);
				}	
			}
			else {
				Map<String, AsyncContext> contexts = new HashMap<>();
				contexts.put(id, context);
				ongoingRequests.put(user, contexts);
			}
		}
	}
	
	public void unregister(String user, String id) {
		Map<String, AsyncContext> contexts = ongoingRequests.get(user);
		
		if (contexts != null) {
			synchronized(contexts) {
				System.out.println(user + " " + id + " deleted");

				if (contexts.containsKey(id)) {
					AsyncContext ac = contexts.remove(id);
					ac.complete();
				}
			}
		}
	}
}
