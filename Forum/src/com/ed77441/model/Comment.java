package com.ed77441.model;

import java.sql.Timestamp;

public class Comment {
	int id;
	String content;
	Timestamp lastUpdate;
	int threadID;
	String user;
	
	public Comment() {
		content = "";
	}
	
	public Comment(int id, String content, Timestamp lastUpdate, int threadID, String user) {
		this.id = id;
		this.content = content;
		this.lastUpdate = lastUpdate;
		this.threadID = threadID;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getThreadID() {
		return threadID;
	}

	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
