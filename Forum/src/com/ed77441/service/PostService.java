package com.ed77441.service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ed77441.dao.CommentDao;
import com.ed77441.dao.CommentDaoImpl;
import com.ed77441.dao.NotificationDao;
import com.ed77441.dao.NotificationDaoImpl;
import com.ed77441.dao.ThreadDao;
import com.ed77441.dao.ThreadDaoImpl;
import com.ed77441.dao.UserDao;
import com.ed77441.dao.UserDaoImpl;
import com.ed77441.model.Comment;
import com.ed77441.model.Thread;
import com.ed77441.model.User;
import com.ed77441.utils.GenreConstants;

public class PostService {
	ThreadDao threadDao;
	CommentDao commentDao;
	UserDao accountDao;
	NotificationDao notificationDao;
	
	public PostService() {
		this(new ThreadDaoImpl(), new CommentDaoImpl(), new UserDaoImpl(), new NotificationDaoImpl());
	}
	
	public PostService(ThreadDao threadDao, CommentDao commentDao, UserDao accountDao,NotificationDao notificationDao) {
		this.threadDao = threadDao;
		this.commentDao = commentDao;
		this.accountDao = accountDao;
		this.notificationDao = notificationDao;
	}
	
	private boolean isValidArticle(String caption, String genre, String content) {
		return caption.length() >= 4 && 
				  caption.length() <= 50 &&
				   GenreConstants.getGenres().contains(genre) &&
				   isValidComment(content);
	}
	
	private boolean isValidComment(String content) {
		return content.length() >= 10 && content.length() <= 1500;
	}
	
	private int getCommentIndex(int tid, int cid) {
		List<Comment> comments = commentDao.getCommentsByThreadID(tid, 0, Integer.MAX_VALUE);
		
		for (int i = 0; i < comments.size(); ++i) {
			if (comments.get(i).getId() == cid) {
				return i;
			}
		}
		return -1;
	}
	
	public List<Map.Entry<User, Comment>> getCommentRows(int tid, int page) {
		final int fetchCount = 10;
		int offset = (page - 1) * fetchCount;
		List<Comment> comments = commentDao.getCommentsByThreadID(tid, offset, fetchCount);		
		List<Map.Entry<User, Comment>> result = new ArrayList<>();
		
		for (Comment comment: comments) {
			String username = comment.getUser();
			User account = accountDao.getAccount(username).get();
			Map.Entry<User, Comment> row = 
					new AbstractMap.SimpleEntry<User, Comment>(account, comment);
			result.add(row);
		}
		
		return result;
	}
	
	public int startNewThread(String caption, String genre, String content, String requester) {
		if (isValidArticle(caption, genre, content)) {
			Thread thread = new Thread();
			int tid ;
	
			thread.setCaption(caption);
			thread.setGenre(genre);
			thread.setUser(requester);
			tid = threadDao.addThread(thread);
	
			postComment(tid, content, requester);
			return tid;
		}
		
		return -1;
	}
	
	public boolean editThreadAndRelatedComment(int tid, String requester, 
											String caption, String genre, String content) {
		
		if (isValidArticle(caption, genre, content)) {
			Optional<Thread> thread = requestThread(tid, requester);

			if (thread.isPresent()) {
				Thread targetThread = thread.get();
				Comment firstComment = getCommentRelatedWithThread(tid);
				
				targetThread.setCaption(caption);
				targetThread.setGenre(genre);
				firstComment.setContent(content);
				
				threadDao.updateTimeStamp(tid);
				threadDao.setThread(targetThread);
				commentDao.setComment(firstComment);

				return true;
			}
		}
	
		return false;
	}
	
	public boolean removeEntireThread(int tid, String requester) {
		Optional<Thread> thread = requestThread(tid, requester);
		
		if (thread.isPresent()) {
			notificationDao.deleteNotificationByTID(tid);
			commentDao.deleteCommentByThreadID(tid);
			threadDao.deleteThread(tid);
			return true;
		}
		
		return false;
	}
	
	public int postComment(int tid, String content, String user) {
		if (isValidComment(content)) {
			Optional <Thread> thread = threadDao.getThread(tid);

			if (thread.isPresent()) {
				Comment comment = new Comment();
				comment.setContent(content);
				comment.setUser(user);
				comment.setThreadID(tid);
				threadDao.increaseCommentCount(tid);
				threadDao.updateTimeStamp(tid);
		
				return commentDao.addComment(comment);
			}
		}
		return -1;
	}
	
	public boolean editComment(int cid, String content, String requester) {
		if (isValidComment(content)) {

			Optional<Comment> comment = requestComment(cid, requester);
			
			if (comment.isPresent()) {
				Comment targetComment = comment.get();
				targetComment.setContent(content);
				commentDao.setComment(targetComment);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeComment(int cid, String requester) {
		Optional<Comment> comment = requestComment(cid, requester);
		if (comment.isPresent()) {
			notificationDao.deleteNotificationByCID(cid);
			commentDao.deleteComment(cid);
			threadDao.decreaseCommentCount(comment.get().getThreadID());
			return true;
		}
		return false;
	}
	
	public Optional<Thread> requestThread(int tid, String requester) {
		Optional<Thread> thread = threadDao.getThread(tid);
		if (thread.isPresent()) {
			String threadOwner = thread.get().getUser();
			if (requester == null || 
					requester.equals(threadOwner)) {
				return thread;
			}
		}
		
		return Optional.empty();
	}
	
	public Optional<Comment> requestComment(int cid, String requester) {
		Optional<Comment> comment = commentDao.getComment(cid);
		if (comment.isPresent()) {
			String commentOwner = comment.get().getUser();
			if (requester == null || 
					requester.equals(commentOwner)) {
				return comment;
			}
		}
		
		return Optional.empty();
	}
	
	public Comment getCommentRelatedWithThread(int tid) {
		return commentDao.getCommentsByThreadID(tid, 0, 1).get(0);
	}
	
	public String getCommentLocation(int tid, int cid) {
		int rowPosition = getCommentIndex(tid, cid);
    	int index = rowPosition + 1;
    	int page = rowPosition / 10 + 1;
    	String pageParam = page == 1 ? "" : "&page=" + page;
    	/*locate comment*/
		return "/threads?id=" + tid + pageParam + "#comment-" + index;
	}
}
