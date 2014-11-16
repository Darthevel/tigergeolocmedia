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

	private static final String LOG_TAG = "SoundController";
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

	public void setDescription(String description) {
		media.setDescription(description);
	}

	// Arrete l'enregistement
	public void stopRecording() {
		try {
			if (mRecorder != null) {
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "stopRecording() failed", e);
		}
	}

	// Demarre l'enregistrement
	@Override
	public void record() {
		try {
			File file = createFile();

			// Création du media
			media = new Media(MediaType.SOUND, file.getName(),
					file.getAbsolutePath(), "");
		} catch (IOException ioException) {
			Log.e(LOG_TAG, "File / Media creation failed");
		}
		// Creation du MediaRecorder, initialisation de ses parametre (Source de
		// l'enregistrement, format du fichier, localisation du fichier, type
		// d'encodage)
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(media.getPath());
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		// Demarrage de l'enregistrement
		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Start recording failed");
		}
	}

	// Stop la lecture (l'ecoute) du son
	@Override
	public void stop() {
		mPlayer.release();
		mPlayer = null;
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
			Log.e(LOG_TAG, "play() failed");
		}
	}
}
