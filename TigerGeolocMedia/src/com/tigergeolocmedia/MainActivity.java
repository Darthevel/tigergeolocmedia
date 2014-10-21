package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	public final static String PICTURE_DIRECTORY = "/DCIM/Camera/TigerCamera";
	public final static String MOVIE_DIRECTORY = "DCIM/Camera/TigerCamera";
	public final static String IMAGE_PREFIX = "TIGER_";
	public static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final int ACTION_TAKE_PHOTO_B = 1;

	public final static String IMAGE_SUFFIX = JPEG_FILE_SUFFIX;
	private Button buttonPicture;
	private Button buttonMovie;
	private Button buttonSound;
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
		
	}

	protected void taKeSound() {
		// TODO Auto-generated method stub
		
	}

	protected void takeMovie() {
		// TODO Auto-generated method stub
		
	}

	protected void takePicture() {
		// Création du fichier où la photo sera sauvegardée.
		File pictureFile = null;
		try {
			pictureFile = createPictureFile();
			if (pictureFile == null) {
				return;
			}
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
			
			startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO_B);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		
		
	}

	@SuppressLint("SimpleDateFormat")
	private File createPictureFile() throws IOException {
		
		
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// Todo : afficher un dialog
			return null;
		}
		
		String mediaMounted = Environment.MEDIA_MOUNTED;
		String externalStorageState = Environment.getExternalStorageState();
		
		File externalStorageDirectory  = Environment.getExternalStorageDirectory();

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = IMAGE_PREFIX + timeStamp + "_";
		File pictureDirectory = new File(externalStorageDirectory + "/" + PICTURE_DIRECTORY);
		boolean exists = pictureDirectory.exists();
		if (!exists) {
			pictureDirectory.mkdirs();
		}
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, pictureDirectory);
		return imageF;
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
}
