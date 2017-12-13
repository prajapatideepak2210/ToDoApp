package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.User;

public interface UserDao {
	public int add(User user);
	
	public List<User> getUser();
	
	public User getUserById(int userId);
	
	public User duplicateUser(User user);
	
	public User getUserByUserName(String userName);
	
	public User update(User user);
}
