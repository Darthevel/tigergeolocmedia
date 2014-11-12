package com.tigergeolocmedia;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tigergeolocmedia.util.Registry;

public class PictureActivityReadOnly extends ParentMenuActivity  {

	private TextView textViewDescription;
	
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
	private static void setPic(Media media, ImageView imageView) {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = imageView.getWidth();
		int targetH = imageView.getHeight();

		Bitmap bitmap = PictureController.computeBitmap(media, targetW,
				targetH, false);


		/* Associate the Bitmap to the ImageView */
		imageView.setImageBitmap(bitmap);
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
