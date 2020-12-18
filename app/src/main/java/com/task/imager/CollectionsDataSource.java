package com.task.imager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.DataSource.getPage;
import static com.task.imager.RandomImageFragment.CLIENT_ID;
import static com.task.imager.RandomImageFragment.TAG;

public class CollectionsDataSource extends PositionalDataSource {
    public CollectionsDataSource() {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
        Log.d(TAG, "DataSource: loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionsPage(CLIENT_ID,1).enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "DataSource: onResponse: isSuccessful: " + response.body().size());
                    List<Collection> roots = (ArrayList<Collection>) response.body();
                    callback.onResult(roots, 0);
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG, "DataSource: onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback callback) {
        Log.d(TAG, "DataSource: loadInitial, requestedStartPosition = " + params.startPosition +
                ", requestedLoadSize = " + params.loadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionsPage(CLIENT_ID, getPage(params.startPosition)).enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "DataSource: onResponse: isSuccessful: " + response.body().size());
                    List<Collection> roots = (ArrayList<Collection>) response.body();
                    callback.onResult(roots);
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "DataSource: onResponse: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG, "DataSource: onFailure: " + t.toString());
            }
        });
    }
}
