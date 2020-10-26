package com.ed77441.dao;
import java.util.Optional;

import com.ed77441.model.User;

public interface UserDao {
	public Optional<User> getAccount(String username);
	public boolean addAccount(User account);
	public void setAccount(User account);
	public void deleteAccount(String username);
}
