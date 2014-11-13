package com.tigergeolocmedia;


import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.google.android.gms.internal.pe;
import com.tigergeolocmedia.webservices.RetrieveUsersBuilder;
import com.tigergeolocmedia.webservices.RetrieveUsersService;

public class PersonActivity extends ParentMenuActivity {
	
	private RetrieveUsersService retrieveUsersService = RetrieveUsersBuilder.create();

	@InjectView(R.id.editTextUsername) EditText username;
	@InjectView(R.id.editTextEmail) EditText email;
	@InjectView(R.id.buttonGetUsers) Button getUsers;
	
	private SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		ButterKnife.inject(this);

		//getUserListFromGitHub(); // Get the user list from github

		prefs = getApplicationContext().getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		
		String name = null;
		if (prefs.contains("username"))
			name = prefs.getString("username", null);
//		String mail;
//		if (prefs.contains("email"))
//			mail = prefs.getString("email", null);
		
		GeolocMediaBddHelper readableHelper = new GeolocMediaBddHelper(getApplicationContext());
		if (name != null)
		{
			Person user = readableHelper.getUser("USERNAME = ?", new String[] {name});
		
			username.setText(user.getLogin());
			email.setText(user.getEmail());
		}
		readableHelper.close();
	}

	@OnClick(R.id.buttonGetUsers)
	public void getUserListFromGitHub() {
		Observable<List<Person>> userList = retrieveUsersService.getUserList();
		
		Observable<Person> o2 = userList.flatMap(new Func1<List<Person>, Observable<? extends Person>>() {

			@Override
			public Observable<? extends Person> call(List<Person> arg0) {
				return Observable.from(arg0);
			}
			
		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		
		Observer<Person> observer = new Observer<Person>() {

			@Override
			public void onCompleted() {
				Toast.makeText(getApplicationContext(), "Recuperation des données terminé", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(Throwable throwable) {
				Toast.makeText(getApplicationContext(), "Error : Probleme lors de la recuperation de donné", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNext(Person person) {
				GeolocMediaBddHelper writableHelper = new GeolocMediaBddHelper(getApplicationContext());
				writableHelper.saveUser(2, person.getLogin(), person.getLogin() + "@gmail.com");
				writableHelper.close();
				Log.i("REF DB USERLIST", "User name : " + person.getLogin());
			}
		};
		o2.subscribe(observer);
	}

	public void saveUser()
	{
		Editor editor = prefs.edit();
		
		GeolocMediaBddHelper writableHelper = new GeolocMediaBddHelper(getApplicationContext());
		writableHelper.saveUser(2, username.getText().toString(), email.getText().toString());
	
		editor.putString("username", username.getText().toString());
		editor.commit();

		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person, menu);
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
		if (id == R.id.itemSaveUser) {
			saveUser();
			return true;
		}
		if (id == R.id.itemCancelUser) {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
