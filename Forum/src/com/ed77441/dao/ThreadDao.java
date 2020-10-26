package com.ed77441.dao;

import java.util.List;
import java.util.Optional;

import com.ed77441.model.Thread;

public interface ThreadDao {
	
	public List<Thread> getAllThreads(int rowStart, int rowCount, boolean orderByLastUpdate);
	public List<Thread> getThreadsByCaption
			(String caption, int rowStart, int rowCount, boolean orderByLastUpdate);
	public List<Thread> getThreadsByGenre
			(String genre, int rowStart, int rowCount, boolean orderByLastUpdate);
	public void updateTimeStamp(int id);
	
	public int addThread(Thread thread);
	public Optional<Thread> getThread(int id);
	public void setThread(Thread thread);
	public void deleteThread(int id);
	
	public void increaseCommentCount(int id);
	public void decreaseCommentCount(int id);
	public int getAllRowCount();
	public int getRowCountByGenre(String genre);
	public int getRowCountByCaption(String caption);
}
