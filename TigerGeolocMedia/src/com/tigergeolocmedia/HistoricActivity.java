package com.tigergeolocmedia;

import butterknife.ButterKnife;
import butterknife.InjectView;
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

	@InjectView(R.id.listView) ListView listView;
	private Historic historic;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);
		
		ButterKnife.inject(this);
		
		context = getApplicationContext();
		historic = Historic.getInstance(context);
		
		// Cree la factory qui cree les element (item) de la listView
		ListElementFactory factory = new ListElementFactory(context);
		
		// Creation de l'observable contenant la liste des element pour que l'adapter puisse cree l'affichage
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
					Toast.makeText(context, getString(R.string.youClickedOn) + media.getName(), Toast.LENGTH_SHORT).show();
					//TODO changer d'activité pour pouvoir afficher le media en grand avec ses informations et le jouer
				}
				
			});
	}
}
