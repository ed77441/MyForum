package com.ed77441.service;

import java.util.List;

import com.ed77441.dao.CommentDao;
import com.ed77441.dao.CommentDaoImpl;

import com.ed77441.dao.ThreadDao;
import com.ed77441.dao.ThreadDaoImpl;
import com.ed77441.model.Comment;

import com.ed77441.model.Thread;

public class ForumService {
	ThreadDao threadDao;
	CommentDao commentDao;
	
	public ForumService() {
		this(new ThreadDaoImpl(), new CommentDaoImpl());
	}
	
	public ForumService(ThreadDao threadDao, CommentDao commentDao) {
		this.threadDao = threadDao;
		this.commentDao = commentDao;
	}
	
	public List<Thread> getThreadsByGenre(String genre, int page, boolean orderByLastUpdate) {
		int fetchCount = 15;
		int offset = (page - 1) * fetchCount;
		List <Thread> listOfThreads;
		
		if (genre.equals("全部主題")) {
			listOfThreads = threadDao.getAllThreads(offset, fetchCount, orderByLastUpdate);
		}
		else {
			listOfThreads = threadDao.getThreadsByGenre(genre, offset, fetchCount, orderByLastUpdate);
		}
		
		return listOfThreads;
	}
	
	public List<Thread> getThreadsByCaption(String caption, int page, boolean orderByLastUpdate) {
		int fetchCount = 15;
		int offset = (page - 1) * fetchCount;
		
		return threadDao.getThreadsByCaption(caption, offset, fetchCount, orderByLastUpdate);
	}
	
	public int getAllRowCount() {
		return threadDao.getAllRowCount();
	}
	
	public int getRowCountByGenre(String genre) {
		int rowCount = 0;
		
		if (genre.equals("全部主題")) {
			rowCount = threadDao.getAllRowCount();
		}
		else {
			rowCount = threadDao.getRowCountByGenre(genre);
		}
		
		return rowCount;
	}
	
	public int getRowCoutByCaption(String caption) {
		return threadDao.getRowCountByCaption(caption);
	}
	
	public Comment commentRelatedWithThread(int tid) {
		return commentDao.getCommentsByThreadID(tid, 0, 1).get(0);
	}
}