package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class PictureController extends MediaControllerBase {
	private Activity activity;
	

	public PictureController(String prefix, String suffix, String directory, Activity activity) {
		super(prefix, suffix, directory);
		this.activity = activity;
	}

	@Override
	public void record() {
		// Cr�ation du fichier o� la photo sera sauvegard�e.
		File pictureFile = null;
		try {
			pictureFile = createFile();
			if (pictureFile == null) {
				return;
			}
			Media media = new Media(MediaType.PICTURE);
			this.media = media;
			media.setPath(pictureFile.getAbsolutePath());
			media.setName(pictureFile.getName());

			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
			
			activity.startActivityForResult(takePictureIntent, Constants.ACTION_TAKE_PICTURE);
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
	
	/**
	 * Construit un {@link Bitmap} à partir du fichier image {@link #currentPicturePath}.
	 * Si {@link #currentPicturePath} vaut <b>null</b>, le {@link Bitmap} sera <b>null</b>.
	 * @param targetW largeur du {@link Bitmap}. 
	 * @param targetH hauteur du {@link Bitmap}.
	 * @return
	 */
	public Bitmap computeCurrentBitmap(int targetW, int targetH) {
		if (media == null) {
			return null;
		}
		int photoW = 0;
		int photoH = 0;
		
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(media.getPath(), bmOptions);
		photoW = bmOptions.outWidth;
		photoH = bmOptions.outHeight;

 		// Get the bitmap's EXIF orientation
		int orientationInDegrees = computeBitmapOriendegreesInDegrees(media.getPath());
		

		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(media.getPath(), bmOptions);
		
		
		if (orientationInDegrees != 0) {
			Matrix matrix = new Matrix();
			matrix.preRotate(orientationInDegrees);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
		return bitmap;
	}
	
	/**
	 * @param currentPicturePath
	 * @return
	 */
	private int computeBitmapOriendegreesInDegrees(String currentPicturePath) {
		try {
			ExifInterface exif = new ExifInterface(currentPicturePath);
			int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			int bitmapOriendegreesInDegrees = exifToDegrees(exifOrientation);
			return bitmapOriendegreesInDegrees;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 
	 * @param exifOrientation
	 * @return
	 */
	private static int exifToDegrees(int exifOrientation) {        
	    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
	    return 0;    
	 }



}
