package com.ed77441.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.service.ForumService;
import com.ed77441.utils.GenreConstants;
import com.ed77441.utils.Pagination;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;
import com.ed77441.model.Thread;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ForumService forumService = new ForumService(); 
	
	@Override 
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setGetConstraints(
			ParameterConstraint.required("genre", "str"),
			ParameterConstraint.optional("page", "int", 1),
			ParameterConstraint.optional("orderBy", "str", "更新時間")
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String genre = request.getAttribute("genre").toString();
		int page = (int) request.getAttribute("page");
		String orderBy = request.getAttribute("orderBy").toString();
						
		boolean orderByLastUpdate = orderBy.equals("更新時間");
				
		if ((genre.equals("全部主題") || GenreConstants.getGenres().contains(genre)) 
				&& (orderBy.equals("發文時間") || orderByLastUpdate)) {
			
			String orderParam = orderByLastUpdate ? "" : "&orderBy=發文時間";
			String currentUrl = "/home?genre=" + genre + orderParam;
	
			List<Thread> threads = forumService.getThreadsByGenre(genre, page, orderByLastUpdate);
			Pagination pagination = new Pagination(page, 
					Pagination.calculatePageCount(forumService.getRowCountByGenre(genre), 15));
			
			request.setAttribute("page", "home");
			request.setAttribute("threads", threads);
			request.setAttribute("currentUrl", currentUrl);
			request.setAttribute("pagination", pagination);
			request.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/error");
		}
	}
}
