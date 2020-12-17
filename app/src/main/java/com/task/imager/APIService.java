package com.task.imager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("/photos/random?")
    Call<Root> getRandomPhoto(
            @Query("client_id") String YOUR_ACCESS_KEY
    );

    @GET("/search/photos?")
    Call<Results> searchKeyword(
            @Query("client_id") String YOUR_ACCESS_KEY,
            @Query("query") String query
    );

    @GET("/search/photos?")
    Call<Results> searchKeywordPage(
            @Query("client_id") String YOUR_ACCESS_KEY,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/collections?")
    Call<List<Collection>> getCollections(
            @Query("client_id") String YOUR_ACCESS_KEY
    );

    @GET("/collections/{id}/photos")
    Call<List<Root>> getCollectionPhoto(
            @Path("id") int id,
            @Query("client_id") String YOUR_ACCESS_KEY
    );
}
