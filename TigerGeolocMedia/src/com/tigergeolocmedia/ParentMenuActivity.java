package com.tigergeolocmedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class ParentMenuActivity extends Activity {

	private String[] drawerItemsList;
	private ListView myDrawer;
	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_parent_menu);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		drawerItemsList = getResources().getStringArray(R.array.items);
		myDrawer = (ListView) findViewById(R.id.my_drawer);
		myDrawer.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item, drawerItemsList));

		myDrawer.setOnItemClickListener(new MyDrawerItemClickListener());
	}

	private class MyDrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
			String clickedItem = (String) adapter.getAdapter().getItem(pos);
			Intent intent;
			switch (clickedItem) {
			case "Capturer un media":
				intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				break;
			case "les médias autour de moi":
				intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				break;
			case "les medias de ceux que je suis":
				intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				break;
			case "mon compte":
				intent = new Intent(getApplicationContext(), PersonActivity.class);
				startActivity(intent);
				break;
			case "Historique":
				intent = new Intent(getApplicationContext(), HistoricActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
			
			drawerLayout.closeDrawer(myDrawer);
		}
	}
	
	@Override
	public void setContentView(final int layoutResID) {
		FrameLayout item = (FrameLayout) findViewById(R.id.content_frame);
    	getLayoutInflater().inflate(layoutResID, item);
	}

}