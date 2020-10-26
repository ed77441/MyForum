package com.ed77441.servlet;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.model.User;
import com.ed77441.model.Comment;
import com.ed77441.service.PostService;
import com.ed77441.utils.Pagination;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;
import com.ed77441.model.Thread;

@WebServlet("/threads")
public class ThreadsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	PostService postService = new PostService(); 

	@Override
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setGetConstraints(
			ParameterConstraint.required("id", "int"),
			ParameterConstraint.optional("page", "int", 1)
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        int id = (int) request.getAttribute("id");
        int page = (int) request.getAttribute("page");
        
		Optional<Thread> thread = postService.requestThread(id, null);
		
		if (thread.isPresent()) {
			Thread targetThread = thread.get();
			Pagination pagination = new Pagination(page, 
						Pagination.calculatePageCount(targetThread.getCommentCount(), 10));
			List<Map.Entry<User, Comment>> contents = postService.getCommentRows(id, page);
			
			request.setAttribute("thread", targetThread);
	        request.setAttribute("rows",  contents);
			request.setAttribute("currentUrl", "/threads?id=" + id);
	        request.setAttribute("pagination", pagination);
	        request.setAttribute("commentURL", "/comment?tid=" + id);
	        request.getRequestDispatcher("/WEB-INF/view/threads.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/error");
		}
	}
}
