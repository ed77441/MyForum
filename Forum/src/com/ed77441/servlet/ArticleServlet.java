package com.ed77441.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.service.PostService;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

import com.ed77441.model.Comment;
import com.ed77441.model.Thread;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PostService postService = new PostService();
	
	@Override 
	public void init() {	
		ParameterConfig config = new ParameterConfig();
		
		ParameterConstraint requiredIDConstraint = 
				ParameterConstraint.required("id", "int");
		ParameterConstraint captionConstraint = 
				ParameterConstraint.required("caption", "str");
		ParameterConstraint genreConstraint = 
				ParameterConstraint.required("genre", "str");
		ParameterConstraint contentConstraint = 
				ParameterConstraint.required("content", "str");
		
		config.setGetConstraints(
			ParameterConstraint.optional("id", "int", -1)
		);
		config.setPostConstraints(
			captionConstraint, genreConstraint, contentConstraint
		);
		config.setPutConstraints(
			requiredIDConstraint,
			captionConstraint, genreConstraint, contentConstraint
		);
		config.setDeleteConstraints(
			requiredIDConstraint
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
        String currentUser = request.getSession().getAttribute("user").toString();
		
		int id = (int) request.getAttribute("id");
       
        boolean isPutMethod = false;
        Thread resultThread =  new Thread();
        Comment resultComment = new Comment();
               
        Optional<Thread> thread = postService.requestThread(id, currentUser);
       
        if (thread.isPresent()) {
        	Comment firstComment = postService.getCommentRelatedWithThread(id);
        	isPutMethod = true;
        	resultThread = thread.get();
        	resultComment = firstComment;
        }
        
    	request.setAttribute("put", isPutMethod);
    	request.setAttribute("thread", resultThread);
    	request.setAttribute("comment", resultComment);
		request.getRequestDispatcher("/WEB-INF/view/article.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentUser = request.getSession().getAttribute("user").toString();
		
		int id;
		String caption = request.getAttribute("caption").toString() ;
		String genre = request.getAttribute("genre").toString();
		String content = request.getAttribute("content").toString();
		
		id = postService.startNewThread(caption, genre, content, currentUser);

		if (id != -1) {
		    response.sendRedirect("/threads?id=" + id);
		}
		else {
			response.sendRedirect("/error");
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentUser = request.getSession().getAttribute("user").toString();
		
		int id = (int) request.getAttribute("id");
		String caption = request.getAttribute("caption").toString() ;
		String genre = request.getAttribute("genre").toString();
		String content = request.getAttribute("content").toString();	
		
		boolean success = postService.editThreadAndRelatedComment(id, currentUser, caption, genre, content);
		
	    if (success) {
	    	response.getWriter().write("/threads?id=" + id);
	    }
	    else {
	    	response.getWriter().write("/error");
	    }
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentUser = request.getSession().getAttribute("user").toString();
		
		int id = (int) request.getAttribute("id") ;
		
		boolean success = postService.removeEntireThread(id, currentUser);
		
		if (success) {
			response.getWriter().write("/home?genre=" + URLEncoder.encode("全部主題", "UTF-8"));
		}
		else {
			response.getWriter().write("/error");
		}
	} 
}
