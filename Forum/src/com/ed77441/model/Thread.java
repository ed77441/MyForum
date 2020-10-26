package com.ed77441.model;

import java.sql.Timestamp;
import com.ed77441.utils.GenreConstants;

public class Thread {
	int id;
	String caption, genre;
	int commentCount;
	Timestamp startTime, lastUpdate;
	String user;
	
	public Thread() {
		id = -1;
		caption = "";
		genre = GenreConstants.getGenres().get(0);
		user = "";
	};
	
	public Thread(int id, String caption, String genre, int commentCount,
			Timestamp startTime, Timestamp lastUpdate, String user) {
		this.id = id;
		this.caption = caption;
		this.genre = genre;
		this.commentCount = commentCount;
		this.startTime = startTime;
		this.lastUpdate = lastUpdate;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
