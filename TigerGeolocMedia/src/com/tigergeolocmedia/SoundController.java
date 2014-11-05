package com.tigergeolocmedia;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.tigergeolocmedia.Media.MediaType;


/*
 * Classe qui "controle" la creation (l'enregistrement) d'un son
 * 
 */
public class SoundController extends MediaControllerBase {

	private static final String LOG_TAG = "AudioRecordTest";
	private boolean isRecording = false;
	private boolean isPlaying = false;

	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;
	
	
	
	public SoundController(String prefix, String suffix, String directory) {
		super(prefix, suffix, directory);
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}

	// Arrete l'enregistement
	public void stopRecording() {
		try {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		}
		catch (Exception e) {
			Log.e(LOG_TAG, "stopRecording() failed", e);
		}
	}

	// Demarre l'enregistrement
	@Override
	public void record() {	
		try {
		File file = createFile();
		
		// Création du media
		media = new Media(MediaType.SOUND, file.getName(), file.getAbsolutePath(), ""); 	
		}
		catch (IOException ioException) {
			Log.e(LOG_TAG, "record() failed");

		}
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(media.getPath());
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}
	
	public void setDescription(String description){
		media.setDescription(description);
	}
	
	// Stop la lecteur (l'ecoute) du son
	@Override
	public void stop() {
		mPlayer.release();
		mPlayer = null;
		//TODO Function pour ajouter une description ou un commentaire
	}

	// Lance la lecture (l'ecoute) du son
	@Override
	public void play() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(media.getPath());
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}
}
