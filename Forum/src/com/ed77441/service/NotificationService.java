package com.ed77441.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ed77441.dao.CommentDao;
import com.ed77441.dao.CommentDaoImpl;
import com.ed77441.dao.NotificationDao;
import com.ed77441.dao.NotificationDaoImpl;
import com.ed77441.dao.ThreadDao;
import com.ed77441.dao.ThreadDaoImpl;
import com.ed77441.model.Comment;
import com.ed77441.model.Notification;
import com.ed77441.model.Thread;

public class NotificationService {
	
	ThreadDao threadDao;
	CommentDao commentDao;
	NotificationDao notificationDao;
	
	public NotificationService() {
		this(new ThreadDaoImpl(), new CommentDaoImpl(), new NotificationDaoImpl());
	}
	
	public NotificationService(ThreadDao threadDao, CommentDao commentDao, NotificationDao notificationDao) {
		this.threadDao = threadDao;
		this.commentDao = commentDao;
		this.notificationDao = notificationDao;
	}
	
	public int getNotificationCount(String user) {
		return notificationDao.getNotificationCount(user);
	}
	
	public List<Map.Entry<Thread, Comment>> getNotificationRows(String user, int page) {
		final int fetchCount = 15;
		int offset = (page - 1) * fetchCount;
		List <Notification> notifications = notificationDao.getNotificationByUser(user, offset, fetchCount);
		List <Map.Entry<Thread, Comment>> notificationRows = new ArrayList<>();
		
		for (Notification notification: notifications) {
			Thread thread = threadDao.getThread(notification.getThreadID()).get();
			Comment comment = commentDao.getComment(notification.getCommentID()).get();
			
			notificationRows.add(Map.entry(thread, comment));
		}
		
		
		return notificationRows;
	}
	
	public List <String> getCommentLocations(String user, int page) {
		final int fetchCount = 15;
		int offset = (page - 1) * fetchCount;
		List <Notification> notifications = notificationDao.getNotificationByUser(user, offset, fetchCount);
		List <String> locations = new ArrayList<>();
		for (Notification notification: notifications) {
			Thread thread = threadDao.getThread(notification.getThreadID()).get();
			Comment comment = commentDao.getComment(notification.getCommentID()).get();
			locations.add(this.getCommentLocation(thread.getId(), comment.getId()));
		}
		
		return locations;
	}
	
	public String getNotificationResponse(String user) {
		JSONObject json = new JSONObject();
		int unseenCount = notificationDao.countUnseenNotification(user);
		List<Notification> notifications = 
				notificationDao.getNotificationByUser(user, 0, 3);
		int status = -1;
		
		if (notifications.size() > 0) {
			status = notifications.get(0).getId();
		}
		
		json.put("status", status);
		json.put("unseen", unseenCount);
		
		JSONArray array = new JSONArray();
		
		for (Notification notification: notifications) {
			JSONObject entry = new JSONObject();
			int tid = notification.getThreadID();
			int cid = notification.getCommentID();
			Thread thread = threadDao.getThread(tid).get();
			Comment comment = commentDao.getComment(cid).get();
			entry.put("commentLocation", getCommentLocation(tid, cid));
			entry.put("caption", thread.getCaption());
			entry.put("comment", comment.getContent());
			entry.put("replyer", comment.getUser());
			array.put(entry);
		}
		
		json.put("notifications", array);
		return json.toString();
	}
	
	public void addNewNotification(int tid, int cid, String user) {
		Notification notification = new Notification();
		notification.setThreadID(tid);
		notification.setCommentID(cid);
		notification.setUser(user);
		notificationDao.addNotification(notification);
	}
	
	public int getNotificationStatus(String user) {
		int status = -1;
		List<Notification> notifications = 
				notificationDao.getNotificationByUser(user, 0, 1);
		if (notifications.size() > 0) {
			status = notifications.get(0).getId();
		}
				
		return status;
	}
	
	public void markNotificationRead(String user) {
		notificationDao.setNotificationSeen(user);
	}
	
	
	private int getCommentIndex(int tid, int cid) {
		List<Comment> comments = commentDao.getCommentsByThreadID(tid, 0, Integer.MAX_VALUE);
		
		for (int i = 0; i < comments.size(); ++i) {
			if (comments.get(i).getId() == cid) {
				return i;
			}
		}
		return -1;
	}
	
	private String getCommentLocation(int tid, int cid) {
		int rowPosition = getCommentIndex(tid, cid);
    	int index = rowPosition + 1;
    	int page = rowPosition / 10 + 1;
    	String pageParam = page == 1 ? "" : "&page=" + page;
    	/*locate comment*/
		return "/threads?id=" + tid + pageParam + "#comment-" + index;
	}
}