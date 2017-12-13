package com.bridgelabz.mailUtility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MyMailSender {
	
        
	@Autowired
	JavaMailSenderImpl mailSender;
  
	
	public boolean sendMail(String to,String subject, String message) {
		
	      try {
	    	  
	    	  MimeMessage msg = mailSender.createMimeMessage();
	    	  MimeMessageHelper helper = new MimeMessageHelper(msg,true,"UTF-8");
	    	  helper.setTo(to);
	    	  helper.setSubject(subject);
	    	  String linkForToken ="click on given link to activate user "+("http://" +message);
	    	  helper.setText(linkForToken);
	    	  mailSender.send(msg);
	    	  return true;
	    	  
		} catch (MessagingException e) {
			System.out.println("Hello Deepak mail not sent");
			return false;
		}
	}
}  

