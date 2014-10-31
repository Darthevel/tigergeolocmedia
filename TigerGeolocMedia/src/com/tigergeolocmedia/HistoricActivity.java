package com.tigergeolocmedia;

import rx.Observable;
import rx.android.events.OnItemClickEvent;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class HistoricActivity extends ParentMenuActivity {

	private ListView listView;
	private Historic historic;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);
		
		context = getApplicationContext();
		historic = Historic.getInstance(context);
		ListElementFactory factory = new ListElementFactory(context);
		Observable<HistoricElement> observable = Observable.create(factory);
		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		CustomListAdapter adapter = new CustomListAdapter(this, observable);


		this.listView = (ListView) findViewById(R.id.listView);

		listView.setAdapter(adapter);
		ViewObservable.itemClicks(listView)
			.subscribe(new Action1<OnItemClickEvent>(){

				@Override
				public void call(OnItemClickEvent e) {
					int position = listView.getPositionForView(e.view);
					Media media = historic.getMediaList().get(position);
					Toast.makeText(context, "Vous avez clique sur" + media.getName(), Toast.LENGTH_SHORT).show();
				}
				
			});
	}
}
