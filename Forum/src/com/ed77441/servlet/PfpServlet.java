package com.ed77441.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.ed77441.service.UserService;

@MultipartConfig
@WebServlet("/pfp-change")
public class PfpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	UserService userService = new UserService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentUser = request.getSession().getAttribute("user").toString();
		String webroot = request.getSession().getServletContext().getRealPath("/");
		Part filePart = request.getPart("image") ;

		boolean success = userService.changeProfilePic(webroot, currentUser, filePart, request.getSession());
		
		if (success) {
			response.sendRedirect("/profile?name=" + currentUser);
		}
		else {
			response.sendRedirect("/error");
		}
	}
}
