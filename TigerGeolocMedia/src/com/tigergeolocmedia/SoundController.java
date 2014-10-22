package com.tigergeolocmedia;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class SoundController {

	private static final String LOG_TAG = "AudioRecordTest";
	
	private String mFileName = null;
	
	public boolean isRecording = false;
	public boolean isPlaying = false;
	
	private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
	
	public void stopReccording()
	{
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
	}
	
	public void startReccording()
	{
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        
		mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try 
        {
            mRecorder.prepare();
            mRecorder.start();
        } 
        catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
        }
	}
	
	public void stopPlaying()
	{
		mPlayer.release();
        mPlayer = null;
	}
	
	public void startPlaying()
	{
		mPlayer = new MediaPlayer();
        try 
        {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } 
        catch (IOException e) 
        {
            Log.e(LOG_TAG, "prepare() failed");
        }
	}	
}
