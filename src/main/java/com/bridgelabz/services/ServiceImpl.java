package com.bridgelabz.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgelabz.dao.UserDaoImpl;
import com.bridgelabz.mailUtility.MyMailSender;
import com.bridgelabz.model.MailUser;                                    
import com.bridgelabz.model.User;
import com.bridgelabz.token.TokenGenerator;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
	
	@Autowired
	UserDaoImpl userDao;
	
	@Autowired
	MyMailSender myMailSender;
	
	@Autowired
	MessageProducer messageProducer;
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#login(com.bridgelabz.model.User)
	 */
	public String login(User loginUser) {
		User user=new User();
		List<User> list=userDao.getUser();
		Iterator<User> iterator=list.iterator();
		while(iterator.hasNext())
		{
			user=iterator.next();
			if(user.getPassword()!=null){
				if(user.getUserName().equals(loginUser.getUserName()) &&
						BCrypt.checkpw(loginUser.getPassword(), user.getPassword()))
				{
					if(user.getIsUserActive()==1)
					{
						return user.getUserName();
					}
					return null;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#add(com.bridgelabz.model.User)
	 */
	public User add(User user, String url) {
		User checkUser=userDao.duplicateUser(user);
		if(checkUser==null){
			String password=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(password);
			user.setIsUserActive(0);
			int userId=userDao.add(user);
			if(userId!=0)
			{
				List<User> list = userDao.getUser();
				System.out.println("List : "+list);
				Iterator<User> iterator=list.iterator();
				while (iterator.hasNext()) {
					User findUser = (User) iterator.next();
					if(findUser.getUserName().equals(findUser.getUserName()))
					{
						String token=getToken(user);
						url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token;
						MailUser mailUser=new MailUser();
						mailUser.setTo(user.getUserName());
						mailUser.setSubject("Varification");
						mailUser.setMessage(url);
						messageProducer.send(mailUser);
						/*sendMail(mailUser);*/
						return user;
					}
				}
			}
			return null;
		}
		return null;
	}
	
	public int addSocialUser(User user)
	{
		return userDao.add(user);
	}
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#getUser()
	 */
	public User getUser(int userId)
	{
		return userDao.getUserById(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#isUserAvailable(com.bridgelabz.model.User)
	 */
	public boolean isUserAvailable(User loginUser)
	{
		List<User> list=userDao.getUser();
		Iterator<User> iterator=list.iterator();
		User user=new User();
		while(iterator.hasNext())
		{
			user=iterator.next();
			if(user.getUserName().equals(loginUser.getUserName()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param mailUser
	 * @return boolean 
	 * @Description This method is used to send the email, it will return true 
	 * if mail will send otherwise return false.
	 */
	public boolean sendMail(MailUser mailUser)
	{
		return myMailSender.sendMail(mailUser.getTo(), mailUser.getSubject(), mailUser.getMessage());
		
	}
	
	
	
	/**
	 * @param user
	 * @return String
	 * @Description this method is used to get the Token.
	 */
	public String getToken(User user)
	{
		return TokenGenerator.generateToken(user.getId(), user);
	}

	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#getUserById(int)
	 */
	public User getUserById(int userId) {
		User user=userDao.getUserById(userId);
		if(user!=null)
		{
			return user;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.Service#activeUser(int, com.bridgelabz.model.User)
	 */
	public User activeUser(int id, User user) {
		User userForActivate=userDao.getUserById(id);
		userForActivate.setIsUserActive(1);
		User checkUser=userDao.update(userForActivate);
		if(checkUser!=null){
			return checkUser;
		}
		return checkUser;
	}
	
	public User getUserByEmail(String userName)
	{
		User user=userDao.getUserByUserName(userName);
		if(user!=null)
		{
			return user;
		}
		return null;
	}
	
	public boolean forgotPassword(User user, String url)
	{
		int userId=user.getId();
		String token=TokenGenerator.generateToken(userId, user);
		url = url.substring(0, url.lastIndexOf("/")) + "/verifyUser/" + token;
		return myMailSender.sendMail(user.getUserName(), "Verification for password", url);
	}
	
	public User updateUser(User user)
	{
		return userDao.update(user);
	}

	
}
