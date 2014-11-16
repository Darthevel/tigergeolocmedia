package com.tigergeolocmedia;

import java.util.List;

import com.tigergeolocmedia.adapter.HistoricBddAdapter;
import com.tigergeolocmedia.dba.HistoCrud;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class HistoricBDDActivity extends ParentMenuActivity {

	private ListView listView;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_bdd);
		
		this.context = getApplicationContext();
		
		HistoCrud db = new HistoCrud();
		List<Media> mediaList = db.selectAll(this.context);
		HistoricBddAdapter adapter = new HistoricBddAdapter(context, mediaList);
		
		this.listView = (ListView) findViewById(R.id.listViewHistoricBDD);
		
		this.listView.setAdapter(adapter);
		
		
	}
}
