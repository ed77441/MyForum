package com.ed77441.servlet;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.service.NotificationService;
import com.ed77441.utils.ClientHandler;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;


@WebServlet(urlPatterns = "/notification", asyncSupported = true)
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final long timeout = 10 * 60 * 1000; /*10 min*/
	NotificationService notificationService = new NotificationService();
	
	public void init() {		
		ParameterConfig config = new ParameterConfig();
		
		ParameterConstraint idConstraints = ParameterConstraint.required("id", "str");
		
		config.setGetConstraints(
			idConstraints,
			ParameterConstraint.required("status", "int")
		);
	
		
		config.setDeleteConstraints(
			idConstraints
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String id = request.getAttribute("id").toString();
		String user = request.getSession().getAttribute("user").toString();
		int status = (int) request.getAttribute("status");
		
		int lastestStatus = notificationService.getNotificationStatus(user);
				
		if (lastestStatus != status) {
			/*return immediately*/
			String msg = notificationService.getNotificationResponse(user);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(msg);
		}
		else {
			/*suspend*/
			AsyncContext context = request.startAsync();
			ClientHandler.getInstance().register(user, id, context, timeout);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getSession().getAttribute("user").toString();
		notificationService.markNotificationRead(user);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getAttribute("id").toString();
		String user = request.getSession().getAttribute("user").toString();
		
		ClientHandler.getInstance().unregister(user, id);
	}
}
