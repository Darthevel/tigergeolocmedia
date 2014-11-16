package com.tigergeolocmedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

public class PictureController extends MediaControllerBase {

	/**
	 * 3 types d'effet :
	 * <li> {@link #BW} : l'image passe en noir et blanc.
	 * <li> {@link #SATURATION} : l'image devient <b>très</b> saturée.
	 * <li> {@link #NONE} : pas d'effet appliqué sur l'image.
	 * @author HumanBooster
	 *
	 */
	public enum VisualEffect {
		NONE, BW, SATURATION
	};
	
	/**
	 * Qualité de compression par défaut.
	 */
	private static int COMPRESSION_QUALITY = 40;

	/**
	 * L'activité courante (celle qui utilise le contrôleur).
	 */
	private Activity activity;

	/**
	 * Constructeur.
	 * @param prefix
	 * @param suffix
	 * @param directory
	 * @param activity
	 */
	public PictureController(String prefix, String suffix, String directory,
			Activity activity) {
		super(prefix, suffix, directory);
		this.activity = activity;
	}

	@Override
	public void record() {
		// Création du fichier où la photo sera sauvegardée.
		File pictureFile = null;
		try {
			pictureFile = createFile();
			if (pictureFile == null) {
				return;
			}
			Media media = new Media(MediaType.PICTURE, pictureFile.getName(),
					pictureFile.getAbsolutePath(), "");
			this.media = media;

			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);

			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(pictureFile));

			activity.startActivityForResult(takePictureIntent,
					Constants.ACTION_TAKE_PICTURE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	
	public static final Parcelable.Creator<PictureController> CREATOR = new Parcelable.Creator<PictureController>() {

		@Override
		public PictureController createFromParcel(Parcel source) {

			String prefix = source.readString();
			String suffix = source.readString();
			String directory = source.readString();

			PictureController pictureController = new PictureController(prefix,
					suffix, directory, null);

			String type = source.readString();

			if (type != null) {
				String name = source.readString();
				String path = source.readString();
				String description = source.readString();

				Media media = new Media(MediaType.PICTURE, name, path,
						description);
				pictureController.setMedia(media);
			}

			// TODO Auto-generated method stub
			return pictureController;
		}

		@Override
		public PictureController[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

	};

	/**
	 * Sauvegarde du {@link Media} (champ #media).
	 * @param historic
	 */
	public void save(Historic historic) {
		if (media != null) {
			historic.add(media);
		}

	}

	public void save(Media media, Bitmap bitmap) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		File f = new File(media.getPath());
		// write the bytes in file

		try {
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());

			// remember close de FileOutput
			fo.close();
		}

		catch (Exception exception) {
			exception.printStackTrace();

		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	

}
