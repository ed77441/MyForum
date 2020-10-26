package com.ed77441.model;

public class Notification {
	int id;
	int threadID, commentID;
	boolean seen;
	String user;
	
	public Notification() {}
	
	public Notification(int id, int threadID, int commentID, boolean seen, String user) {
		this.id = id;
		this.threadID = threadID;
		this.commentID = commentID;
		this.seen = seen;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getThreadID() {
		return threadID;
	}
	
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	
	public int getCommentID() {
		return commentID;
	}
	
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	
	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
}
