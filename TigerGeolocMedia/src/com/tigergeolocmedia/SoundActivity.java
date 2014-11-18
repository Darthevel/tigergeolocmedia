package com.tigergeolocmedia;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.RecordSoundService.RecordSoundServiceInnerClass;
import com.tigergeolocmedia.dba.HistoCrud;

public class SoundActivity extends ParentMenuActivity {

	@InjectView(R.id.recordButton) Button recordButton;
	@InjectView(R.id.playSoundButton) Button playButton;
	@InjectView(R.id.soundDescription) EditText description;
	
	private RecordSoundServiceInnerClass recordService;

	private Historic historic = null; 

	/*
	 * Contrôleur de son.
	 */
	private SoundController soundController = new SoundController(
			Constants.SOUND_PREFIX, Constants.SOUND_SUFFIX,
			Constants.SOUND_DIRECTORY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		ButterKnife.inject(this);

		// Initialisation des bouttons et champs de l'activité
		// Recuperation de l'historique pour pouvoir stoquer tout nouveaux sons
		historic = Historic.getInstance(getApplicationContext());
		
		// File file = new File(soundController.media.getPath());
		// if(!file.exists())
		if (soundController.getMedia() == null)
			playButton.setEnabled(false);
		//description = (EditText) findViewById(R.id.soundDescription);
		Intent intent = new Intent(this, RecordSoundService.class);
		bindService(intent, new ServiceConnection() {
		  @Override
		  public void onServiceConnected(ComponentName name, IBinder service) {

		      // Actual implementation is known, either because it is our local Service
		      // or because the service provider published a public target interface
			  recordService = (RecordSoundServiceInnerClass)service;

		      Toast.makeText(getApplicationContext(), recordService.test(), Toast.LENGTH_SHORT).show();
		  }
		  @Override
		  public void onServiceDisconnected(ComponentName name) {
		      // Returned IBinder is no longer usable...
		      Toast.makeText(getApplicationContext(), name + " Unbind", Toast.LENGTH_SHORT).show();
		  }
		}, 0 /* flags */);
	}

	/*
	 * Permet de lock l'ecran sur son orientation actuelle
	 */
	public void lockRotation()
	{
	   final int rotation = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();//getOrientation();
       switch (rotation) {
        case Surface.ROTATION_0:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        	break;
        case Surface.ROTATION_90:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            break;
        case Surface.ROTATION_180:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        	break;
        default:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        	break;
        }		
	}
	
	/*
	 * Regarde si on est en train d'enregistrer : Si on est en train
	 * d'enregistrer, change le texte du boutton, et appel la fonction d'arret
	 * de l'enregistrement 
	 * Si on est pas en train d'enregistrer, change le texte
	 * du boutton, et appel la fonction d'enregistrement
	 */
	@OnClick(R.id.recordButton)
	public void record() {
		if (soundController.isRecording()) {
			recordButton.setText(R.string.record);
			//soundController.stopRecording();
			recordService.stopRecording();
			soundController.setRecording(false);
			playButton.setEnabled(true);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else {
			lockRotation();
			recordButton.setText(R.string.stopRecord);
			//soundController.record();
			recordService.record();
			soundController.setRecording(true);
		}
		soundController = recordService.getSoundController();
	}

	/*
	 * Regarde si on est en train d'ecoute le media : Si on est en train
	 * d'ecouter, change le texte du boutton, et appel la fonction pour arreter
	 * l'ecoute 
	 * Si on est pas en train d'ecouter, change le texte du boutton, et
	 * appel la fonction d'ecoute
	 */
	@OnClick(R.id.playSoundButton)
	public void playSound() {
		if (soundController.isPlaying()) {
			playButton.setText(R.string.play);
			soundController.setPlaying(false);

			getApplicationContext().stopService(new Intent(getApplicationContext(), PlaySoundService.class));
			
			//soundController.stop();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else {
			lockRotation();
			playButton.setText(R.string.stop);
			soundController.setPlaying(true);
			
			Intent i=new Intent(getApplicationContext(), PlaySoundService.class);
			i.putExtra("path", soundController.getMedia().getPath());
			startService(i);
			
			//soundController.play();
		}
	}

	/*
	 * Lie la description au media Ajout le media a l'historique Redirige vers
	 * MainActivity
	 */
	public void saveAndSend() {
		soundController.setDescription(description.getText().toString());
		historic.add(soundController.getMedia());
		
		//Using SQLite
		HistoCrud db = new HistoCrud();
		db.create(this, soundController.getMedia());
		
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 * Recuperation des donnés lié au media (son) et réinjection de celles ci
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		soundController.setRecording(savedInstanceState.getBoolean("recording"));
		soundController.setPlaying(savedInstanceState.getBoolean("playing"));

		if (savedInstanceState.containsKey("mediaName"))
		{
			soundController.setMedia(new Media(MediaType.SOUND, 
					savedInstanceState.getString("mediaName"), 
					savedInstanceState.getString("mediaPath"), 
					savedInstanceState.getString("mediaDescription")));
			playButton.setEnabled(true);
		}

		description.setText(savedInstanceState.getString("description"));

		if (soundController.isPlaying())
			playButton.setText(R.string.stop);
		else
			playButton.setText(R.string.play);

		if (soundController.isRecording())
			recordButton.setText(R.string.stopRecord);
		else
			recordButton.setText(R.string.record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 * Enregistre les donné lié au media (son) lors de la rotation de l'ecran
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("recording", soundController.isRecording());
		outState.putBoolean("playing", soundController.isPlaying());
		if (soundController.getMedia() != null) {
			outState.putString("mediaName", 
					soundController.getMedia().getName());
			outState.putString("mediaPath", 
					soundController.getMedia().getPath());
			outState.putString("mediaDescription", 
					soundController.getMedia().getDescription());
		}
		outState.putString("description", description.getText().toString());
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
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

