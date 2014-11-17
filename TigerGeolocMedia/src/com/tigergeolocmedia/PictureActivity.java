package com.tigergeolocmedia;

import java.io.File;

import rx.Observable;
import rx.android.events.OnClickEvent;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.PictureController.VisualEffect;
import com.tigergeolocmedia.util.Registry;
import com.tigergeolocmedia.dba.HistoCrud;


/**
 * Activité gérant les images.
 * @author HumanBooster
 *
 */
public class PictureActivity extends ParentMenuActivity {

	private static final String VISUAL_EFFECT_KEY = "VISUAL_EFFECT_KEY";

	/**
	 * Saturation par défaut : une valeur de 2.0f donne une image très saturée.
	 */
	private static final float SATURATION = 2.0f;

	/**
	 * L'effet courant : initialisé à {@link VisualEffect#NONE}.
	 */
	private PictureController.VisualEffect visualEffect = VisualEffect.NONE;

	/**
	 * L'historique dans lequel l'image sera éventuellement sauvegardée.
	 */
	private Historic historic = null;
	
	/**
	 * Le {@link Menu} de l'activité.
	 */
	private Menu menu;

	/**
	 * Contrôleur d'images.
	 */
	private PictureController controller;

	/**
	 * L'{@link ImageView} dans laquelle l'image sera visualisée.
	 */
	private ImageView pictureView;

	/**
	 * L'{@link ImageView} dans laquelle s'effectue le preview de l'image sur laquelle l'effet BW est apliqué.
	 */
	private ImageView imageViewVisualEffectBW;
	
	/**
	 * L'{@link ImageView} dans laquelle s'effectue le preview de l'image sur laquelle l'effet SATURATION est apliqué.
	 */
	private ImageView imageViewVisualEffectSaturation;
	
	/**
	 * L'{@link ImageView} dans laquelle s'effectue le preview de l'image sur laquelle l'effet NONE est apliqué.
	 */
	private ImageView imageViewVisualEffectNone;

	/**
	 * L'{@link EditText} dans lequel l'utilisateur entre la description de l'image.
	 */
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

		PictureController controller = Registry
				.get(Constants.PICTURE_CONTROLLER);
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
						applyVisualEffectBW();

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

	/**
	 * Application de l'effet saturation ({@link VisualEffect#SATURATION}).
	 * @param manageScaleFactor 
	 */
	protected void applyVisualEffectsaturation(boolean manageScaleFactor) {
		applyVisualEffectsaturation(SATURATION, manageScaleFactor);
	}

	protected void applyVisualEffectsaturation(float saturation,
			boolean manageScaleFactor) {
		visualEffect = VisualEffect.SATURATION;
		// Bitmap bwBitmap =
		// PictureController.computeBitmapWithSaturationEffect(
		// controller.getMedia(), pictureView.getWidth(),
		// pictureView.getHeight(), saturation, manageScaleFactor);
		// pictureView.setImageBitmap(bwBitmap);
		setPic(controller.getMedia(), pictureView, visualEffect);
	}

	protected void applyVisualEffectNONE(boolean manageScaleFactor) {
		visualEffect = VisualEffect.NONE;
		// Bitmap bwBitmap = PictureController.computeBitmap(
		// controller.getMedia(), pictureView.getWidth(),
		// pictureView.getHeight(), manageScaleFactor);
		// pictureView.setImageBitmap(bwBitmap);
		setPic(controller.getMedia(), pictureView, visualEffect);
	}

	protected void applyVisualEffectBW() {
		visualEffect = VisualEffect.BW;
		// Bitmap bwBitmap = PictureController.computeBitmapWithBWEffect(
		// controller.getMedia(), pictureView.getWidth(),
		// pictureView.getHeight(), manageScaleFactor);
		// pictureView.setImageBitmap(bwBitmap);
		setPic(controller.getMedia(), pictureView, visualEffect);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);

