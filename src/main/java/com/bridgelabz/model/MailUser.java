package com.bridgelabz.model;

import java.io.Serializable;

public class MailUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4154418864369857809L;
	private String to;
	private String subject;
	private String message;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
