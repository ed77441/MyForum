package com.ed77441.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(filterName="authentication", 
			urlPatterns= {"/article", "/comment", "/pfp-change", "/profile-info", "/notification", "/all-notification"},
			asyncSupported = true)
public class AuthFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (req.getSession().getAttribute("user") != null) {
			chain.doFilter(request, response);
		}
		else {
			String method = req.getMethod();

			if (method.equalsIgnoreCase("GET") || 
					method.equalsIgnoreCase("POST")) {
				resp.sendRedirect("/login");
			}
			else {
				resp.getWriter().write("/login");
			}
		}
	}
}