		if (!itemSaveEnabled) {
			MenuItem item = menu.findItem(R.id.itemSave);
			disable();
			item.setEnabled(false);
		}
		if (!itemSaveAndSendEnabled) {
			MenuItem item = menu.findItem(R.id.itemSaveAndSend);
			disable();
			item.setEnabled(false);
		}
		return true;
	}

	/**
	 * Désactivation des contrôles suivants: <li>{@link #editTextDescription}
	 * <li>{@link #imageViewVisualEffectBW} <li>
	 * {@link #imageViewVisualEffectNone} <li>
	 * {@link #imageViewVisualEffectSaturation} En effet, une fois que l'image a
	 * été sauvegardée, il n'est plus possible, ni de modifier sa description,
	 * ni d'appliquer un effet dessus
	 */
	private void disable() {
		editTextDescription.setEnabled(false);
		imageViewVisualEffectBW.setEnabled(false);
		imageViewVisualEffectNone.setEnabled(false);
		imageViewVisualEffectSaturation.setEnabled(false);

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
			disable();

			return true;
		}
		if (id == R.id.itemSave) {
			save();

			// On vient de sauver l'image, on interdit une nouvelle sauvegarde.
			itemSaveEnabled = false;
			item.setEnabled(false);
			disable();
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

	/**
	 * Sauvegarde de l'image (du {@link Media}).
	 */
	private void save() {
		// Récuoération du media
		final Media media = controller.getMedia();

		// On colle la description dans le media.
		media.setDescription(editTextDescription.getText().toString());


		File file = new File(media.getPath());

		TigerBitmapFactory bitmapFactory = new TigerBitmapFactory(
				getApplicationContext(), file, null);
		bitmapFactory.setVisualEffect(visualEffect);

		Observable<Bitmap> observable = Observable.create(bitmapFactory);

		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Bitmap>() {

					@Override
					public void call(Bitmap bitmap) {
						if (bitmap != null) {
							// On sauve l'image (avec l'effet).
							controller.save(media, bitmap);

							// On sauve l'historique
							controller.save(historic);
						}
					}

				});

		
		//Using SQLite
		HistoCrud db = new HistoCrud();
		db.create(this, controller.getMedia());
		String log = "ID = "+ controller.getMedia().getId() +
				", NAME = "+ controller.getMedia().getName() +
				", MEDIATYPE = "+ controller.getMedia().getType();
		Log.d("test get:Media", log);

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

	protected void restoreImage() {
		Media media = controller.getMedia();

		if (media != null) {
			if (media.getType().equals(MediaType.PICTURE)) {
				setPic(media);

			}
		}

	}

	private void setPic(final Media media, final ImageView imageView,
			VisualEffect effect) {

		File file = new File(media.getPath());

		TigerBitmapFactory bitmapFactory = new TigerBitmapFactory(
				getApplicationContext(), file, imageView);
		bitmapFactory.setVisualEffect(effect);

		Observable<Bitmap> observable = Observable.create(bitmapFactory);

		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Bitmap>() {

					@Override
					public void call(Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}

				});

	}


	/**
	 * Remplit les {@link ImageView}s suivantes:
	 * <li>{@link #pictureView}
	 * <li>{@link #imageViewVisualEffectBW}
	 * <li>{@link #imageViewVisualEffectSaturation}
	 * <li>{@link #imageViewVisualEffectNone}
	 * @param media
	 */
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
		outState.putBoolean(ITEM_SAVE_ENABLED_KEY, itemSaveEnabled);
		outState.putBoolean(ITEM_SAVE_AND_SEND_ENABLED_KEY,
				itemSaveAndSendEnabled);
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
		itemSaveAndSendEnabled = savedInstanceState
				.getBoolean(ITEM_SAVE_AND_SEND_ENABLED_KEY);
		if (!itemSaveEnabled) {
			disable();
		}
	}

}
