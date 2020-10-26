package com.ed77441.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.model.Thread;
import com.ed77441.service.ForumService;
import com.ed77441.utils.Pagination;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ForumService forumService = new ForumService(); 
	
    public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setGetConstraints(
			ParameterConstraint.required("q", "str"),
			ParameterConstraint.optional("page", "int", 1),
			ParameterConstraint.optional("orderBy", "str", "更新時間")
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String q = request.getAttribute("q").toString();
		int page = (int) request.getAttribute("page");
		String orderBy = request.getAttribute("orderBy").toString();
		boolean orderByLastUpdate = orderBy.equals("更新時間");
		
		if ((orderBy.equals("發文時間") || orderByLastUpdate)) {
			String orderParam = orderByLastUpdate ? "" : "&orderBy=發文時間";
			String currentUrl = "/search?q=" + q + orderParam;
	
			List<Thread> threads = forumService.getThreadsByCaption(q, page, orderByLastUpdate);
			Pagination pagination = new Pagination(page, 
					Pagination.calculatePageCount(forumService.getRowCoutByCaption(q), 15));
			
			request.setAttribute("page", "search");
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
