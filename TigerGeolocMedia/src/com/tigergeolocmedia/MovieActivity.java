package com.tigergeolocmedia;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

public class MovieActivity extends ParentMenuActivity {
	
	private MovieController controller;
	private VideoView videoViewVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		
		Intent intent = getIntent();
		
		

		MovieController controller = intent.getParcelableExtra(Constants.MOVIE_CONTROLLER_PARCELABLE);
		
		File movieFile = new File(controller.getMedia().getPath());
		boolean exists = movieFile.exists();
		
		this.controller = controller;
		
		videoViewVideo = (VideoView) findViewById(R.id.videoViewVideo);
		Uri videoUri = Uri.fromFile(movieFile);
		videoUri = intent.getParcelableExtra(Constants.MOVIE_URI);
		videoViewVideo.setVideoURI(videoUri);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
