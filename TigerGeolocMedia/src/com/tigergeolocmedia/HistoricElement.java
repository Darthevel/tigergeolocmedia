package com.tigergeolocmedia;

import android.graphics.Bitmap;
import com.tigergeolocmedia.Media.MediaType;


public class HistoricElement{

	private String name;
	private String description;
	private MediaType type;
	private Bitmap image;
	

	public HistoricElement(String name, String description,
			MediaType type, Bitmap image) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}
