package com.bridgelabz.services;

import java.util.List;

import com.bridgelabz.model.MailUser;
import com.bridgelabz.model.User;

public interface Service {
	/**
	 * @param user
	 * @return String
	 * @Description This method is used to Login purpose.
	 */
	String login(User user);
	
	/**
	 * @param user
	 * @return User 
	 * This method is used to register the user.
	 */
	User add(User user, String url);
	
	/**
	 * @return List
	 * @Description This method is used to get the Users list witch is available in database.
	 */
	public List<User> getUser();
	
	/**
	 * @param user
	 * @return boolean 
	 * @Description It will return true if user is available otherwise return false.
	 */
	boolean isUserAvailable(User user);
	
	/**
	 * @param userId
	 * @return User
	 * @Description This method is return User if user available in the database otherwise it return null.
	 */
	User getUserById(int userId);
	
	/**
	 * @param user
	 * @return String
	 * @Description this method is used to get the Token.
	 */
	public String getToken(User user);
	

	/**
	 * @param id
	 * @param user
	 * @return boolean
	 * @Description this method will activate the user .
	 */
	public int addSocialUser(User user);
	
	public boolean sendMail(MailUser mailUser);
	
	public User activeUser(int id, User user);
	
	public User getUserByEmail(String userName);
	
	public boolean forgotPassword(User user, String url);
	
	public User updateUser(User user);
}
