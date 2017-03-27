package com.techouts.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="user") 
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String firstname;
	private String surname;
	private String email;
	private long number;
	private String password;
	private String cpwd;
	
	
	public User(){
		
	}
	public User(String title, String firstname, String surname, String email, long number, String password) {
		super();
		this.title = title;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.number = number;
		this.password = password;
	
	}
	public String getTitle() {
		return title;
	}
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstname() {
		return firstname;
	}
	@XmlElement
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	@XmlElement
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	public long getNumber() {
		return number;
	}
	@XmlElement
	public void setNumber(long number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCpwd() {
		return cpwd;
	}
	@XmlElement
	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
	@Override
	public String toString() {
		return "User [title=" + title + ", firstname=" + firstname + ", surname=" + surname + ", email=" + email
				+ ", number=" + number + ", password=" + password + ", cpwd=" + cpwd + "]";
	}
	
	
	
	
	
	

}
