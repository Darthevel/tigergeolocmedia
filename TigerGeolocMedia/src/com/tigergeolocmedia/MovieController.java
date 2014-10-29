package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class MovieController extends MediaControllerBase {
	private Activity activity;
	
	

	public MovieController(String prefix, String suffix, String directory, Activity activity) {
		super(prefix, suffix, directory);
		this.activity = activity;
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
			this.media = media;
			Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			
			activity.startActivityForResult(takePictureIntent, Constants.ACTION_TAKE_MOVIE);
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
	

}
