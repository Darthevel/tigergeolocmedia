package com.tigergeolocmedia;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class SoundController {

	private static final String LOG_TAG = "AudioRecordTest";
	
	private Media media = null;

	private boolean isRecording = false;
	private boolean isPlaying = false;

	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;
	
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

	public void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	public void startRecording() {
		media = new Media(MediaType.SOUND); //Initialiser avec AUDIO_TYPE	
		
//		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//		mFileName += "/audiorecordtest.3gp";

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

	public void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
		//TODO Function pour ajouter une description ou un commentaire
	}

	public void startPlaying() {
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
