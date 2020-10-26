package com.ed77441.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ed77441.model.Comment;
import com.ed77441.utils.SimpleJDBCWrapper;

public class CommentDaoImpl implements CommentDao {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/Forum?useSSL=false";

    static final String USER = "root";
    static final String PASS = "1234";
	
    private Comment mapToModel (ResultSet resultSet) throws Exception {
    	return new Comment(
    			resultSet.getInt(1),
    			resultSet.getString(2),
    			resultSet.getTimestamp(3),
    			resultSet.getInt(4),
    			resultSet.getString(5)
    	);
    }
    
	@Override
	public List<Comment> getCommentsByThreadID(int tid, int rowStart, int rowCount) {
		List<Comment> listOfComments = new ArrayList<>();
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "SELECT * FROM Comment WHERE ThreadID = ? ORDER BY ID LIMIT ? OFFSET ?";
			Object [] parameters = new Object[] {tid, rowCount, rowStart}; 
			ResultSet resultSet = jdbc.read(query, parameters);
			
			while (resultSet.next()) {
				listOfComments.add(mapToModel(resultSet));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		return listOfComments;
	}

	@Override
	public void deleteCommentByThreadID(int tid) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "DELETE FROM Comment WHERE ThreadID = ?";
			Object [] parameters = new Object[] {tid}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	@Override
	public int addComment(Comment comment) {
		int key = -1;
		
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "INSERT INTO Comment (Content, ThreadID, User) VALUES (?, ?, ?)";
			Object [] parameters = new Object[] {comment.getContent(), comment.getThreadID(), comment.getUser()}; 
			jdbc.update(query, parameters);
			ResultSet genKeys = jdbc.getGeneratedKeys();
			
			if (genKeys.next()) {
				key = genKeys.getInt(1);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}			
		
		return key;
	}

	@Override
	public Optional<Comment> getComment(int id) {
		Optional <Comment> target = Optional.empty();
		
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "SELECT * FROM Comment WHERE ID = ?";
			Object [] parameters = new Object[] {id}; 
			ResultSet resultSet = jdbc.read(query, parameters);
			
			if (resultSet.next()) {
				target = Optional.of(mapToModel(resultSet));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		return target;
	}

	@Override
	public void setComment(Comment comment) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "UPDATE Comment SET Content = ? WHERE ID = ?";
			Object [] parameters = new Object[] {comment.getContent(), comment.getId()}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteComment(int id) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "DELETE FROM Comment WHERE ID = ?";
			Object [] parameters = new Object[] {id}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
