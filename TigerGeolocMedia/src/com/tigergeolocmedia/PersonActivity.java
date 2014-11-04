package com.tigergeolocmedia;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tigergeolocmedia.webservices.RetrieveUsersBuilder;
import com.tigergeolocmedia.webservices.RetrieveUsersService;

public class PersonActivity extends ParentMenuActivity {
	
	private RetrieveUsersService retrieveUsersService = RetrieveUsersBuilder.create();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		// testRetrofit1();
		// testRetrofitLogin("darthevel");
		// testRetrofitList();
		// testRetrofitList();
		testRetrofitFull();
	}
	
	/*
		GitHubServices service = restAdapter.create(GitHubServices.class);
        Observable<List<Person>> users = service.listUsers();
        users.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());		
	 */


	private void testRetrofitLogin(String login) {
		// TODO Auto-generated method stub
		Observable<Person> person = retrieveUsersService.getUser(login).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
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
		
		person.subscribe(observer);

		
	}
	
	private void testRetrofitList () {
		Observable<List<Person>> userList = retrieveUsersService.getUserList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());;
		Observer<List<Person>> observerList = new Observer<List<Person>>() {

			@Override
			public void onCompleted() {
				int toto = 5;
				toto++;
				
			}

			@Override
			public void onError(Throwable arg0) {
				int toto = 5;
				toto++;
			}

			@Override
			public void onNext(List<Person> arg0) {
				int toto = 5;
				toto++;
			}
			
		};
		userList.subscribe(observerList);

	}
	
	private void testRetrofitFull() {
		Observable<List<Person>> userList = retrieveUsersService.getUserList();
		
		Observable<Person> o2 = userList.flatMap(new Func1<List<Person>, Observable<? extends Person>>() {

			@Override
			public Observable<? extends Person> call(List<Person> arg0) {
				return Observable.from(arg0);
			}
			
		}).map(new Func1<Person,Person>() {

			@Override
			public Person call(Person person) {
				// On récupère ici une Person incomplète : seul le champ login est rempli.
				// On va maintenant récupérer le reste des champs (en particulier le champ email).
				Person completePerson = retrieveUsersService.getUserSync(person.getLogin());
				return completePerson;
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


	private void testRetrofit1() {
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
