package com.tigergeolocmedia;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoricActivity extends Activity {

	private ListView listView;
	private Historic historic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);
		
		historic = Historic.getInstance(getApplicationContext());
		CustomListAdapter adapter = new CustomListAdapter(this, historic.getMediaList());
		listView.setAdapter(adapter);
		
			
		this.listView = (ListView) findViewById(R.id.listView);
		
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
