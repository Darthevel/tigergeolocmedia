package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tigergeolocmedia.PictureController.VisualEffect;

public class TigerBitmapFactory implements Observable.OnSubscribe<Bitmap> {
	/**
	 * Saturation par défaut : une valeur de 2.0f donne une image très saturée.
	 */
	private static final float SATURATION = 2.0f;

	private Context context;

	private File bitmapFile;
	private ImageView imageView;

	private VisualEffect visualEffect = null;

	@Override
	public void call(Subscriber<? super Bitmap> arg0) {
		try {

			Bitmap bitmap = null;
			if (imageView != null) {
				
				int imageWidth = imageView.getWidth();
				int imageHeight = imageView.getHeight();
				
				bitmap = Picasso.with(context).load(bitmapFile)
						.resize(imageView.getWidth(), imageView.getHeight())
						.centerInside().get();
			} else {
				try {
					// Attention ! Ici, risque de OutOfMemoryException !
					// On récupère les dimensions du bitmap
					BitmapFactory.Options bmOptions = new BitmapFactory.Options();
					bmOptions.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(
							bitmapFile.getAbsolutePath(), bmOptions);
					
					// les dimensions du bitmap donc
					int bitmapWidth = bmOptions.outWidth;
					int bitmapHeight = bmOptions.outHeight;
					
					// On les divise par 2
					bitmapWidth = bitmapWidth / 2;
					bitmapHeight = bitmapHeight / 2;

					// On calcule un nouveau bitmap dont les dimensions sont divisées par 2...
					bitmap = Picasso.with(context).load(bitmapFile).resize(bitmapWidth, bitmapHeight).get();
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			}

			if (bitmap != null) {
				if (visualEffect != null) {
					if (visualEffect.equals(VisualEffect.BW)) {
						bitmap = applyBWEffect(bitmap);
					} else if (visualEffect.equals(VisualEffect.SATURATION)) {
						bitmap = applySaturationEffect(bitmap);
					}
				}
			}

			arg0.onNext(bitmap);
			arg0.onCompleted();
		} catch (IOException e) {
			arg0.onError(e);
			e.printStackTrace();
		}
	}

	private Bitmap applySaturationEffect(Bitmap source) {
		Bitmap bmpSaturated = Bitmap.createBitmap(source.getWidth(),
				source.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpSaturated);
		ColorMatrix ma = new ColorMatrix();
		ma.setSaturation(SATURATION);
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(ma));
		canvas.drawBitmap(source, 0, 0, paint);
		return bmpSaturated;
	}

	private Bitmap applyBWEffect(Bitmap source) {
		Bitmap bmpMonochrome = Bitmap.createBitmap(source.getWidth(),
				source.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpMonochrome);
		ColorMatrix ma = new ColorMatrix();
		ma.setSaturation(0);
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(ma));
		canvas.drawBitmap(source, 0, 0, paint);
		return bmpMonochrome;
	}

	public TigerBitmapFactory(Context context, File bitmapFile,
			ImageView imageView) {
		super();
		this.context = context;
		this.bitmapFile = bitmapFile;
		this.imageView = imageView;
	}

	public VisualEffect getVisualEffect() {
		return visualEffect;
	}

	public void setVisualEffect(VisualEffect visualEffect) {
		this.visualEffect = visualEffect;
	}

}
