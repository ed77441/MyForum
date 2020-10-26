package com.ed77441.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ed77441.dao.UserDao;
import com.ed77441.dao.UserDaoImpl;
import com.ed77441.model.User;
import com.ed77441.utils.IDGenerator;

public class UserService {
	
	public static final int CREATED = 0;
	public static final int DUPLICATED = 1;
	public static final int INVALID = 2;
	
	UserDao userDao;

	public UserService() {
		this(new UserDaoImpl());
	}
	
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public Optional<User> getUser (String user) {
		return userDao.getAccount(user);
	}
	
	public int register(String username, String password,
			String birthday, String email, String phone) {
		
		if (username.length() < 6 ||
			username.length() > 20 ||
			password.length() < 6 ||
			password.length() > 20 ||	
			email.length() > 30 ||	
			!username.matches("^[a-zA-Z0-9]+$") ||
			!password.matches("^[a-zA-Z0-9]+$") || 
			!birthday.matches("^[0-9]{4}(-[0-9]{2}){2}$") ||
			!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z0-9]+)+$") ||
			!phone.matches("^[0-9]{10}$")) {
			
			return INVALID;
		}
		
		final String defaultPfp = "/pfp/default.png";
		User account = new User(username, password.hashCode(),
					birthday, email, phone, defaultPfp, "");
		
		if (!userDao.addAccount(account)) {
			return DUPLICATED;
		}
		
		return CREATED;
	}
	
	public boolean editProfileInfo( 
			String username, String birthday, String email, String phone, String intro) {
			
		if (email.length() > 30 ||
			intro.length() > 1000 ||
			!birthday.matches("^[0-9]{4}(-[0-9]{2}){2}$") ||
			!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z0-9]+)+$") ||
			!phone.matches("^[0-9]{10}$")) {
				return false;
		}
		
		User account = userDao.getAccount(username).get();
		account.setBirthday(birthday);
		account.setEmail(email);
		account.setPhone(phone);
		account.setIntro(intro);
		
		userDao.setAccount(account);
		return true;
	}
	
	
	public boolean changeProfilePic(String webroot, 
			String username, Part filePart, HttpSession session) {
			
		if (filePart != null) {
			InputStream inputStream = null;
			User account = userDao.getAccount(username).get();
			
			try {
				inputStream = filePart.getInputStream();
			    BufferedImage uploadedPfp = ImageIO.read(inputStream);
				
			    String pathPrefix = webroot + "pfp/";
			    String []pathSegments = account.getPfp().split("/");
			    String oldFileName = pathSegments[pathSegments.length - 1];
				String newFileName = IDGenerator.generate(8) + ".png";
				
			    File oldFile = new File(pathPrefix + oldFileName);
			    File newFile = new File(pathPrefix + newFileName);
	
				while (newFile.exists()) { /*Choose new name if file existed*/
					newFileName = IDGenerator.generate(8) + ".png";
					newFile = new File(pathPrefix + newFileName);
				}
			   			    
			    if (!oldFileName.equals("default.png") 
			    		&& !oldFile.delete()) {
			    	System.out.println("Old pfp deletion failed!");
			    } 
			    
			    ImageIO.write(uploadedPfp, "png", newFile); 			    
				account.setPfp("/pfp/" + newFileName);
				session.setAttribute("pfp", account.getPfp());
				userDao.setAccount(account);
				return true;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (inputStream != null) 
						inputStream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public boolean login(HttpSession session, String username, String password) {
		
		Optional<User> account = userDao.getAccount(username);
		
		if (account.isPresent()) {
			User userAccount = account.get();
			
			if (userAccount.getPassword() == password.hashCode()) {
				session.setAttribute("user", userAccount.getUsername());
				session.setAttribute("pfp", userAccount.getPfp());
				return true;
			}
		}
		
		return false;
	}
	
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
