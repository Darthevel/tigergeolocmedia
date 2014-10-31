package com.tigergeolocmedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SoundActivity extends ParentMenuActivity {

	private Button recordButton = null;
	private Button playButton = null;
	private EditText description;
	private Historic historic = null;

	/**
	 * Contrôleur de son.
	 */
	private SoundController soundController = new SoundController(Constants.SOUND_PREFIX, Constants.SOUND_SUFFIX, Constants.SOUND_DIRECTORY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		
		historic = Historic.getInstance(getApplicationContext());
		recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				record();
			}
		});

		playButton = (Button) findViewById(R.id.playSoundButton);
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSound();
			}
		});
		description = (EditText) findViewById(R.id.soundDescription);
	}

	public void record() {
		if (soundController.isRecording()) {
			recordButton.setText(R.string.record);
			soundController.stopRecording();
			soundController.setRecording(false);
		} else {
			recordButton.setText(R.string.stopRecord);
			soundController.record();
			soundController.setRecording(true);
		}
	}

	public void playSound() {
		if (soundController.isPlaying()) {
			playButton.setText(R.string.play);
			soundController.stop();
			soundController.setPlaying(false);
		} else {
			playButton.setText(R.string.stop);
			soundController.play();
			soundController.setPlaying(true);
		}
	}
	
	public void saveAndSend(){
		soundController.setDescription(description.getText().toString());
		historic.add(soundController.getMedia());
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sound, menu);
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
		if (id == R.id.itemSaveAndSendSound) {
			saveAndSend();
			return true;
		}
		if (id == R.id.itemCancelSound) {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
