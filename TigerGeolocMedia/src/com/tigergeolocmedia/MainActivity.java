package com.tigergeolocmedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Button buttonPicture;
	private Button buttonMovie;
	private ImageView pictureView;
	
	private Button recordButton = null;
	private Button playButton = null;
	
	/**
	 * Contrôleur d'images.
	 */
	private PictureController pictureController = new PictureController(this);
	private SoundController soundController = new SoundController();
	private Historic historic = new Historic();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			recordButton.setText(R.string.reccord);
			soundController.stopReccording();
			soundController.setRecording(false);
			historic.add(soundController.getMedia());
		} else {
			recordButton.setText(R.string.stopReccord);
			soundController.startReccording();
			soundController.setRecording(true);
		}
	}

	public void playSound() {
		if (soundController.isPlaying()) {
			playButton.setText(R.string.play);
			soundController.stopPlaying();
			soundController.setPlaying(false);
		} else {
			playButton.setText(R.string.stop);
			soundController.startPlaying();
			soundController.setPlaying(true);
		}
	}

	protected void takeMovie() {
		// TODO Auto-generated method stub

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

		} // switch
	}


	private void handlePicture() {

		if (pictureController.getCurrentPicturePath() != null) {
			setPic();
			pictureController.setCurrentPicturePath(null);
		}
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
}
