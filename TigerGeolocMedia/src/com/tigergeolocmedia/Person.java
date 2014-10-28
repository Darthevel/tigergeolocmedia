package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String login;
	private int id;
	private List<Person> followers = new ArrayList<Person>();
	private List<Person> followed = new ArrayList<Person>();
	
	public Person() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Person> getFollowers() {
		return followers;
	}
	public void setFollowers(List<Person> followers) {
		this.followers = followers;
	}
	public List<Person> getFollowed() {
		return followed;
	}
	public void setFollowed(List<Person> followed) {
		this.followed = followed;
	}
}
