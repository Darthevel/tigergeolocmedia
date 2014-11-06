package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import com.tigergeolocmedia.Media.MediaType;


public class MovieController extends MediaControllerBase {
	private Activity activity;
	
	

	public MovieController(String prefix, String suffix, String directory, Activity activity) {
		super(prefix, suffix, directory);
		this.activity = activity;
	}


	public void recordOLD() {
		// Cr�ation du fichier o� la photo sera sauvegard�e.
		File file = null;
		try {
			// file = createMovieFile();
			file = createFile();
			if (file == null) {
				return;
			}
			Media media = new Media(MediaType.MOVIE, file.getName(), file.getAbsolutePath(), "");
			this.media = media;
			Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			
			// takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			
			activity.startActivityForResult(takeVideoIntent, Constants.ACTION_TAKE_MOVIE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void record() {
		// Cr�ation du fichier o� la photo sera sauvegard�e.
		File file = null;
		try {
			// file = createMovieFile();
			file = createFile();
			if (file == null) {
				return;
			}
			Media media = new Media(MediaType.MOVIE, file.getName(), file.getAbsolutePath(), "");
			setMedia(media);
			Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			
			takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			// set the video image quality to high
		    takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); 
			
			activity.startActivityForResult(takeVideoIntent, Constants.ACTION_TAKE_MOVIE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
	public static final Parcelable.Creator<MovieController> CREATOR = new Parcelable.Creator<MovieController>() {

		@Override
		public MovieController createFromParcel(Parcel source) {
			
	        String prefix = source.readString();
	        String suffix = source.readString();
	        String directory = source.readString();
	        

	        MovieController controller = new MovieController(prefix, suffix, directory, null);
	        
	        String type = source.readString();
	        
	        if (type != null) {
	        	String name = source.readString();
	        	String path = source.readString();
	        	String description = source.readString();
	        	
	        	Media media = new Media(MediaType.MOVIE, name, path, description);
	        	controller.setMedia(media);
	        }

			return controller;
		}

		@Override
		public MovieController[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};

	public void save(Historic historic) {
		if (media != null) {
			historic.add(media);
		}

	}

}
