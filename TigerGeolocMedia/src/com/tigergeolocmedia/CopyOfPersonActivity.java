package com.tigergeolocmedia;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tigergeolocmedia.webservices.RetrieveUsersBuilder;
import com.tigergeolocmedia.webservices.RetrieveUsersService;

public class CopyOfPersonActivity extends ParentMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		testRetrofit1();
	}
	
	/*
		GitHubServices service = restAdapter.create(GitHubServices.class);
        Observable<List<Person>> users = service.listUsers();
        users.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());		
	 */


	private void testRetrofit1() {
		RetrieveUsersService retrieveUsersService = RetrieveUsersBuilder.create();
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
				int toto = 5;
				toto++;
				
			}

			@Override
			public void onError(Throwable throwable) {
				int toto = 5;
				toto++;
				
			}

			@Override
			public void onNext(Person person) {
				int toto = 5;
				toto++;
				
			}
		};
		
		o2.subscribe(observer);
		// userList.subscribe(observer);
//		userList.subscribe(
//			
//		}
		
		int toto = 5;
		toto++;
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
		return super.onOptionsItemSelected(item);
	}
}
