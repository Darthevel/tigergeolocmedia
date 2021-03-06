package com.tigergeolocmedia;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.util.Registry;

/**
 * Activity principale de l'application.
 * @author HumanBooster
 *
 */
public class MainActivity extends ParentMenuActivity {
	
	
	
	private Uri videoURI;


	private Button buttonPicture;
	private Button buttonMovie;
	private ImageView pictureView;
	
	private Button recordButton = null;
	
	private static String INITIAL_KEY = "INITIAL_KEY";
	/**
	 * Indique si l'application vient de démarrer : quand on bouge l'écran (portrait -> paysage ou le contraire)
	 * initial passe à false. Ce boolean est utilisé par la methode {@link #onWindowFocusChanged(boolean)}.
	 */
	private boolean initial = true;
	

	/**
	 * Contrôleur d'images.
	 */
	// private PictureController pictureController = new PictureController(Constants.IMAGE_PREFIX, Constants.IMAGE_SUFFIX, Constants.PICTURE_DIRECTORY, this);
	private PictureController pictureController = null;
	
	
	
	public MainActivity() {
		super();
		
		initControllers();
	}

	private void initControllers() {
		pictureController = Registry.get(Constants.PICTURE_CONTROLLER);
		if (pictureController == null) {
			pictureController = new PictureController(Constants.IMAGE_PREFIX, Constants.IMAGE_SUFFIX, Constants.PICTURE_DIRECTORY, this);
			Registry.register(Constants.PICTURE_CONTROLLER, pictureController);
		}
		pictureController.setActivity(this);
		
		movieController = Registry.get(Constants.MOVIE_CONTROLLER);
		if (movieController == null) {
			movieController = new MovieController(Constants.MOVIE_PREFIX, Constants.MOVIE_SUFFIX, Constants.MOVIE_DIRECTORY, this);
			Registry.register(Constants.MOVIE_CONTROLLER, movieController);
		}
		movieController.setActivity(this);
	}

	/**
	 * Contrôleur de video.
	 */
	// private MovieController movieController = new MovieController(Constants.MOVIE_PREFIX, Constants.MOVIE_SUFFIX, Constants.MOVIE_DIRECTORY, this);
	private MovieController movieController = null;

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
				Intent intent = new Intent(getApplicationContext(), SoundActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}

		});
		pictureView = (ImageView) findViewById(R.id.pictureView);
		// Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").error(R.drawable.error).into(pictureView);
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
		} // ACTION_TAKE_PICTURE
		// 
		case Constants.ACTION_TAKE_MOVIE: {
			if (resultCode == RESULT_OK) {
				handleMovie();
			}
			break;
		} // ACTION_TAKE_MOVIE

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
	
	
	private void handleMovie() {

//		if (movieController.getMedia() != null) {
//			setMovie();
//			pushToHistoric(movieController.getMedia());
//			movieController.setMedia(null);
//		}
 		startMovieActivity();
	}

	private void pushToHistoric(Media media) {
		historic.add(media);
	}

	private void setMovie() {
		// TODO 
		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		initial = savedInstanceState.getBoolean(INITIAL_KEY);
		
		
		// pictureController = Registry.get(Constants.PICTURE_CONTROLLER);

	}
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// 
//		if (!initial) {
//			restoreLatestmedia();
//		}
	}

//	private void restoreLatestmedia() {
//		Media latestMedia = historic.getLatestMedia();
//		
//		// Pour l'instant, je ne gère que les images.
//		if (latestMedia != null) {
//			if (latestMedia.getType().equals(MediaType.PICTURE)) {
//				setPic(latestMedia);
//				
//			}
//		}
//		
//	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean(INITIAL_KEY, false);
		outState.putParcelable(Constants.VIDEO_STORAGE_KEY, videoURI);

		
		// Registry.register(Constants.PICTURE_CONTROLLER, pictureController);

	}
	
	private void startPictureActivity() {
		if (pictureController.getMedia() != null) {
			Intent intent = new Intent(this, PictureActivity.class);
			// intent.putExtra(Constants.PICTURE_CONTROLLER_PARCELABLE, pictureController);
			// Registry.register(Constants.PICTURE_CONTROLLER, pictureController);
			startActivity(intent);
		}
	}

	private void startMovieActivity() {
		if (movieController.getMedia() != null) {
			
			
			File movieFile = new File(movieController.getMedia().getPath());
			
			videoURI = Uri.fromFile(movieFile);

			Intent intent = new Intent(this, VideoViewActivity.class);
			
			// intent.putExtra(Constants.MOVIE_CONTROLLER_PARCELABLE, movieController);
			// Registry.register(Constants.MOVIE_CONTROLLER, movieController);
			intent.putExtra(Constants.MOVIE_URI, videoURI);
			startActivity(intent);
		}
	}



}
