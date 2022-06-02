package com.adithya.WhatsAppScheduler.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.adithya.WhatsAppScheduler.annotations.ContactNumberConstraint;
import com.adithya.WhatsAppScheduler.annotations.CustomDateConstraint;



public class Request {
	@NotNull
	@NotEmpty(message="Message should not be empty")
	private String message;
	
	
	public Request(String message, String phonenumber, String scheduledTime) {
		//super();
		this.message = message;
		this.phonenumber = phonenumber;
		this.scheduledTime = scheduledTime;
	}
	
	
	
@ContactNumberConstraint
	private String phonenumber;
	
@CustomDateConstraint
	private String scheduledTime;


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	@Override
	public String toString() {
		return "Request [message=" + message + ", phonenumber=" + phonenumber + ", scheduledTime=" + scheduledTime
				+ "]";
	}
	
}
