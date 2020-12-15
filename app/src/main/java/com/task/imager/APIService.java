package com.task.imager;

import java.net.URL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @GET("/photos/random?")
    Call<Root> getRandomPhoto(
            @Query("client_id") String YOUR_ACCESS_KEY
    );
}
