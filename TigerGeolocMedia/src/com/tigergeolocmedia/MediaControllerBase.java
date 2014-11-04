package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class MediaControllerBase implements MediaController, Parcelable { 
	
	protected String prefix;
	protected String suffix;
	protected String directory;

	protected Media media;
	
	
	
	public MediaControllerBase(String prefix, String suffix, String directory) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		this.directory = directory;
	}



	protected File createFile() throws IOException {
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// Todo : afficher un dialog ou lever une exception
			return null;
		}
		
		
		File externalStorageDirectory  = Environment.getExternalStorageDirectory();

		// Create an image file name
		String timeStamp = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT).format(new Date());
		String imageFileName = prefix + timeStamp + "_";
		File mediaDirectory = new File(externalStorageDirectory + "/" + directory);
		boolean exists = mediaDirectory.exists();
		if (!exists) {
			mediaDirectory.mkdirs();
		}
		File imageF = File.createTempFile(imageFileName, suffix, mediaDirectory);
		return imageF;
	}



	public String getPrefix() {
		return prefix;
	}



	public String getSuffix() {
		return suffix;
	}



	public String getDirectory() {
		return directory;
	}



	public Media getMedia() {
		return media;
	}



	public void setMedia(Media media) {
		this.media = media;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prefix);
        dest.writeString(suffix);
        dest.writeString(directory);
        if (media != null) {
        	media.writeToParcel(dest, flags);
        }
	}
	
	
	

}
