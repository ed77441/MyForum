package com.ed77441.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ed77441.model.Notification;
import com.ed77441.utils.SimpleJDBCWrapper;

public class NotificationDaoImpl implements NotificationDao {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/Forum?useSSL=false";

    static final String USER = "root";
    static final String PASS = "1234";
    
	@Override
	public List<Notification> getNotificationByUser(String user, int rowStart, int rowCount) {
		List<Notification> notifications = new ArrayList<>();
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT * FROM Notification WHERE User = ? ORDER BY ID DESC LIMIT ? OFFSET ?";
			Object [] parameters = new Object[] 
					{user, rowCount, rowStart}; 
			ResultSet resultSet = jdbc.read(query, parameters);
			
			while (resultSet.next()) {
				notifications.add(new Notification(
					resultSet.getInt(1),
					resultSet.getInt(2),
					resultSet.getInt(3),
					resultSet.getBoolean(4),
					resultSet.getString(5)
				));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return notifications;
	}

	@Override
	public void addNotification(Notification notification) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "INSERT INTO Notification (ThreadID, CommentID, User) VALUES (?, ?, ?)";
			Object [] parameters = new Object[] 
					{notification.getThreadID(), notification.getCommentID(), notification.getUser()}; 
			jdbc.update(query, parameters);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public int countUnseenNotification(String user) {
		int count = 0;
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT COUNT(*) FROM Notification WHERE User = ? AND Seen = 0";
			Object [] parameters = new Object[] 
					{user}; 
			ResultSet resultSet = jdbc.read(query, parameters);
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}				
		
		return count;
	}

	@Override
	public void setNotificationSeen(String user) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "UPDATE Notification SET Seen = 1 WHERE User = ?";
			Object [] parameters = new Object[] 
					{user}; 
			jdbc.update(query, parameters);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}				
	}

	@Override
	public int getNotificationCount(String user) {
		int rowCount = 0;
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT COUNT(*) FROM Notification WHERE User = ?";
			Object [] parameters = new Object[] {user}; 
			ResultSet resultSet = jdbc.read(query, parameters);
			
			if (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return rowCount;
	}
	
}
