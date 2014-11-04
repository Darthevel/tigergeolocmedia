package com.tigergeolocmedia;

import rx.android.events.OnClickEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.tigergeolocmedia.PictureController.VisualEffect;

public class PictureActivity extends ParentMenuActivity {

	private static final String VISUAL_EFFECT_KEY = "VISUAL_EFFECT_KEY";

	/**
	 * Saturation par défaut : une valeur de 2.0f donne une image très saturée.
	 */
	private static final float SATURATION = 2.0f;

	private PictureController.VisualEffect visualEffect = VisualEffect.NONE;

	private Historic historic = null;
	private Menu menu;

	/**
	 * Contrôleur d'images.
	 */
	private PictureController controller;

	private ImageView pictureView;

	private ImageView imageViewVisualEffectBW;
	private ImageView imageViewVisualEffectSaturation;
	private ImageView imageViewVisualEffectNone;

	private EditText editTextDescription;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);

		historic = Historic.getInstance(getApplicationContext());

		Intent intent = getIntent();
		PictureController controller = intent
				.getParcelableExtra(Constants.PICTURE_CONTROLLER_PARCELABLE);
		this.controller = controller;
		pictureView = (ImageView) findViewById(R.id.pictureViewPicture);

		// Effet NONE (l'image initiale reste à l'identique) début
		imageViewVisualEffectNone = (ImageView) findViewById(R.id.imageViewVisualEffectNone);
		ViewObservable.clicks(imageViewVisualEffectNone).subscribe(
				new Action1<OnClickEvent>() {

					@Override
					public void call(OnClickEvent arg0) {
						applyVisualEffectNONE(true);

					}
				});
		// Effet NONE (l'image initiale reste à l'identique) fin

		// Effet BW début
		imageViewVisualEffectBW = (ImageView) findViewById(R.id.imageViewVisualEffectBW);
		ViewObservable.clicks(imageViewVisualEffectBW).subscribe(
				new Action1<OnClickEvent>() {

					@Override
					public void call(OnClickEvent arg0) {
						applyVisualEffectBW(true);

					}
				});

		// Effet BW fin

		// Effet SATURATION Début
		imageViewVisualEffectSaturation = (ImageView) findViewById(R.id.imageViewVisualEffectSaturation);
		ViewObservable.clicks(imageViewVisualEffectSaturation).subscribe(
				new Action1<OnClickEvent>() {

					@Override
					public void call(OnClickEvent arg0) {
						applyVisualEffectsaturation(SATURATION, true);

					}
				});

		// Effet SATURATION Fin

		editTextDescription = (EditText) findViewById(R.id.editTextDescription);

	}

	protected void applyVisualEffectsaturation(boolean manageScaleFactor) {
		applyVisualEffectsaturation(SATURATION, manageScaleFactor);
	}

	protected void applyVisualEffectsaturation(float saturation,
			boolean manageScaleFactor) {
		visualEffect = VisualEffect.SATURATION;
		Bitmap bwBitmap = PictureController.computeBitmapWithSaturationEffect(
				controller.getMedia(), pictureView.getWidth(),
				pictureView.getHeight(), saturation, manageScaleFactor);
		pictureView.setImageBitmap(bwBitmap);
	}

	protected void applyVisualEffectNONE(boolean manageScaleFactor) {
		visualEffect = VisualEffect.NONE;
		Bitmap bwBitmap = PictureController.computeBitmap(
				controller.getMedia(), pictureView.getWidth(),
				pictureView.getHeight(), manageScaleFactor);
		pictureView.setImageBitmap(bwBitmap);
	}

	protected void applyVisualEffectBW(boolean manageScaleFactor) {
		visualEffect = VisualEffect.BW;
		Bitmap bwBitmap = PictureController.computeBitmapWithBWEffect(
				controller.getMedia(), pictureView.getWidth(),
				pictureView.getHeight(), manageScaleFactor);
		pictureView.setImageBitmap(bwBitmap);
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

	private void send() {
		// TODO Auto-generated method stub

	}

	private Bitmap computeCurrentBitmap() {
		Bitmap bitmap = PictureController.computeBitmap(controller.getMedia(),
				pictureView.getWidth(), pictureView.getHeight(), false);

		if (visualEffect.equals(VisualEffect.BW)) {
			bitmap = PictureController.computeBitmapWithBWEffect(bitmap, false);
		} else if (visualEffect.equals(VisualEffect.SATURATION)) {
			bitmap = PictureController.computeBitmapWithSaturationEffect(
					bitmap, SATURATION, false);
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

	/*
	 * (non-Javadoc)
	 * 
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

	private static void setPic(Media media, ImageView imageView,
			VisualEffect effect, boolean manageScaleFactor) {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = imageView.getWidth();
		int targetH = imageView.getHeight();

		Bitmap bitmap = PictureController.computeBitmap(media, targetW,
				targetH, manageScaleFactor);

		if (effect.equals(VisualEffect.BW)) {
			bitmap = PictureController.computeBitmapWithBWEffect(bitmap,
					manageScaleFactor);
		} else if (effect.equals(VisualEffect.SATURATION)) {
			bitmap = PictureController.computeBitmapWithSaturationEffect(
					bitmap, SATURATION, manageScaleFactor);
		}

		/* Associate the Bitmap to the ImageView */
		imageView.setImageBitmap(bitmap);
	}

	private void setPic(Media media) {

		setPic(media, pictureView, visualEffect, true);
		setPic(media, imageViewVisualEffectBW, VisualEffect.BW, true);
		setPic(media, imageViewVisualEffectSaturation, VisualEffect.SATURATION,
				true);
		setPic(media, imageViewVisualEffectNone, VisualEffect.NONE, true);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(VISUAL_EFFECT_KEY, visualEffect.toString());
		outState.putBoolean(ITEM_SAVE_ENABLED_KEY, itemSaveEnabled);
		outState.putBoolean(ITEM_SAVE_AND_SEND_ENABLED_KEY, itemSaveAndSendEnabled);
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
		itemSaveEnabled = savedInstanceState.getBoolean(ITEM_SAVE_ENABLED_KEY);
		itemSaveAndSendEnabled = savedInstanceState.getBoolean(ITEM_SAVE_AND_SEND_ENABLED_KEY);
	}
	
	


}
