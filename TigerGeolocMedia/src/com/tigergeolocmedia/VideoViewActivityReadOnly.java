package com.tigergeolocmedia;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.tigergeolocmedia.util.Registry;

public class VideoViewActivityReadOnly extends Activity {
	
	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	
	/**
	 * TextView contenant la description de sa video.
	 */
	private TextView textViewDescription;

	
	/**
	 * Contrôleur de film.
	 */
	private MovieController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view_activity_read_only);
		
		// Pas besoin d'ActionBar ici.
		getActionBar().hide();

		
		textViewDescription = (TextView) findViewById(R.id.textViewDescription);
		
		//initialize the VideoView
		myVideoView = (VideoView) findViewById(R.id.video_view);
		Intent intent = getIntent();
		MovieController controller = Registry.get(Constants.MOVIE_CONTROLLER);
		this.controller = controller;
		
		//set the media controller buttons
		if (mediaControls == null) {
			mediaControls = new MediaController(VideoViewActivityReadOnly.this);
		}
		
		
		// Uri videoUri = intent.getParcelableExtra(Constants.MOVIE_URI);
		File videoFile = new File(controller.getMedia().getPath());
		Uri videoUri = Uri.fromFile(videoFile);

		textViewDescription.setText(controller.getMedia().getDescription());
		
		// create a progress bar while the video file is loading
		progressDialog = new ProgressDialog(VideoViewActivityReadOnly.this);
		// set a title for the progress bar
		// progressDialog.setTitle("JavaCodeGeeks Android Video View Example");
		// set a message for the progress bar
		progressDialog.setMessage("Loading...");
		//set the progress bar not cancelable on users' touch
		progressDialog.setCancelable(false);
		// show the progress bar
		progressDialog.show();
		
		try {
			//set the media controller in the VideoView
			myVideoView.setMediaController(mediaControls);

			//set the uri of the video to be played
			// myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat));
			myVideoView.setVideoURI(videoUri);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		myVideoView.requestFocus();

		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			
			public void onPrepared(MediaPlayer mediaPlayer) {
				// close the progress bar and play the video
				progressDialog.dismiss();
				//if we have a position on savedInstanceState, the video playback should start from here
				myVideoView.seekTo(position);
				if (position == 0) {
					// myVideoView.start();
					myVideoView.pause();
				} else {
					//if we come from a resumed activity, video playback will be paused
					myVideoView.pause();
				}
			}
		});



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_view_activity_read_only, menu);
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
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//we use onSaveInstanceState in order to store the video playback position for orientation change
		outState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
		
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//we use onRestoreInstanceState in order to play the video playback from the stored position 
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}

}
