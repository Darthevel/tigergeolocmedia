package com.tigergeolocmedia;
import java.util.List;

import retrofit.http.GET;
import rx.Observable;


public interface GitHubServices {
    @GET("/users")
    Observable<List<Person>> listUsers();
}
