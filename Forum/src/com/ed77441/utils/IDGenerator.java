package com.ed77441.utils;

import java.util.Random;

public class IDGenerator {
	
	private static final String charset = 
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private IDGenerator () {}
	
	public static String generate(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		Random rnd = new Random(); 

		for (int i = 0; i < length; ++i) {
			stringBuilder.append(charset.charAt(rnd.nextInt(charset.length())));
		}
		
		return stringBuilder.toString();
	} 
}
