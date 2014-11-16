package com.tigergeolocmedia;

import java.io.File;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.util.Registry;

/**
 * Activity gérant la visualisation d'une image déjà sauvegardée : voilà le pourquoi du "ReadOnly".
 * @author HumanBooster
 *
 */
public class PictureActivityReadOnly extends ParentMenuActivity  {

	/**
	 * Le {@link TextView} contenant la description de l'image (du {@link Media}).
	 */
	private TextView textViewDescription;
	
	/**
	 * La {@link TextView} contenant l'image.
	 */
	private ImageView pictureView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_activity_read_only);
		
		PictureController controller = Registry.get(Constants.PICTURE_CONTROLLER);

		textViewDescription = (TextView) findViewById(R.id.textViewDescription);
		textViewDescription.setText(controller.getMedia().getDescription());
		textViewDescription.setEnabled(false);
		
		pictureView = (ImageView) findViewById(R.id.pictureViewPicture);
		
		// Pas besoin d'ActionBar ici.
		getActionBar().hide();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_activity_read_only, menu);
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
	
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		restoreImage();
	}
	
	protected void restoreImage() {
		PictureController controller = Registry.get(Constants.PICTURE_CONTROLLER);
		Media media = controller.getMedia();

		if (media != null) {
			if (media.getType().equals(MediaType.PICTURE)) {
				setPic(media, pictureView);

			}
		}

	}

	private void setPic(Media media, ImageView imageView) {
		File file =  new File(media.getPath());
		boolean exists = file.exists();
		if (exists) {
		Picasso.with(getApplicationContext()).load(file).error(R.drawable.error).into(imageView);
		}

	}


	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		PictureController controller = Registry.get(Constants.PICTURE_CONTROLLER);

		textViewDescription = (TextView) findViewById(R.id.textViewDescription);
		textViewDescription.setText(controller.getMedia().getDescription());
		textViewDescription.setEnabled(false);
	}



}
