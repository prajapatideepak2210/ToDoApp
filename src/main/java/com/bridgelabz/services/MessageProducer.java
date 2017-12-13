package com.bridgelabz.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bridgelabz.model.MailUser;

public class MessageProducer {
	@Autowired
	JmsTemplate jmsTemplate;
	
	public void send(MailUser email) {
		System.out.println("email : "+email);
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session){
				System.out.println("befor the session.");
				Message message=null;
				try {
					message = session.createObjectMessage(email);
					System.out.println("message : "+message);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				return message;
			}
		});
	}
}
