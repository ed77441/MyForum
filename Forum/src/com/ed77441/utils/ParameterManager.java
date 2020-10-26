package com.ed77441.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class ParameterManager {
	
	ParameterConfig config;
	
	public ParameterManager(ParameterConfig config) {
		this.config = config;
	}
	
	private Map<String, String> modifyParameterMap(Map<String, String[]> original) {
		Map<String, String> newMap = new HashMap<>();
		for (Map.Entry<String, String[]> entry : original.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue()[0]);
		}
		return newMap;
	}
	
	private Map<String, String> parseParameterMap(String query) throws UnsupportedEncodingException {
		Map<String, String> parameterMap = new HashMap<>();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        parameterMap.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), 
	        		URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    
	    return parameterMap;
	} 
	
	
	private boolean processParameters(List<ParameterConstraint> constraints,
										Map<String, String> parameters, HttpServletRequest request) {
		
		for (ParameterConstraint constraint : constraints) {
			String parameter = parameters.get(constraint.name);
						
			if (parameter == null) {
				if (constraint.required) {
					return false;
				}
				else {
					request.setAttribute(constraint.name, constraint.defaultValue);
				}
			}
			else {
				Object value = null;
				if (constraint.type.equals("int")) {
					try {
						value = Integer.parseInt(parameter);
					}
					catch(NumberFormatException e) {
						return false;
					}
				}
				else {
					value = parameter;
				}
				request.setAttribute(constraint.name, value);
			}
		}
		
		return true;		
	}
	
	public boolean processGetParameters(HttpServletRequest request) {		
		List<ParameterConstraint> constraints = config.getGetConstraints();
		Map<String, String> parameters = modifyParameterMap(request.getParameterMap());		
		return processParameters(constraints, parameters, request);
	}
	
	public boolean processPostParameters(HttpServletRequest request) {
		List<ParameterConstraint> constraints = config.getPostConstraints();
		Map<String, String> parameters = modifyParameterMap(request.getParameterMap());		
		return processParameters(constraints, parameters, request);
	}
	
	public boolean processPutParameters(HttpServletRequest request)  {
		List<ParameterConstraint> constraints = config.getPutConstraints();
		Map<String, String> parameters = new HashMap<>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			parameters = parseParameterMap(br.readLine());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return processParameters(constraints, parameters, request);
	}
	
	public boolean processDeleteParameters(HttpServletRequest request) {
		List<ParameterConstraint> constraints = config.getDeleteConstraints();
		Map<String, String> parameters = modifyParameterMap(request.getParameterMap());		
		return processParameters(constraints, parameters, request);
	}
}
