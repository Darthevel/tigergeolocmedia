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

public class PictureController implements MediaController {
	private String currentPicturePath;
	private Activity activity;
	

	public PictureController(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void record() {
		// Cr�ation du fichier o� la photo sera sauvegard�e.
		File pictureFile = null;
		try {
			pictureFile = createPictureFile();
			if (pictureFile == null) {
				return;
			}
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			currentPicturePath = pictureFile.getAbsolutePath();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
			
			activity.startActivityForResult(takePictureIntent, Constants.ACTION_TAKE_PICTURE);
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
		String imageFileName = Constants.IMAGE_PREFIX + timeStamp + "_";
		File pictureDirectory = new File(externalStorageDirectory + "/" + Constants.PICTURE_DIRECTORY);
		boolean exists = pictureDirectory.exists();
		if (!exists) {
			pictureDirectory.mkdirs();
		}
		File imageF = File.createTempFile(imageFileName, Constants.JPEG_FILE_SUFFIX, pictureDirectory);
		return imageF;
	}


	public String getCurrentPicturePath() {
		return currentPicturePath;
	}

	public void setCurrentPicturePath(String currentPicturePath) {
		this.currentPicturePath = currentPicturePath;
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
		if (currentPicturePath == null) {
			return null;
		}
		int photoW = 0;
		int photoH = 0;
		
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(getCurrentPicturePath(), bmOptions);
		photoW = bmOptions.outWidth;
		photoH = bmOptions.outHeight;

 		// Get the bitmap's EXIF orientation
		int orientationInDegrees = computeBitmapOriendegreesInDegrees(getCurrentPicturePath());
		

		
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
		Bitmap bitmap = BitmapFactory.decodeFile(getCurrentPicturePath(), bmOptions);
		
		
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
