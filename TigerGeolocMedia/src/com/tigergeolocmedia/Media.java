package com.tigergeolocmedia;

import android.os.Environment;

enum MediaType {
	PICTURE,
	MOVIE,
	SOUND
};

public class Media {
	
	private MediaType type;
	private String name;
	private String path;
	private String description;
	
	public Media(MediaType type, String name, String path, String description) {
		super();
		this.type = type;
		this.name = name;
		this.path = path;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MediaType getType() {
		return type;
	}
	
	public void setType(MediaType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}