package com.ed77441.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreConstants {
	
	static final private List<String> listOfGenre = new ArrayList<String>(Arrays.asList(
		    "輕鬆閒聊", "新聞時事", "專業知識",  
		    "人際關係", "職場問題", "求學問題" 
	));
	
	public List<String> getListOfGenre() {
		return listOfGenre;
	}
	
	public static List<String> getGenres() {
		return listOfGenre;
	}
}
