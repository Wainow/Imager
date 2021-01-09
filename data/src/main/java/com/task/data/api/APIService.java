package com.task.data.api;

import com.task.domain.Collection;
import com.task.domain.Results;
import com.task.domain.Root;

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

    @GET("/collections?")
    Call<List<Collection>> getCollectionsPage(
            @Query("client_id") String YOUR_ACCESS_KEY,
            @Query("page") int page
    );

    @GET("/search/photos?")
    Call<Results> searchKeywordPage(
            @Query("client_id") String YOUR_ACCESS_KEY,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/collections/{id}/photos")
    Call<List<Root>> getCollectionPhotoPage(
            @Path("id") int id,
            @Query("client_id") String YOUR_ACCESS_KEY,
            @Query("page") int page
    );

    @GET("/collections/{id}/photos?&page=1&per_page=1")
    Call<List<Root>> getCollectionPhoto(
            @Path("id") int id,
            @Query("client_id") String YOUR_ACCESS_KEY
    );
}
