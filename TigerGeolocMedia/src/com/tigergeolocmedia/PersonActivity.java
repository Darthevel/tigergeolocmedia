package com.tigergeolocmedia;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class PersonActivity extends ParentMenuActivity {

	private EditText username;
	private EditText email;
	
	private SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		prefs = getApplicationContext().getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		
		String name = null;
		if (prefs.contains("username"))
			name = prefs.getString("username", null);
//		String mail;
//		if (prefs.contains("email"))
//			mail = prefs.getString("email", null);
		
		username = (EditText) findViewById(R.id.editTextUsername);
		email = (EditText) findViewById(R.id.editTextEmail);
		
		GeolocMediaBddHelper readableHelper = new GeolocMediaBddHelper(getApplicationContext());
		if (name != null)
		{
			Person user = readableHelper.getUser("USERNAME = ?", new String[] {name});
		
			username.setText(user.getUsername());
			email.setText(user.getEmail());
		}
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
