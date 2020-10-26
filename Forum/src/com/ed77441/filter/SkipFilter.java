package com.ed77441.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName="skip", urlPatterns= {"/login", "/signup"})
public class SkipFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (req.getSession().getAttribute("user") == null) {
			chain.doFilter(request, response);
		}
		else {
			resp.sendRedirect("/home?genre=" + URLEncoder.encode("全部主題", "UTF-8")); /*to lobby*/
		}
	}
}
