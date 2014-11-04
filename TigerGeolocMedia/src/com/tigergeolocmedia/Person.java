package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private String username;
	private String password;
	private String email;
	private int id;
	private List<Person> followers = new ArrayList<Person>();
	private List<Person> following = new ArrayList<Person>();
	
	public Person() {
		super();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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
	public List<Person> getFollowing() {
		return following;
	}
	public void setFollowing(List<Person> followed) {
		this.following = followed;
	}
}
