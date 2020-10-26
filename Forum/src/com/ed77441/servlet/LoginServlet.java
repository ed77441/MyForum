package com.ed77441.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ed77441.dao.UserDaoImpl;
import com.ed77441.service.UserService;
import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterConstraint;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService(new UserDaoImpl());
	
	@Override
	public void init() {
		ParameterConfig config = new ParameterConfig();
		
		config.setPostConstraints(
			ParameterConstraint.required("user", "str"),
			ParameterConstraint.required("pass", "str")
		);
		
		getServletContext().setAttribute(getServletName(), config);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		HttpSession session = request.getSession();
		if (session.getAttribute("error") != null) {
			session.removeAttribute("error");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getAttribute("user").toString();
		String pass = request.getAttribute("pass").toString();
		HttpSession session = request.getSession();
		
		if (userService.login(session, user, pass)) {
			Object lastBrowsed = session.getAttribute("last-browsed");
			if (lastBrowsed != null) {
				response.sendRedirect(lastBrowsed.toString());
				session.removeAttribute("last-browsed");
			}
			else {
				response.sendRedirect("/home?genre=" + URLEncoder.encode("全部主題", "UTF-8"));
			}
		}
		else {
			session.setAttribute("error", true);
			response.sendRedirect("/login");
		}
	}
}
