package com.tigergeolocmedia;

import com.tigergeolocmedia.util.Registry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends ParentMenuActivity {

	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	
	private EditText editTextDescription;

	
	/**
	 * Contrôleur de film.
	 */
	private MovieController controller;

	
	private Menu menu;
	
	/**
	 * Par défaut, l'image peut être sauvée.
	 */
	private boolean itemSaveEnabled = true;
	private final static String ITEM_SAVE_ENABLED_KEY = "ITEM_SAVE_ENABLED_KEY";

	/**
	 * Par défaut, l'image peut être envoyéee.
	 */
	private boolean itemSaveAndSendEnabled = true;
	private final static String ITEM_SAVE_AND_SEND_ENABLED_KEY = "ITEM_SAVE_AND_SEND_ENABLED_KEY";


	private Historic historic = null;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set the main layout of the activity
		setContentView(R.layout.activity_video_view);

		
		historic = Historic.getInstance(getApplicationContext());
		
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);



		
		Intent intent = getIntent();
//		MovieController controller = intent
//				.getParcelableExtra(Constants.MOVIE_CONTROLLER_PARCELABLE);
		MovieController controller = Registry.get(Constants.MOVIE_CONTROLLER);
		this.controller = controller;


		//set the media controller buttons
		if (mediaControls == null) {
			mediaControls = new MediaController(VideoViewActivity.this);
		}
		Bundle extras  = intent.getExtras();
		Uri videoUri = intent.getParcelableExtra(Constants.MOVIE_URI);

		//initialize the VideoView
		myVideoView = (VideoView) findViewById(R.id.video_view);

		// create a progress bar while the video file is loading
		progressDialog = new ProgressDialog(VideoViewActivity.this);
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
		//we also set an setOnPreparedListener in order to know when the video file is ready for playback
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
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//we use onSaveInstanceState in order to store the video playback position for orientation change
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//we use onRestoreInstanceState in order to play the video playback from the stored position 
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
		
		if (!itemSaveEnabled) {
			MenuItem item = menu.findItem(R.id.itemSave);
			item.setEnabled(false);
		}
		if (!itemSaveAndSendEnabled) {
			MenuItem item = menu.findItem(R.id.itemSaveAndSend);
			item.setEnabled(false);
		}
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
		if (id == R.id.itemSaveAndSend) {
			saveAndSend();
			// On vient d'envoyer l'image, on interdit un nouvel envoi.
			item.setEnabled(false);
			itemSaveAndSendEnabled = false;
			
			// En avant d'envoyer l'image, on l'a aussi sauvée
			// on interdit donc une nouvelle sauvegarde.
			item = menu.findItem(R.id.itemSave);
			itemSaveEnabled = false;
			item.setEnabled(false);

			return true;
		}
		if (id == R.id.itemSave) {
			save();
			
			// On vient de sauver l'image, on interdit une nouvelle sauvegarde.
			itemSaveEnabled = false;
			item.setEnabled(false);
			return true;
		}
		if (id == R.id.itemCancel) {
			cancel();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Méthode appelée quand le menu cancel (R.id.itemCancel) est cliqué .
	 */
	private void cancel() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	private void saveAndSend() {
		// Sauvegarde du media
		if (itemSaveEnabled) {
			save();
		}

		// Envoi du media
		send();

	}
	
	/**
	 * Envoi de l'image sur le serveur.
	 */
	private void send() {
		// TODO : implémenter cette fonctionnalité.
	}
	
	private void save() {
		// Récuoération du media
		Media media = controller.getMedia();

		// On colle la description dans le media.
		media.setDescription(editTextDescription.getText().toString());



		// On sauve l'historique
		controller.save(historic);

	}






}