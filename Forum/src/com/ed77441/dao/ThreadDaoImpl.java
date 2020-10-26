package com.ed77441.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ed77441.model.Thread;
import com.ed77441.utils.SimpleJDBCWrapper;

public class ThreadDaoImpl implements ThreadDao {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/Forum?useSSL=false";

    static final String USER = "root";
    static final String PASS = "1234";
    
    private Thread mapToModel(ResultSet resultSet) throws Exception {
    	return new Thread(					
    			resultSet.getInt(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getInt(4),
				resultSet.getTimestamp(5),
				resultSet.getTimestamp(6),
				resultSet.getString(7)
		);
    }

	@Override
	public Optional<Thread> getThread(int id) {
		Optional<Thread> target = Optional.empty();
		
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT * FROM Thread WHERE ID = ?";
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
	
	private List<Thread> getListOfThreads(String condition, String payload, 
			int rowStart, int rowCount, boolean orderByLastUpdate) {
		List <Thread> listOfThreads = new ArrayList<>();
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String order = orderByLastUpdate ? "LastUpdate" : "StartTime";
			String query = "SELECT * FROM Thread " + condition + 
					" ORDER BY " + order +  " DESC LIMIT ? OFFSET ?";
						
			Object [] parameters = null;
			if (condition.isEmpty()) {
				parameters = new Object[] {rowCount, rowStart};
			}
			else {
				parameters = new Object[] {payload, rowCount, rowStart};
			}

			ResultSet resultSet = jdbc.read(query, parameters);
			
			while (resultSet.next()) {
				listOfThreads.add(mapToModel(resultSet));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return listOfThreads;
	}
	
	@Override
	public List<Thread> getAllThreads(int rowStart, int rowCount,  
			boolean orderByLastUpdate) {
		return getListOfThreads("", "", rowStart, rowCount, orderByLastUpdate);
	}
	

	@Override
	public List<Thread> getThreadsByCaption(String caption,
			int rowStart, int rowCount, boolean orderByLastUpdate) {
		return getListOfThreads("WHERE Caption LIKE ?", "%" + caption + "%", 
				rowStart, rowCount, orderByLastUpdate);
	}

	@Override
	public List<Thread> getThreadsByGenre(String genre, 
			int rowStart, int rowCount, boolean orderByLastUpdate) {
		return getListOfThreads("WHERE Genre = ?", genre, 
				rowStart, rowCount, orderByLastUpdate);
	}

	@Override
	public int addThread(Thread thread) {
		int key = -1;
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "INSERT INTO Thread (Caption, Genre, User) VALUES (?, ?, ?)";
			Object [] parameters = new Object[] 
					{thread.getCaption(), thread.getGenre(), thread.getUser()}; 
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
	public void setThread(Thread thread) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "UPDATE Thread SET Caption = ?, Genre = ? WHERE ID = ?";
			Object [] parameters = new Object[] 
					{thread.getCaption(), thread.getGenre(), thread.getId()}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}			
	}

	
	private void updateCommentCount(int id, String modify) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "UPDATE Thread SET " + modify + " WHERE ID = ?";
			Object [] parameters = new Object[] {id}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	@Override
	public void increaseCommentCount(int id) {
		updateCommentCount(id, "CommentCount = CommentCount + 1");	
	}
	
	@Override
	public void decreaseCommentCount(int id) {
		updateCommentCount(id, "CommentCount = CommentCount - 1");	
	}

	private int getRowCount(String condition, String payload) {
		int rowCount = 0;
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT COUNT(*) FROM Thread " + condition;
			Object [] parameters = new Object[] {payload}; 
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
	
	@Override
	public int getAllRowCount() {
		int rowCount = 0;
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "SELECT COUNT(*) FROM Thread";
			ResultSet resultSet = jdbc.read(query, null);
			if (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return rowCount;
	}
	
	@Override
	public int getRowCountByGenre(String genre) {
		return getRowCount("WHERE Genre = ?", genre);
	}

	@Override
	public int getRowCountByCaption(String caption) {
		return getRowCount("WHERE Caption LIKE ?", "%" + caption + "%");
	}

	@Override
	public void deleteThread(int id) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "DELETE FROM Thread WHERE ID = ?";
			Object [] parameters = new Object[] {id}; 
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
	}

	@Override
	public void updateTimeStamp(int id) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			
			String query = "UPDATE Thread SET LastUpdate = NOW() WHERE ID = ?";
			Object [] parameters = new Object[] {id};
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
