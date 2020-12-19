package com.task.imager.DataSource;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.task.imager.API.APIService;
import com.task.imager.API.Root;
import com.task.imager.Custom.TextViewPlus;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.DataSource.SearchDataSource.getPage;
import static com.task.imager.Fragment.RandomImageFragment.CLIENT_ID;
import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class CollectionDataSource extends PositionalDataSource<Root> {
    private final int id;
    private TextViewPlus t;

    public CollectionDataSource(int id, TextViewPlus t) {
        this.id = id;
        this.t = t;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback<Root> callback) {
        Log.d(TAG, "SearchDataSource: loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionPhotoPage(id, CLIENT_ID,1).enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "SearchDataSource: onResponse: loadInitial: isSuccessful: " + response.body().size());
                    List<Root> roots = (ArrayList<Root>) response.body();
                    callback.onResult(roots, 0);
                } else {
                    t.setVisibility(View.VISIBLE);
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadInitial: isNotSuccessful: error 404: page not found");
                            t.setText("Error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadInitial: isNotSuccessful: error 500: error on server");
                            t.setText("Error 500: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadInitial: isNotSuccessful: error 403: have no permissions");
                            t.setText("Error 403: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.d(TAG, "SearchDataSource: onFailure: loadInitial: " + t.toString());
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback<Root> callback) {
        Log.d(TAG, "SearchDataSource: loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionPhotoPage(id, CLIENT_ID, getPage(params.startPosition)).enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "CollectionDataSource: onResponse: isSuccessful: " + response.body().size());
                    List<Root> roots = (ArrayList<Root>) response.body();
                    callback.onResult(roots);
                } else {
                    t.setVisibility(View.VISIBLE);
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.d(TAG, "CollectionDataSource: onFailure: loadRange: " + t.toString());
            }
        });
    }
}
