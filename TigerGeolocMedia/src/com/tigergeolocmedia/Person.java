package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private String login;
	private String password;
	private String email;
	private int id;
	private List<Person> followerList = new ArrayList<Person>();
	private List<Person> followingList = new ArrayList<Person>();
	
	private long followers;
	private long following;
	
	public Person() {
		super();
	}
	

	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
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

	public List<Person> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(List<Person> followerList) {
		this.followerList = followerList;
	}

	public List<Person> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(List<Person> followingList) {
		this.followingList = followingList;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getFollowing() {
		return following;
	}

	public void setFollowing(long following) {
		this.following = following;
	}

	
}
