package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

/**
 * 
 * Gestion de l'historique Media
 * Limitation du stockage des medias dans le telephone.
 * Cette classe implémente le pattern Singleton.
 * 
 **/
public class Historic {
	
	/**
	 * Instance unique du singleton Historic.
	 */
	private static Historic historicInstance;
	
	
	/**
	 * Context de l'application.
	 */
	private static Context context;

	public static final int MAX_LENGTH = 10;
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	String historicKey = "historic";
	SharedPreferences prefs = null;

	private List<Media> mediaList = new ArrayList<Media>(MAX_LENGTH);

	public List<Media> getMediaList() {
		return mediaList;
	}

	/**
	 * Ajout d'un {@link Media} dans l'historique.
	 * @param media
	 */
	public void add(Media media) {
		if (mediaList.size() == MAX_LENGTH)
			mediaList.remove(MAX_LENGTH - 1);
		mediaList.add(0, media);
		saveHistoric();
	}

	/**
	 * Sauvegarde de l'historique.
	 */
	public void saveHistoric()
	{
		Editor editor = prefs.edit();
		int historicNumber = 1;
		for (Media media : mediaList)
		{
			editor.putString(historicKey + historicNumber, media.getName() + ";" + media.getType().toString() + ";" + media.getPath() + ";" + media.getDescription());
			historicNumber++;
		}
		editor.commit();
		Toast.makeText(context, "" + mediaList.size() + " stockés dans l'historique", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Vide l'historique.
	 */
	public void clear() {
		mediaList.clear();
		saveHistoric();
	}
	
	/**
	 * Constructeur privé enpêchant l'instantiation.
	 * Pour accéder au singleton Historic, il faut utiliser la méthode sstatique {@link #getInstance(Context)}.
	 * @param context
	 */
	private Historic(Context context) {		
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
		
		if (result[1].equals("PICTURE"))
			media.setType(MediaType.PICTURE);
		else if (result[1].equals("MOVIE"))
			media.setType(MediaType.MOVIE);
		else if (result[1].equals("SOUND"))
			media.setType(MediaType.SOUND);

		media.setPath(result[2]);
		media.setDescription(result[3]);
		mediaList.add(media);
	}
	
	/**
	 * Méthode permettant d'accéder au singleton Historic
	 * @param _context le {@link Context} de l'application.
	 * @return
	 */
	public static Historic getInstance(Context _context) {
		if (historicInstance == null) {
			context = _context;
			historicInstance = new Historic(context);
		}
		return historicInstance;
	}
	
	/**
	 * Renvoie le dernier {@link Media} enregistré dans l'historique.
	 * @return
	 */
	public Media getLatestMedia() {
		if (mediaList.isEmpty()) {
			return null;
		}
		else {
			Media latestMedia = mediaList.get(0);
			return latestMedia;
		}
	}
}
