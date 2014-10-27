package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Gestion de l'historique Media
 * Limitation du stockage des media dans le telephone
 * 
 **/
public class Historic {

	static final int MAX_LENGTH = 10;

	private List<Media> mediaList = new ArrayList<Media>(MAX_LENGTH);

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void add(Media media) {
		if (mediaList.size() == MAX_LENGTH)
			mediaList.remove(MAX_LENGTH - 1);
		mediaList.add(0, media);
	}

	public Historic() {
		super();
	}
	

}
