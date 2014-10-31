package com.tigergeolocmedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rx.Observable;
import rx.Subscriber;

public class ListElementFactory implements Observable.OnSubscribe<HistoricElement>{

	private Context context;
	
	public ListElementFactory(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void call(Subscriber<? super HistoricElement> arg0) {
		Historic historic = Historic.getInstance(context);
		
		try{
			for (Media m : historic.getMediaList()) {
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				Bitmap myBitmap = BitmapFactory.decodeFile(m.getPath(), options);
				
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


//Observer#onNext(Object item) for each item 
//and either Observer#onCompleted() or Observer#onError(Throwable t)

