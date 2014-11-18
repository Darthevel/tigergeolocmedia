package com.tigergeolocmedia;

import java.io.IOException;
import java.util.List;

import rx.android.events.OnItemClickEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.adapter.HistoricBddAdapter;
import com.tigergeolocmedia.dba.HistoCrud;
import com.tigergeolocmedia.util.Registry;

public class HistoricBDDActivity extends ParentMenuActivity {

	@InjectView(R.id.listViewHistoricBDD)
	ListView listView;
	private Context context;
	private HistoCrud db;
	private List<Media> mediaList;
	private MediaPlayer mPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_bdd);

		ButterKnife.inject(this);

		this.context = getApplicationContext();

		this.db = new HistoCrud();
		this.mediaList = db.selectAll(this.context);
		HistoricBddAdapter adapter = new HistoricBddAdapter(context, mediaList);

		// this.listView = (ListView) findViewById(R.id.listViewHistoricBDD);

		this.listView.setAdapter(adapter);

		ViewObservable.itemClicks(listView).subscribe(
				new Action1<OnItemClickEvent>() {

					@Override
					public void call(OnItemClickEvent e) {
						int position = listView.getPositionForView(e.view);
						Media media = mediaList.get(position);
						Toast.makeText(
								context,
								getString(R.string.youClickedOn)
										+ media.getName(), Toast.LENGTH_SHORT)
								.show();
						showMedia(media);
					}

				});
	}

	protected void showMedia(Media media) {
		if (media.getType().equals(MediaType.PICTURE)) {
			PictureController pictureController = Registry
					.get(Constants.PICTURE_CONTROLLER);
			pictureController.setMedia(media);

			Intent intent = new Intent(this, PictureActivityReadOnly.class);
			startActivity(intent);

			return;
		}
		if (media.getType().equals(MediaType.MOVIE)) {
			MovieController movieController = Registry
					.get(Constants.MOVIE_CONTROLLER);
			movieController.setMedia(media);

			Intent intent = new Intent(this, VideoViewActivityReadOnly.class);
			startActivity(intent);
			return;
		}
		if (media.getType().equals(MediaType.SOUND)) {

			if (mPlayer.isPlaying()) {
				mPlayer.stop();
				mPlayer.reset();
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			} else {
				try {
					mPlayer.setDataSource(media.getPath());
					mPlayer.prepare();

				} catch (IllegalArgumentException | SecurityException
						| IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					Log.e(AUDIO_SERVICE,
							"Problem with AUDIO_SERVICE see StackTrace() for more");
					e.printStackTrace();
				}
				lockRotation();
				mPlayer.start();
			}

			return;
		}

	}

	public void lockRotation() {
		final int rotation = ((WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getRotation();// getOrientation();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
}
