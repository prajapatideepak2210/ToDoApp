package com.bridgelabz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.PasswordUser;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.services.Service;
import com.bridgelabz.token.TokenGenerator;
import com.bridgelabz.validator.Validator;

/**
 * @author Deepak Prajapati
 * @Description this class controls all the transaction of project.
 *
 */
@RestController
public class UserController {
	@Autowired
	Service serviceImpl;
	
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(HttpServletRequest request) {
		String token = request.getHeader("TokenAccess");
		int userId = TokenGenerator.verifyToken(token);
		System.out.println("user id : "+userId);
		User user = serviceImpl.getUserById(userId);
		return user;
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addUser(@RequestBody User user, HttpServletRequest request) {

		Response response = new Response();
		String message = Validator.isUserValid(user);
		if (message == null) {
			String url = request.getRequestURL().toString();
			User checkUser=serviceImpl.add(user, url);
			if (checkUser!=null) {

				response.setMessage(
						"User Successfully Registered, user got a mail for verifing please go there and verify.");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage("User Already Exist.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setMessage(message);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody User user, HttpSession session) {
		if (serviceImpl.isUserAvailable(user)) {
			String userName = serviceImpl.login(user);
			if (userName != null) {
				Response response = new Response();
				session.setAttribute("user", userName);
				User userForId=serviceImpl.getUserByEmail(userName);
				session.setAttribute("user_id", userForId.getId());
				User userForToken=serviceImpl.getUserByEmail(user.getUserName());
				String token = TokenGenerator.generateToken(userForToken.getId(), userForToken);
				response.setMessage(token);
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			} else {
				Response response = new Response();
				response.setMessage("UserName and Password Mismatch.");
				System.out.println(response.getMessage());
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			Response response = new Response();
			response.setMessage("User is not available, please register first.");
			System.out.println(response.getMessage());
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpSession session) {
		Response response = new Response();
		session.removeAttribute("user");
		session.invalidate();
		response.setMessage("Logout Successfully.");
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}


	@RequestMapping(value = "/active/{jwt:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyToken(@PathVariable("jwt") String token, HttpServletResponse response) throws IOException {
		Response responseMessage=new Response();
		int id = TokenGenerator.verifyToken(token);
		System.out.println("id is : "+id);
		//Response responseMessage = new Response();
		if (id != 0) {
			User user = serviceImpl.getUserById(id);
			if (user != null) {
				serviceImpl.activeUser(id, user);
				System.out.println("tum active ho gae ho");
				responseMessage.setMessage("User has been Activated");
				//response.sendRedirect("http://localhost:9090/ToDoApp/#!/login");
				return new ResponseEntity<Response>(responseMessage, HttpStatus.ACCEPTED);
			}
			responseMessage.setMessage("User is not available.");
			//response.sendRedirect("http://localhost:9090/ToDoApp/#!/registration.html");
			return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		}
		responseMessage.setMessage("Wrong id.");
		//response.sendRedirect("http://localhost:9090/ToDoApp/#!/registration");
		return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotPassword(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Response responseMessage = new Response();
		
		if (user.getUserName() != null && serviceImpl.isUserAvailable(user)) {
			String url = request.getRequestURL().toString();
			User users = serviceImpl.getUserByEmail(user.getUserName());
			
			boolean isSent = serviceImpl.forgotPassword(users, url);
			if (isSent) {
				responseMessage.setMessage("Go on email, and verify the link.");
				return new ResponseEntity<Response>(responseMessage, HttpStatus.ACCEPTED);
			}else{
				responseMessage.setMessage("operation is failed please try after some time.");
				return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
			}
		}
		responseMessage.setMessage("email is not available, please enter correct email.");
		return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/verifyUser/{jwt:.+}", method = RequestMethod.GET)
	public void userVerify(@PathVariable("jwt") String token, HttpSession session, HttpServletResponse response) throws IOException {
		int id = TokenGenerator.verifyToken(token);
		session.setAttribute("id", id);
		Response responseMessage = new Response();
		if (id != 0) {
			//responseMessage.setMessage("You are verified Your key is " + user.getId() + " please reset your password with this key.");
			response.sendRedirect("http://localhost:9090/ToDoApp/#!/resetpassword");
		}else{
			responseMessage.setMessage("you are not user.");
			response.sendRedirect("http://localhost:9090/ToDoApp/#!/registration");
		}
	}
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.PUT)
	public ResponseEntity<Response> resetPassword(@RequestBody PasswordUser passwordUser, HttpSession session, HttpServletResponse response) throws IOException
	{
		Response responseMessage=new Response();
		int userId=(int) session.getAttribute("id");
		if(passwordUser.getPassword().equals(passwordUser.getConfirmPassword()))
		{
			User user=serviceImpl.getUserById(userId);
			String securePassword=BCrypt.hashpw(passwordUser.getPassword(), BCrypt.gensalt());
			user.setPassword(securePassword);
			serviceImpl.updateUser(user);
			
			responseMessage.setMessage("Password Reset Successfully.");
			return new ResponseEntity<Response>(responseMessage, HttpStatus.ACCEPTED);
			//response.sendRedirect("http://localhost:9090/ToDoApp/#!/login");
			
		}else{
			responseMessage.setMessage("Password and confirmpassword is not matching, please reset again.");
			//response.sendRedirect("http://localhost:9090/ToDoApp/#!/resetpassword");
			return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Note note){
		int ownerId = note.getUser_id();
		User owner = serviceImpl.getUserById(ownerId);
		if(owner != null)
		{
			return new ResponseEntity<User>(owner, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
