package com.tigergeolocmedia;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {

	private static final String LOG_TAG = "AudioRecordTest";

	private boolean isReccording = false;
	private boolean isPlaying = false;
	
	private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
	
	private Button reccordButton = null;
	private Button playButton = null;
	
	private String mFileName = null;
	
	public void stopReccording()
	{
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
	}
	
	public void startReccording()
	{
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
	
	public void reccord(View v)
	{
		if (isReccording)
		{
			reccordButton.setText(R.string.reccord);
			stopReccording();
			isReccording = false;
		}
		else
		{
			reccordButton.setText(R.string.stopReccord);
			startReccording();
			isReccording = true;
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
	
	public void playSound(View v)
	{
		if (isPlaying)
		{
			playButton.setText(R.string.play);
			stopPlaying();
			isPlaying = false;
		}
		else
		{
			playButton.setText(R.string.stop);
			startPlaying();
			isPlaying = true;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		reccordButton = (Button) findViewById(R.id.reccordButton);
		playButton = (Button) findViewById(R.id.playButton);
		
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
