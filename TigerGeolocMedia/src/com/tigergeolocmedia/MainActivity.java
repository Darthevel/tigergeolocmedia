package com.tigergeolocmedia;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.tigergeolocmedia.util.Registry;

/**
 * Activity principale de l'application.
 * @author HumanBooster
 *
 */
public class MainActivity extends ParentMenuActivity {

	private Button buttonPicture;
	private Button buttonMovie;
	private ImageView pictureView;
	
	private Button recordButton = null;
	private Button playButton = null;
	
	private static String INITIAL_KEY = "INITIAL_KEY";
	/**
	 * Indique si l'application vient de démarrer : quand on bouge l'écran (portrait -> paysage ou le contraire)
	 * initial passe à false. Ce boolean est utilisé par la methode {@link #onWindowFocusChanged(boolean)}.
	 */
	private boolean initial = true;
	

	/**
	 * Contrôleur d'images.
	 */
	private PictureController pictureController = new PictureController(Constants.IMAGE_PREFIX, Constants.IMAGE_SUFFIX, Constants.PICTURE_DIRECTORY, this);
	
	
	/**
	 * Contrôleur de video.
	 */
	private MovieController movieController = new MovieController(Constants.MOVIE_PREFIX, Constants.MOVIE_SUFFIX, Constants.MOVIE_DIRECTORY, this);
	
	/**
	 * Contrôleur de son.
	 */
	private SoundController soundController = new SoundController(Constants.SOUND_PREFIX, Constants.SOUND_SUFFIX, Constants.SOUND_DIRECTORY);
	private Historic historic = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context context = getApplicationContext();

		historic = Historic.getInstance(context);
		buttonPicture = (Button) findViewById(R.id.buttonPicture);
		buttonPicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				takePicture();
			}

		});

		buttonMovie = (Button) findViewById(R.id.buttonMovie);
		buttonMovie.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				takeMovie();
			}

		});

		recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				record();
			}

		});

		playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
			}

		});
		pictureView = (ImageView) findViewById(R.id.pictureView);
	}

	public void record() {
		if (soundController.isRecording()) {
			recordButton.setText(R.string.record);
			soundController.stopRecording();
			soundController.setRecording(false);
			historic.add(soundController.getMedia());
		} else {
			recordButton.setText(R.string.stopRecord);
			soundController.record();
			soundController.setRecording(true);
		}
	}

	public void playSound() {
		if (soundController.isPlaying()) {
			playButton.setText(R.string.play);
			soundController.stop();
			soundController.setPlaying(false);
		} else {
			playButton.setText(R.string.stop);
			soundController.play();
			soundController.setPlaying(true);
		}
	}

	protected void takeMovie() {
		movieController.record();

	}

	protected void takePicture() {

		// On confie le boulot au controleur.
		pictureController.record();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		
		// 
		case Constants.ACTION_TAKE_PICTURE: {
			if (resultCode == RESULT_OK) {
				handlePicture();
			}
			break;
		} // ACTION_TAKE_PICTURE_B
		// 
		case Constants.ACTION_TAKE_MOVIE: {
			if (resultCode == RESULT_OK) {
				handleMovie();
			}
			break;
		} // ACTION_TAKE_PICTURE_B

		} // switch
	}


	private void handlePicture() {

//		if (pictureController.getMedia() != null) {
//			setPic();
//			pushToHistoric(pictureController.getMedia());
//			pictureController.setMedia(null);
//		}
		startPictureActivity();
	}

	private void setPic() {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = pictureView.getWidth();
		int targetH = pictureView.getHeight();
		
		Bitmap currentBitmap = pictureController.computeCurrentBitmap(targetW, targetH);
		
		/* Associate the Bitmap to the ImageView */
		pictureView.setImageBitmap(currentBitmap);
	}
	
	private void setPic(Media media) {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = pictureView.getWidth();
		int targetH = pictureView.getHeight();
		
		Bitmap currentBitmap = PictureController.computeBitmap(media, targetW, targetH);
		
		/* Associate the Bitmap to the ImageView */
		pictureView.setImageBitmap(currentBitmap);
	}
	
	private void handleMovie() {

		if (movieController.getMedia() != null) {
			setMovie();
			pushToHistoric(movieController.getMedia());
			movieController.setMedia(null);
		}
// 		startMovieActivity();
	}

	private void pushToHistoric(Media media) {
		historic.add(media);
		
		// Pour test
		List<Media> mediaList = historic.getMediaList();
		mediaList = historic.getMediaList();		
	}

	private void setMovie() {
		// TODO 
		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		initial = savedInstanceState.getBoolean(INITIAL_KEY);
		
		
		pictureController = Registry.get(Constants.PICTURE_CONTROLLER);

	}
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// 
		if (!initial) {
			restoreLatestmedia();
		}
	}

	private void restoreLatestmedia() {
		Media latestMedia = historic.getLatestMedia();
		
		// Pour l'instant, je ne gère que les images.
		if (latestMedia != null) {
			if (latestMedia.getType().equals(MediaType.PICTURE)) {
				setPic(latestMedia);
				
			}
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean(INITIAL_KEY, false);
		
		Registry.register(Constants.PICTURE_CONTROLLER, pictureController);

	}
	
	private void startPictureActivity() {
		if (pictureController.getMedia() != null) {
			Intent intent = new Intent(this, PictureActivity.class);
			intent.putExtra(Constants.PICTURE_CONTROLLER_PARCELABLE, pictureController);
			startActivity(intent);
		}
	}

	private void startMovieActivity() {
		if (movieController.getMedia() != null) {
			Intent intent = new Intent(this, MovieActivity.class);
			intent.putExtra(Constants.MOVIE_CONTROLLER_PARCELABLE, movieController);
			startActivity(intent);
		}
	}



}
