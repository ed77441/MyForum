package com.ed77441.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.model.Comment;
import com.ed77441.model.Thread;
import com.ed77441.service.NotificationService;
import com.ed77441.service.PostService;
import com.ed77441.utils.ClientHandler;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	PostService postService = new PostService(); 
	NotificationService notificationService = new NotificationService();
	
	@Override
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		ParameterConstraint tidConstraint =
				ParameterConstraint.required("tid", "int");
		ParameterConstraint cidConstraint =
				ParameterConstraint.required("cid", "int");
		ParameterConstraint commentConstraint =
				ParameterConstraint.required("comment", "str");	
		
		config.setGetConstraints(ParameterConstraint.required("id", "int"));
		config.setPostConstraints(tidConstraint, commentConstraint);
		config.setPutConstraints(tidConstraint, cidConstraint, commentConstraint);
		config.setDeleteConstraints(tidConstraint, cidConstraint);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentUser = request.getSession().getAttribute("user").toString();
		
		int id = (int) request.getAttribute("id");
		
		Optional <Comment> comment = postService.requestComment(id, currentUser);
		
		if (comment.isPresent()) {
			Comment targetComment = comment.get();
			Optional <Thread> thread = postService.requestThread(targetComment.getThreadID(), null);
		
			request.setAttribute("put", true);
			request.setAttribute("thread", thread.get());
			request.setAttribute("comment", targetComment);
			request.getRequestDispatcher("/WEB-INF/view/comment.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/error");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentUser = request.getSession().getAttribute("user").toString();
		
        int tid = (int) request.getAttribute("tid");
        String comment = request.getAttribute("comment").toString();
        
		int cid = postService.postComment(tid, comment, currentUser);
		
		if (cid != -1) {
        	String commentLocation = postService.getCommentLocation(tid, cid);
        	Thread thread = postService.requestThread(tid, null).get();
        	
        	String owner = thread.getUser();
        	
        	if (!currentUser.equals(owner)) {
        		String msg;
        		notificationService.
        			addNewNotification(tid, cid, owner);
        		msg = notificationService.getNotificationResponse(owner);
        		ClientHandler.getInstance().response(owner, msg);
        	}
        	
        	response.sendRedirect(commentLocation);
		}
		else {
			response.sendRedirect("/error");
		}     
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String currentUser = request.getSession().getAttribute("user").toString();

		int tid = (int) request.getAttribute("tid");
		int cid = (int) request.getAttribute("cid");
		String comment = request.getAttribute("comment").toString();
        
        boolean success = postService.editComment(cid, comment, currentUser);
        if (success) {
        	String commentLocation = postService.getCommentLocation(tid, cid);
        	response.getWriter().write(commentLocation);   
        }
        else {
        	response.getWriter().write("/error");
        }
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String currentUser = request.getSession().getAttribute("user").toString();
        
        int tid = (int) request.getAttribute("tid");
		int cid = (int) request.getAttribute("cid");
		
        boolean success = postService.removeComment(cid, currentUser);

		if (success) {
			response.getWriter().write("/threads?id=" + tid);
		}
		else {
        	response.getWriter().write("/error");
		}
	}
}
