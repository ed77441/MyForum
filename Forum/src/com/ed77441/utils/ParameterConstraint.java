package com.ed77441.utils;

public class ParameterConstraint {
	String name;
	String type;
	boolean required;
	Object defaultValue;
	
	private ParameterConstraint(String name, String type, boolean required, Object defaultValue) {
		this.name = name;
		this.type = type;
		this.required = required;
		this.defaultValue = defaultValue;
	}
	
	public static ParameterConstraint optional(String name, String type, Object defaultValue) {
		return new ParameterConstraint(name, type, false, defaultValue);
	}
	
	public static ParameterConstraint required(String name, String type) {
		return new ParameterConstraint(name, type, true, null);
	}
}
