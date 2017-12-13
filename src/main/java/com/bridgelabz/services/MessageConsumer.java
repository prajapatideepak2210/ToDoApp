package com.bridgelabz.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.mailUtility.MyMailSender;
import com.bridgelabz.model.MailUser;

public class MessageConsumer implements MessageListener{
	@Autowired
	Service service;
	
	@Autowired
	MyMailSender myMailSender;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			ObjectMessage msg = (ObjectMessage)message;
			MailUser mail = (MailUser) msg.getObject();
			myMailSender.sendMail(mail.getTo(), mail.getSubject(), mail.getMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
}

	
