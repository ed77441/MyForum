package com.ed77441.dao;
import com.ed77441.dao.UserDao;
import java.sql.*;
import java.util.Optional;

import com.ed77441.model.User;
import com.ed77441.utils.SimpleJDBCWrapper;
public class UserDaoImpl implements UserDao {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/Forum?useSSL=false";

    static final String USER = "root";
    static final String PASS = "1234";
    
	@Override
	public Optional<User> getAccount(String username) {
		Optional<User> target = Optional.empty();
		
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "SELECT * FROM Account WHERE Username = ?";
			Object [] parameters = new Object[] {username};

			ResultSet resultSet = jdbc.read(query, parameters);
			
			if (resultSet.next()) {
				User account = 
					new User(
							resultSet.getString(1),
							resultSet.getInt(2),
							resultSet.getString(3),
							resultSet.getString(4),
							resultSet.getString(5),
							resultSet.getString(6),
							resultSet.getString(7)
					);
				target = Optional.of(account);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return target;
	}

	
	@Override
	public boolean addAccount(User account) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "INSERT INTO Account (Username, Password, Birthday, Email, Phone, Pfp, Intro) VALUES (?, ?, ?, ?, ?, ?, ?)";
			Object [] parameters = 
					new Object[] {account.getUsername(), account.getPassword(), 
							account.getBirthday(), account.getEmail(), account.getPhone(), 
							account.getPfp(), account.getIntro()};
			jdbc.update(query, parameters);
			return true;
		}
		catch (SQLIntegrityConstraintViolationException de) {
			return false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void setAccount(User account) {
		try (SimpleJDBCWrapper jdbc =
				new SimpleJDBCWrapper(JDBC_DRIVER, DB_URL, USER, PASS)) {
			String query = "UPDATE Account SET Birthday=?, Email=?, Phone=?, Pfp=?, Intro=? WHERE Username=?";
			Object [] parameters = 
					new Object[] {account.getBirthday(), account.getEmail(), account.getPhone(), 
							account.getPfp(), account.getIntro(), account.getUsername()};
			jdbc.update(query, parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void deleteAccount(String username) { }
}
