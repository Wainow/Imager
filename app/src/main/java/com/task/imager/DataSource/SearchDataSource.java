package com.task.imager.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.task.imager.API.APIService;
import com.task.imager.API.Results;
import com.task.imager.API.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.Fragment.RandomImageFragment.CLIENT_ID;
import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class SearchDataSource extends PositionalDataSource<Root> {
    private final String query;

    public SearchDataSource(String query1) {
        this.query = query1;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<Root> callback) {
        Log.d(TAG, "SearchDataSource: loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.searchKeywordPage(CLIENT_ID, query, 1).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "SearchDataSource: onResponse: isSuccessful: " + response.body().results.size());
                    List<Root> roots = response.body().results;
                    callback.onResult(roots, 0);
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 500: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 403: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d(TAG, "SearchDataSource: onFailure: " + t.toString());
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
        apiService.searchKeywordPage(CLIENT_ID, query, getPage(params.startPosition)).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "SearchDataSource: onResponse: isSuccessful: " + response.body().results.size());
                    List<Root> roots = response.body().results;
                    callback.onResult(roots);
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "SearchDataSource: onResponse: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d(TAG, "SearchDataSource: onFailure: " + t.toString());
            }
        });
    }

    static int getPage(int startPosition) {
        return (startPosition / 10) + 1;
    }
}
