package com.bridgelabz.validator;


import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.User;

public class Validator{
	
	@Autowired
	User user;
	
	/**
	 * @param user
	 * @return boolean
	 * @Description This method is used to check FirstName and LastName is correct or not.
	 * if correct than it returns true, otherwise returns false. 
	 */
	public static boolean isName(User user)
	{
		if(user.getfName().matches("[a-zA-Z]*") & user.getlName().matches("[a-zA-Z]*")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @param user
	 * @return boolean
	 * @Description This method is used to check password is correct or not.
	 * if password is correct than it returns true, otherwise it returns false.
	 */
	public static boolean isPassword(User user)
	{
		if(user.getPassword().length()>=8)
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @param user
	 * @return boolean
	 * @Description This method is used to check ContactNumber is correct or not.
	 * if ContactNumber is correct than it returns true, otherwise it returns false.
	 */
	public static boolean isContactNumber(User user)
	{
		if(user.getContactNumber().matches("[0-9]{10}"))
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @param user
	 * @return boolean
	 * @Description This method is used to check UserName is correct or not.
	 * if UserName is correct than it returns true, otherwise it returns false.
	 */
	public static boolean isUserName(User user)
	{
		if(user.getUserName().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
		{
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @param user
	 * @return String
	 * @Description This method is used to check User is Valid or not.
	 * if User is correct than it returns null, otherwise it returns related message.
	 */
	public static String isUserValid(User user)
	{
		if(!isName(user)){
			return "Name is not correct.";
		}
		else if(!isPassword(user)){
			return "Password is not correct.";
		}
		else if(!isContactNumber(user)){
			return "Contact Number is not correct";
		}
		else if(!isUserName(user)){
			return "UserName is not correct";
		}
		else{
			return null;
		}
	}
}
