package com.ed77441.dao;

import java.util.List;
import java.util.Optional;

import com.ed77441.model.Comment;

public interface CommentDao {
	public List<Comment> getCommentsByThreadID(int tid, int rowStart, int rowCount);
	public void deleteCommentByThreadID(int tid);
	
	public int addComment (Comment comment);
	public Optional<Comment> getComment(int id);
	public void setComment(Comment comment);
	public void deleteComment(int id);
}
