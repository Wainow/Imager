package com.task.data.source;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.task.data.api.APIService;
import com.task.domain.Collection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.data.source.CollectionDataSource.CLIENT_ID;
import static com.task.data.source.CollectionDataSource.TAG;

public class CollectionListDataSource extends PositionalDataSource {
    private TextView t;

    public CollectionListDataSource( TextView t) {
        this.t = t;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
        Log.d(TAG, "SearchDataSource: loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionsPage(CLIENT_ID, 0).enqueue(new Callback<List<Collection>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful() && response.body() != null){
                    t.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "SearchDataSource: onResponse: isSuccessful: " + response.body().size());
                    List<Collection> roots = (ArrayList<Collection>) response.body();
                    callback.onResult(roots, 0);
                } else {
                    t.setVisibility(View.VISIBLE);
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "CollectionListDataSource: loadInitial: onResponse: isNotSuccessful: error 404: page not found");
                            t.setText("Error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "CollectionListDataSource: loadInitial: onResponse: isNotSuccessful: error 500: error on server");
                            t.setText("Error 500: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "CollectionListDataSource: loadInitial: onResponse: isNotSuccessful: error 403: have no permissions");
                            t.setText("Error 403: have no permissions");
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<List<Collection>> call, Throwable tr) {
                Log.d(TAG, "CollectionListDataSource: loadInitial: onFailure: " + t.toString());
                t.setVisibility(View.VISIBLE);
                t.setText("Unable to resolve host \"api.unsplash.com\"");
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback callback) {
        Log.d(TAG, "SearchDataSource: loadRange, requestedStartPosition = " + params.startPosition +
                ", requestedLoadSize = " + params.loadSize);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionsPage(CLIENT_ID, getPage(params.startPosition)).enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d(TAG, "SearchDataSource: onResponse: isSuccessful: " + response.body().size());
                    List<Collection> roots = (ArrayList<Collection>) response.body();
                    callback.onResult(roots);
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "CollectionListDataSource: loadRange: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "CollectionListDataSource: loadRange: onResponse: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "CollectionListDataSource: loadRange: onResponse: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG, "CollectionListDataSource: loadRange: onFailure: " + t.toString());
            }
        });
    }

    public static int getPage(int startPosition) {
        return (startPosition / 10) + 1;
    }


}
