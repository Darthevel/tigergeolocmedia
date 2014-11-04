package com.tigergeolocmedia.webservices;

import java.util.List;

import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

import com.google.gson.Gson;
import com.tigergeolocmedia.Person;

public interface RetrieveUsersService {
	Converter DATA_CONVERTER = new GsonConverter(new Gson());
	String SERVICE_ENDPOINT = "https://api.github.com";

	/**
	 * Renvoie un {@link Observable} sur une {@link List} de {@link Person}s.
	 * @return
	 */
	@GET("/users")
	Observable<List<Person>> getUserList();

	/**
	 * Renvoie un {@link Observable} sur une {@link Person}, connaissant son login.
	 * @param login
	 * @return
	 */
	@GET("/users/{login}")
	Observable<Person> getUser(@Path("login") String login);
	
	/**
	 * Renvoie une {@link Person}, connaissant son login.
	 * @param login
	 * @return
	 */
	@GET("/users/{login}")
	Person getUserSync(@Path("login") String login);
}
