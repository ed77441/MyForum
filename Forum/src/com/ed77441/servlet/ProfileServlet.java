package com.ed77441.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ed77441.model.User;
import com.ed77441.service.UserService;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService userService = new UserService();
	
	@Override
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setGetConstraints(
			ParameterConstraint.required("name", "str")
		);
		
		getServletContext().setAttribute(getServletName(), config);		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getAttribute("name").toString();
        Optional<User> user = userService.getUser(name);
        
		if (user.isPresent()) {
			request.setAttribute("user", user.get());
			request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/error");
		}		
	}
}
