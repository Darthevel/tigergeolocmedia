package com.tigergeolocmedia;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ShowListUsersActivity extends ParentMenuActivity {

	@InjectView(R.id.RecyclerViewUsers) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list_users);
		
		ButterKnife.inject(this);
		
		mRecyclerView.setHasFixedSize(true);
		
		mLayoutManager = new LinearLayoutManager(this);
	    mRecyclerView.setLayoutManager(mLayoutManager);
	    
	    //TODO Recuperation de la liste des user en BDD ==> myDataSet
		GeolocMediaBddHelper readableHelper = new GeolocMediaBddHelper(getApplicationContext());
		List<Person> listUsers = readableHelper.getUsers(null, null);
	    
	    mAdapter = new RecyclerAdapter(listUsers);
        mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list_users, menu);
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
		return super.onOptionsItemSelected(item);
	}
}
