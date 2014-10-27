package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * 
 * Gestion de l'historique Media
 * Limitation du stockage des medias dans le telephone
 * 
 **/
public class Historic {

	public static final int MAX_LENGTH = 10;
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	String historicKey = "historic";
	SharedPreferences prefs = null;

	private List<Media> mediaList = new ArrayList<Media>(MAX_LENGTH);

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void add(Media media) {
		if (mediaList.size() == MAX_LENGTH)
			mediaList.remove(MAX_LENGTH - 1);
		mediaList.add(0, media);
		saveHistoric();
	}

	public void saveHistoric()
	{
		Editor editor = prefs.edit();
		int historicNumber = 1;
		for (Media media : mediaList)
		{
			editor.putString(historicKey + historicNumber, media.getName() + ";" + media.getType() + ";" + media.getPath() + ";" + media.getDescription());
			historicNumber++;
		}
		editor.commit();
	}
	
	public Historic(Context context) {		
		super();
		
		Media media = null;
		
		prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
		for (int i = 1; i <= 10; i++)
		{
			if (prefs.contains(historicKey + i))
				setMedia(prefs.getString(historicKey + i, null));
		}
	}
	
	public void setMedia(String info)
	{
		String[] result = null;
		Media media = new Media();
		
		result = info.split(";");
		media.setName(result[0]);
		//media.setType(result[1]);
		media.setPath(result[2]);
		media.setDescription(result[3]);
		mediaList.add(media);
	}
}
