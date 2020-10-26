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

@WebServlet("/profile-info")
public class ProfileInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService userService = new UserService();

	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setPostConstraints(
			ParameterConstraint.required("birthday", "str"),
			ParameterConstraint.required("email", "str"),
			ParameterConstraint.required("phone", "str"),
			ParameterConstraint.required("intro", "str")
		);
		
		getServletContext().setAttribute(getServletName(), config);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentUser = request.getSession().getAttribute("user").toString();
		Optional<User> user = userService.getUser(currentUser);
		request.setAttribute("user", user.get());
		request.getRequestDispatcher("/WEB-INF/view/profile-info.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentUser = request.getSession().getAttribute("user").toString();		
		
		String birthday = request.getAttribute("birthday").toString();
		String email = request.getAttribute("email").toString();
		String phone = request.getAttribute("phone").toString();
		String intro = request.getAttribute("intro").toString();

		boolean success = userService.editProfileInfo(currentUser, birthday, email, phone, intro);
								
		if (success) {
			response.sendRedirect("/profile?name=" + currentUser);
		}
		else {
			response.sendRedirect("/error");
		}	
	}
}
