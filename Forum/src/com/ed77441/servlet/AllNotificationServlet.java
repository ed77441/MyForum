package com.ed77441.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.model.Comment;
import com.ed77441.model.Thread;
import com.ed77441.service.NotificationService;
import com.ed77441.utils.Pagination;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

/**
 * Servlet implementation class NotificationController
 */
@WebServlet("/all-notification")
public class AllNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	NotificationService notificationService = new NotificationService();

	@Override
	public void init() {
		ParameterConfig config = new ParameterConfig();
		config.setGetConstraints(ParameterConstraint.optional("page", "int", 1));
		getServletContext().setAttribute(getServletName(), config);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getSession().getAttribute("user").toString();
		int page = (int) request.getAttribute("page");
		
		notificationService.markNotificationRead(user);
		List<Map.Entry<Thread, Comment>> notifications = notificationService.getNotificationRows(user, page);
		List<String> locations =  notificationService.getCommentLocations(user, page);
		Pagination pagination = new Pagination(page, 
				Pagination.calculatePageCount(notificationService.getNotificationCount(user), 15));
		request.setAttribute("notifications", notifications);
		request.setAttribute("locations", locations);
		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("/WEB-INF/view/notification.jsp").forward(request, response);
	}

}
