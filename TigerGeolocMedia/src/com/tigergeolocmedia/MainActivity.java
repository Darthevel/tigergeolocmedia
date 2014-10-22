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
	private Button buttonSound;
	private ImageView pictureView;
	
	
	/**
	 * Contrôleur d'images.
	 */
	private PictureController pictureController = new PictureController(this);
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
		buttonSound = (Button) findViewById(R.id.buttonSound);
		buttonSound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				taKeSound();
			}
			
		});
		
		pictureView = (ImageView) findViewById(R.id.pictureView);
	}

	protected void taKeSound() {
		// TODO Auto-generated method stub
		
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

