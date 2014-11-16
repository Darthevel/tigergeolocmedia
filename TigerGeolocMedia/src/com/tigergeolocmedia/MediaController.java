package com.tigergeolocmedia;

public interface MediaController {
	
	/**
	 * Enregistrement du media.
	 */
	public void record();
	
	/**
	 * Lecture du media.
	 */
	public void play();
	
	/**
	 * Arrêt de l'enregistrement ou de la visualisation du media.
	 */
	public void stop();
}
