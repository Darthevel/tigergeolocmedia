package com.tigergeolocmedia;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

public class HistoricActivity extends ParentMenuActivity {

	private ListView listView;
	private Historic historic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);
		
		historic = Historic.getInstance(getApplicationContext());
		ListElementFactory factory = new ListElementFactory(getApplicationContext());
		Observable<HistoricElement> observable = Observable.create(factory);
		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		CustomListAdapter adapter = new CustomListAdapter(this, observable);


		this.listView = (ListView) findViewById(R.id.listView);

		listView.setAdapter(adapter);
		
			
		
//			this.listView.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
//				{
//					// TODO Auto-generated method stub
//					HashMap<String, String> element = (HashMap<String, String>) parent.getItemAtPosition(position);
//					Toast.makeText(getApplicationContext(), "Click ListItem Number " + (position + 1) + "\n" + element.get("nom") + " " + element.get("tel"), Toast.LENGTH_SHORT).show();
//				  	Intent intent = new Intent(getApplicationContext(), ContactDescriptionActivity.class);
//				  	intent.putExtra("element", element);
//				  	startActivity(intent);
//				}
//			});
	        
		
		
	        //ListAdapter adapter = new SimpleAdapter(this, list, R.layout.listviewitem, new String[] {"nom", "tel"}, new int[] {R.id.itemTitle, R.id.itemDesc});
	        //this.listView.setAdapter(adapter);
		
	}
}
