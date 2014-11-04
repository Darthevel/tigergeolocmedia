package com.tigergeolocmedia.webservices;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;

public class RetrieveUsersBuilder {
	public static RestAdapter build() {
		Builder builder = new RestAdapter.Builder();
		builder.setEndpoint(RetrieveUsersService.SERVICE_ENDPOINT);
		RestAdapter build = builder.build();
		return build;
	}
	
	public static RetrieveUsersService create() {
		RestAdapter restAdapter = RetrieveUsersBuilder.build();
		RetrieveUsersService createdUsersService = restAdapter.create(RetrieveUsersService.class);
		return createdUsersService;
	}
}
