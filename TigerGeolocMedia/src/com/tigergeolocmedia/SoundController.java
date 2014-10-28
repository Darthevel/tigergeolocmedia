package com.tigergeolocmedia;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;


/*
 * Classe qui "controle" la creation (l'enregistrement) d'un son
 * 
 */
public class SoundController extends MediaControllerBase {

	private static final String LOG_TAG = "AudioRecordTest";
	
	private Media media = null;

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
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	// Demarre l'enregistrement
	@Override
	public void record() {
		media = new Media(MediaType.SOUND); //Initialiser avec AUDIO_TYPE
		media.setName(Constants.SOUND_PREFIX + String.valueOf(System.nanoTime()));
		media.setPath(Constants.SOUND_DIRECTORY + "/" + media.getName());

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
