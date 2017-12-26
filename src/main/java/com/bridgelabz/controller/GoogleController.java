package com.bridgelabz.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.connection.GoogleConnection;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.services.Service;
import com.bridgelabz.token.TokenGenerator;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class GoogleController {

	@Autowired
	Service userService;
	
	@RequestMapping(value = "/googleLogin")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", uuid);
		String googleLoginURL = GoogleConnection.getGoogleAuthURL(uuid);
		response.sendRedirect(googleLoginURL);
	}
	
	@RequestMapping(value="/connectgoogle")
	public void redirectFromGoogle(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		Response responseForMessage=new Response();
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");
		
		if (sessionState == null || !sessionState.equals(googlestate)) {
			response.sendRedirect("googleLogin");
			return;
		}

		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			response.sendRedirect("http://localhost:9090/ToDoApp/#!/registration");
			return;
		}
		
		String authCode = request.getParameter("code");
		String googleaccessToken = GoogleConnection.getAccessToken(authCode);
		JsonNode profile = GoogleConnection.getUserProfile(googleaccessToken);
		User user = userService.getUserByEmail(profile.get("emails").get(0).get("value").asText());

		if (user == null) {
			user = new User();
			user.setfName(profile.get("displayName").asText());
			user.setUserName(profile.get("emails").get(0).get("value").asText());
			user.setIsUserActive(1);
			user.setProfilePic(profile.get("image").get("url").asText());
			user.setPassword("");
			int id = userService.addSocialUser(user);
			if(id>0) {
				String token = TokenGenerator.generateToken(id, user);
    			response.setHeader("Authorization", token);
    			session.setAttribute("token", token);
    			System.out.println("id is > 0");
    			response.sendRedirect("http://localhost:9090/ToDoApp/#!/dummy");
			}
			else
			{
				System.out.println("id is < 0");
				response.sendRedirect("http://localhost:9090/ToDoApp/#!/login");
			}
			
			
		}
		else{
			user = userService.getUserByEmail(user.getUserName());
			String token = TokenGenerator.generateToken(user.getId(), user);
			response.setHeader("Authentication", token);
			user.setProfilePic(profile.get("image").get("url").asText());
			userService.updateUser(user);
			session.setAttribute("token", token);
			responseForMessage.setMessage("Hello "+user.getfName()+" you are alredy visited here.");
			System.out.println("dummy user");
			response.sendRedirect("http://localhost:9090/ToDoApp/#!/dummy");
			//return new ResponseEntity<Response>(responseForMessage, HttpStatus.ACCEPTED);
		}
		
	}
	
	@RequestMapping(value="/getToken")
	public ResponseEntity<Response> getToken(HttpSession session){
		Response responseMessage = new Response();
		responseMessage.setMessage((String) session.getAttribute("token"));
		session.removeAttribute("token");
		return  ResponseEntity.ok(responseMessage);
	}
}