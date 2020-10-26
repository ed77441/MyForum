package com.ed77441.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ed77441.service.UserService;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	UserService userService = new UserService();
	
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setPostConstraints(
			ParameterConstraint.required("user", "str"),
			ParameterConstraint.required("pass", "str"),
			ParameterConstraint.required("birthday", "str"),
			ParameterConstraint.required("email", "str"),
			ParameterConstraint.required("phone", "str")
		);
		
		getServletContext().setAttribute(getServletName(), config);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(request, response);
		HttpSession session = request.getSession();
		if (session.getAttribute("tmp") != null) {
			session.removeAttribute("tmp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getAttribute("user").toString();
		String pass =  request.getAttribute("pass").toString();
		String birthday = request.getAttribute("birthday").toString();
		String email = request.getAttribute("email").toString();
		String phone = request.getAttribute("phone").toString();
		
		int status = userService.register(user, pass, birthday, email, phone);	
		
		switch (status) {
			case UserService.CREATED:
				response.sendRedirect("/login");
				break;
			case UserService.DUPLICATED:
				Map<String, String> tmp = new HashMap<String, String>();
				tmp.put("user", user);
				tmp.put("pass", pass);
				tmp.put("birthday", birthday);
				tmp.put("email", email);
				tmp.put("phone", phone);
				
				request.getSession().setAttribute("tmp", tmp);
				response.sendRedirect("/signup");
				break;
			case UserService.INVALID:
				response.sendRedirect("/error");
				break;
		}
	}

}
