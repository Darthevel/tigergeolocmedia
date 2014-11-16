package com.tigergeolocmedia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author MichaelAdjedj
 *
 */
public class Media implements Parcelable{
	
	public enum MediaType {
		PICTURE,
		MOVIE,
		SOUND
	}

	private MediaType type;
	private String name;
	private String path;
	private String description;
	private int id;
	
	//Use by HistoCrud.
	public Media() {}

	public Media(int id, String name, MediaType type, String description,
			String path) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.path = path;
	}
	//End of Constructors used by HistoCrud.

	public Media(MediaType type, String name, String path, String description,
			int id) {
		super();
		this.type = type;
		this.name = name;
		this.path = path;
		this.description = description;
		this.id = id;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * A vérifier si utile ou pas?
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A vérifier si utile ou pas?
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeString(this.getType().toString());
    	dest.writeString(this.getName());
    	dest.writeString(this.getPath());
    	dest.writeString(this.getDescription());
	}
}
