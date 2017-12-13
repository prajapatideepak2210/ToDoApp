package com.bridgelabz.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

/**
 * @author Deepak Prajapati
 * @Description This class is used to control all database operation.
 *
 */
@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * @param user
	 * @return boolean
	 * @Description This method is used to add the users into the database. 
	 * It returns true if data is inserted, otherwise it returns false.
	 */
	public int add(User user)
	{
		int userId=0;
		if(user!=null){
			try{
				Session session= sessionFactory.openSession();
				Transaction transaction=session.beginTransaction();
				userId=(Integer) session.save(user);
				transaction.commit();
				session.close();
				return userId;
			}catch(Exception e){
				return userId;
			}
		}
		return userId;
	}
	
	/**
	 * @return List
	 * @Description this method is used to get the list of user from the database.
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUser()
	{
		Session session=sessionFactory.openSession();
		Query<User> query = session.createQuery("from User");
		List<User> list = (List<User>)query.list();
		session.close();
		return list;
	}
	
	
	/**
	 * @param userId
	 * @return User
	 * 
	 * @Description It will return User if user available in database otherwise return null.
	 */
	@SuppressWarnings("rawtypes")
	public User getUserById(int userId)
	{
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from User where id = :id");
			query.setParameter("id", userId);
			User user = (User) query.uniqueResult();
			session.close();
			return user;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * @param user
	 * @return User
	 * @Description This method is used to check user is duplicate or not.
	 * If user is duplicate than it returns true otherwise false.
	 */
	public User duplicateUser(User user) {
		List<User> list=getUser();
		Iterator<User> iterator=list.iterator();
		while (iterator.hasNext()) {
			User userForDuplicate= (User) iterator.next();
			if(user.getContactNumber().equals(userForDuplicate.getContactNumber()) ||
					user.getUserName().equals(userForDuplicate.getUserName()))
			{
				return user;
			}
		}
		return null;
	}
	
	/**
	 * @param user
	 * @return boolean 
	 * @Description this method is used to activate the user, 
	 * it will return true if user is activated otherwise returns false.
	 */
	
	
	@SuppressWarnings("rawtypes")
	public User getUserByUserName(String userName)
	{
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from User where userName = :userName");
			query.setParameter("userName", userName);
			User user = (User) query.uniqueResult();
			session.close();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public User update(User user) {
		try {
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			session.update(user);
			transaction.commit();
			session.close();
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}
