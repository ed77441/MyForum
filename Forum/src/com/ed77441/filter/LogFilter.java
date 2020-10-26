package com.ed77441.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter(filterName="log", 
			urlPatterns={"/home", "/search", "/threads", "/profile"})
public class LogFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String servletPath = req.getServletPath();		
		
		if (session.getAttribute("user") == null &&
				req.getMethod().equalsIgnoreCase("GET")) {
			String queryString = req.getQueryString();
			String fullUrl = servletPath;
			
			if (queryString != null) {
				fullUrl = fullUrl + "?" + queryString;
			}
			
			session.setAttribute("last-browsed", fullUrl);
		}
		
		chain.doFilter(request, response);
	}
}
