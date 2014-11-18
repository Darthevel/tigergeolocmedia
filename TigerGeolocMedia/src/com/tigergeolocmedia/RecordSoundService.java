package com.tigergeolocmedia;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class RecordSoundService extends IntentService {

	public class RecordSoundServiceInnerClass extends Binder {
		private Intent intent;
		private RecordSoundService rss;
		private String path;
		private MediaRecorder mRecorder;
		private String LOG_TAG = "RecordSoundService record()";
		private SoundController soundController = new SoundController(
				Constants.SOUND_PREFIX, Constants.SOUND_SUFFIX,
				Constants.SOUND_DIRECTORY);

		public RecordSoundServiceInnerClass(Intent intent,
				RecordSoundService rss) {
			super();
			this.intent = intent;
			this.rss = rss;
			this.path = intent.getStringExtra("path");
		}

		public RecordSoundService getRss() {
			return rss;
		}

		public void record() {
			//soundController.record();
			Log.i("RecordSoundService", "On record");
		}

		public void stopRecording() {
			//soundController.stopRecording();
			Log.i("RecordSoundService", "On stop le record");
		}

		public SoundController getSoundController() {
			return soundController;
		}
		
		public String test(){
			return "Whatever, we'll see what happen. We're bind anyway";
		}
	}

	public RecordSoundService() {
		super("RecordSoundService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new RecordSoundServiceInnerClass(intent, this);
	}

}
