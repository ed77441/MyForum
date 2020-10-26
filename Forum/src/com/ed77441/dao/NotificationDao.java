package com.ed77441.dao;

import java.util.List;

import com.ed77441.model.Notification;

public interface NotificationDao {
	public List <Notification> getNotificationByUser(String user, int rowStart, int rowCount);
	public int countUnseenNotification(String user);
	public int getNotificationCount(String user);
	
	public void setNotificationSeen(String user);
	public void addNotification(Notification notification);
}
