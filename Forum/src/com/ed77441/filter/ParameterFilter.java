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

import com.ed77441.utils.ParameterConfig;
import com.ed77441.utils.ParameterManager;

/**
 * Servlet Filter implementation class UrlFilter
 */
@WebFilter(filterName="parameter", urlPatterns= "/*", asyncSupported = true)
public class ParameterFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String method = req.getMethod();
		String key = req.getHttpServletMapping().getServletName();
		ParameterConfig config = (ParameterConfig) req.getServletContext().getAttribute(key);
		boolean valid = false;

		if (config == null) {
			valid = true; /*skip processing since there is no parameter config*/
		}
		else {
			ParameterManager manager = new ParameterManager(config);
			
			if (method.equalsIgnoreCase("GET")) {
				valid = manager.processGetParameters(req);
			}
			else if (method.equalsIgnoreCase("POST")) {
				valid = manager.processPostParameters(req);
			}
			else if (method.equalsIgnoreCase("PUT")) {
				valid = manager.processPutParameters(req);
			}
			else if (method.equalsIgnoreCase("DELETE")){
				valid = manager.processDeleteParameters(req);
			}
		}
				
		if (valid) {
			chain.doFilter(request, response);
		}
		else {
			if (method.equalsIgnoreCase("GET")) {
				resp.sendRedirect("/error");
			}
			else if (method.equalsIgnoreCase("POST")) {
				resp.sendRedirect("/error");
			}
			else {
				resp.getWriter().write("/error");
			}
		}
	}
}
