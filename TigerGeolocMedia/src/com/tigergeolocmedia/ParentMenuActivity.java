package com.tigergeolocmedia;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ParentMenuActivity extends Activity {

	private String[] drawerItemsList;
	private ListView myDrawer;
	private DrawerLayout drawerLayout;
	
	

	public ParentMenuActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_parent_menu);
		
		ActionBar actionBar = getActionBar(); // Enable the home button on the ActionBar
		actionBar.setHomeButtonEnabled(true);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		drawerItemsList = getResources().getStringArray(R.array.items);
		myDrawer = (ListView) findViewById(R.id.my_drawer);
		myDrawer.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item, drawerItemsList));

		myDrawer.setOnItemClickListener(new MyDrawerItemClickListener());
	}

	
	/**
	 * Listen the ItemClick in the drawer to know what is clicked and start a new activity or
	 * if the activity already exist go back to a stacked activity and put it to the front of
	 * the task's history stack. If the called activity is the main, all activities on top of
	 * it will be closed and this Intent will be delivered to the (now on top) old activity
	 * as a new Intent.
	 */
	private class MyDrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
			String clickedItem = (String) adapter.getAdapter().getItem(pos);
			Intent intent;
			
			switch (pos) {
			case 0:
				intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(getApplicationContext(), PersonActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(getApplicationContext(), HistoricActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent(getApplicationContext(), MapActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			case 6:
				intent = new Intent(getApplicationContext(), ShowListUsersActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			case 7:
				intent = new Intent(getApplicationContext(), HistoricBDDActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;

			default:
				break;
			}
			
			Observable.just(clickedItem)
				.map(new Func1<String, String>() {
					@Override
					public String call(String message) {
						return "Vous avez cliquer sur : "+message;
					}
				})
				.subscribe(new Action1<String>(){
				@Override
				public void call(String message) {
					toastMessage(message);
				}
			});
			drawerLayout.closeDrawer(myDrawer);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
		if(drawerLayout.isDrawerOpen(GravityCompat.START))
		drawerLayout.closeDrawer(GravityCompat.START);
		else
		drawerLayout.openDrawer(GravityCompat.START);
		return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void setContentView(final int layoutResID) {
		FrameLayout item = (FrameLayout) findViewById(R.id.content_frame);
    	getLayoutInflater().inflate(layoutResID, item);
	}
	
	/**
	 * Create a toast message.
	 * @param message = the message that you can see.
	 */
	private void toastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

}