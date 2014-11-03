package com.tigergeolocmedia;

import rx.android.events.OnClickEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;

import com.tigergeolocmedia.PictureController.VisualEffect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class PictureActivity extends ParentMenuActivity {
	
	private static final String VISUAL_EFFECT_KEY = "VISUAL_EFFECT_KEY";
	
	
	/**
	 * Saturation par défaut : une valeur de 2.0f donne une image très saturée.
	 */
	private static final float SATURATION = 2.0f;

	
	private PictureController.VisualEffect visualEffect = VisualEffect.NONE;
	
	private Historic historic = null;

	
	
	/**
	 * Contrôleur d'images.
	 */
	private PictureController controller;
	
	private ImageView pictureView;
	
	private ImageView imageViewVisualEffectBW;
	private ImageView imageViewVisualEffectSaturation;
	private ImageView imageViewVisualEffectNone;
	
	private EditText editTextDescription;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		
		historic = Historic.getInstance(getApplicationContext());
		
		Intent intent = getIntent();
		PictureController controller = intent.getParcelableExtra(Constants.PICTURE_CONTROLLER_PARCELABLE);
		this.controller = controller;
		pictureView = (ImageView) findViewById(R.id.pictureViewPicture);
		
		// Effet NONE (l'image initiale reste à l'identique) début
		imageViewVisualEffectNone = (ImageView) findViewById(R.id.imageViewVisualEffectNone);
		ViewObservable.clicks(imageViewVisualEffectNone).subscribe(new Action1<OnClickEvent>() {

			@Override
			public void call(OnClickEvent arg0) {
				applyVisualEffectNONE();
				
			}
		});
		// Effet NONE (l'image initiale reste à l'identique) fin

		// Effet BW début
		imageViewVisualEffectBW = (ImageView) findViewById(R.id.imageViewVisualEffectBW);
		ViewObservable.clicks(imageViewVisualEffectBW).subscribe(new Action1<OnClickEvent>() {

			@Override
			public void call(OnClickEvent arg0) {
				applyVisualEffectBW();
				
			}
		});

		// Effet BW fin
		
		// Effet SATURATION Début
		imageViewVisualEffectSaturation = (ImageView) findViewById(R.id.imageViewVisualEffectSaturation);
		ViewObservable.clicks(imageViewVisualEffectSaturation).subscribe(new Action1<OnClickEvent>() {

			@Override
			public void call(OnClickEvent arg0) {
				applyVisualEffectsaturation(SATURATION);
				
			}
		});
		
		// Effet SATURATION Fin
		
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		
		
	}

	protected void applyVisualEffectsaturation() {
		applyVisualEffectsaturation(SATURATION);
	}
	protected void applyVisualEffectsaturation(float saturation) {
		visualEffect = VisualEffect.SATURATION;
		Bitmap bwBitmap = PictureController.computeBitmapWithSaturationEffect(controller.getMedia(), pictureView.getWidth(), pictureView.getHeight(), saturation);
		pictureView.setImageBitmap(bwBitmap);
	}

	protected void applyVisualEffectNONE() {
		visualEffect = VisualEffect.NONE;
		Bitmap bwBitmap = PictureController.computeBitmap(controller.getMedia(), pictureView.getWidth(), pictureView.getHeight());
		pictureView.setImageBitmap(bwBitmap);
	}

	protected void applyVisualEffectBW() {
		visualEffect = VisualEffect.BW;
		Bitmap bwBitmap = PictureController.computeBitmapWithBWEffect(controller.getMedia(), pictureView.getWidth(), pictureView.getHeight());
		pictureView.setImageBitmap(bwBitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
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
			return true;
		}
		if (id == R.id.itemSave) {
			save();
			return true;
		}
		if (id == R.id.itemCancel) {
			cancel();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void cancel() {
		// TODO Auto-generated method stub
		
	}

	private void saveAndSend() {
		// Sauvegarde du media
		save();
		
		// Envoi du media
		send();
		
	}

	private void send() {
		// TODO Auto-generated method stub
		
	}
	
	private Bitmap computeCurrentBitmap() {
		Bitmap bitmap = PictureController.computeBitmap(controller.getMedia(), pictureView.getWidth(), pictureView.getHeight());
		
		if (visualEffect.equals(VisualEffect.BW)) {
			bitmap = PictureController.computeBitmapWithBWEffect(bitmap);
		}
		else if (pictureView.equals(VisualEffect.SATURATION)) {
			bitmap = PictureController.computeBitmapWithSaturationEffect(bitmap, SATURATION);
		}
		return bitmap;

	}

	private void save() {
		// Récuoération du media
		Media media = controller.getMedia();
		
		// On colle la description dans le media.
		media.setDescription(editTextDescription.getText().toString());
		
		// Récup du bitmap courant
		Bitmap currentBitmap = computeCurrentBitmap();
		
		// On sauve l'image (avec l'effet).
		controller.save(media, currentBitmap);
		
		
		// On sauve l'historique
		controller.save(historic);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// 
		restoreImage();
	}
	
	private void restoreImage() {
		Media media = controller.getMedia();
		
		if (media != null) {
			if (media.getType().equals(MediaType.PICTURE)) {
				setPic(media);
				
			}
		}
		
	}
	
	private static void setPic(Media media, ImageView imageView, VisualEffect effect) {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = imageView.getWidth();
		int targetH = imageView.getHeight();
		
		Bitmap bitmap = PictureController.computeBitmap(media, targetW, targetH);
		
		if (effect.equals(VisualEffect.BW)) {
			bitmap = PictureController.computeBitmapWithBWEffect(bitmap);
		}
		else if (effect.equals(VisualEffect.SATURATION)) {
			bitmap = PictureController.computeBitmapWithSaturationEffect(bitmap, SATURATION);
		}
		
		/* Associate the Bitmap to the ImageView */
		imageView.setImageBitmap(bitmap);
	}
	

	private void setPic(Media media) {
		
		setPic(media, pictureView, visualEffect);
		setPic(media, imageViewVisualEffectBW, VisualEffect.BW);
		setPic(media, imageViewVisualEffectSaturation, VisualEffect.SATURATION);
		setPic(media, imageViewVisualEffectNone, VisualEffect.NONE);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(VISUAL_EFFECT_KEY, visualEffect.toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		String effect = savedInstanceState.getString(VISUAL_EFFECT_KEY);
		switch (effect) {
		case "NONE":
			visualEffect = VisualEffect.NONE;
			break;
		case "BW":
			visualEffect = VisualEffect.BW;
			break;
		case "SATURATION":
			visualEffect = VisualEffect.SATURATION;
			break;
		default:
			visualEffect = VisualEffect.NONE;
			break;
		}

	}
	
	


}
