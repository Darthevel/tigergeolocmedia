package com.tigergeolocmedia;

import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class PlaySoundService extends IntentService {
	private String path;
	private MediaPlayer mPlayer;
	
	  /*
	   * A constructor is required, and must call the super IntentService(String)
	   * constructor with a name for the worker thread.
	   */
	public PlaySoundService(String name) {
		super(name);
	}
	
	public PlaySoundService() {
		super("PlaySoundService");
	}
		
	  /*
	   * The IntentService calls this method from the default worker thread with
	   * the intent that started the service. When this method returns, IntentService
	   * stops the service, as appropriate.
	   */
	@Override
	protected void onHandleIntent(Intent intent) {
		path = intent.getStringExtra("path");
		play();
	}
	
	public void play() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(path);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e("SoundService Play()", "play() failed");
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
}
