package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;

import com.squareup.picasso.Picasso;

public class ListElementFactory implements Observable.OnSubscribe<HistoricElement>{

	private Context context;
	
	public ListElementFactory(Context context) {
		super();
		this.context = context;
	}
	
	
	private Bitmap computeThumbNail(Media media, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		if (media.getType().equals(MediaType.PICTURE)) {
			bitmap = BitmapFactory.decodeFile(media.getPath(), options);
			
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 80, 80, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		    
		}
		else if (media.getType().equals(MediaType.MOVIE)) {
			bitmap = ThumbnailUtils.createVideoThumbnail(media.getPath(), Thumbnails.MICRO_KIND);

		}
		else if (media.getType().equals(MediaType.SOUND)) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound);

		}
		return bitmap;
	}

	@Override
	public void call(Subscriber<? super HistoricElement> arg0) {
		Historic historic = Historic.getInstance(context);
		
		try{
			/*
			 *  Pour chaque Media dans la liste de Media : 
			 *  Recupere les informations concernant le media, charge le media en memoire
			 *  Et cree un HistoricElement en lui remplissant ses information
			 */
			
			for (Media m : historic.getMediaList()) {
				
				// options.inSampleSize = 8 permet de réduire la taille du bitmap obtenu.
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				Bitmap myBitmap = computeThumbNail(m, options);
				
				HistoricElement element = new HistoricElement(m.getName(), m.getDescription(), m.getType(), myBitmap);
				arg0.onNext(element);
			}
			arg0.onCompleted();
		}
		catch (Exception e){
			arg0.onError(e);
		}

	}	
}


