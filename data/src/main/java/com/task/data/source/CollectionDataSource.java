package com.task.data.source;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.task.data.api.APIService;
import com.task.domain.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.data.source.SearchDataSource.getPage;

public class CollectionDataSource extends PositionalDataSource<Root> {
    private final int id;
    private TextView t;
    private Boolean isLoadRange;
    public static final String CLIENT_ID = "xEBs2kNeIhxsB2NWUEiOKmWSOM5gZXamsjitk3j1NPc";
    public static final String TAG = "DebugLogs";

    public CollectionDataSource(int id, TextView t) {
        this.id = id;
        this.t = t;
        this.isLoadRange = true;
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d(TAG, "CollectionDataSource: onResponse: loadInitial: isSuccessful: " + response.body().size());
                    if(response.body().size() < 10)
                        isLoadRange = false;
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

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<List<Root>> call, Throwable tr) {
                Log.d(TAG, "CollectionDataSource: onFailure: loadInitial: " + t.toString());
                t.setVisibility(View.VISIBLE);
                t.setText("Unable to resolve host \"api.unsplash.com\"");
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback<Root> callback) {
        if(isLoadRange) {
            Log.d(TAG, "SearchDataSource: loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService apiService = retrofit.create(APIService.class);
            apiService.getCollectionPhotoPage(id, CLIENT_ID, getPage(params.startPosition)).enqueue(new Callback<List<Root>>() {
                @Override
                public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "CollectionDataSource: onResponse: isSuccessful: " + response.body().size());
                        List<Root> roots = (ArrayList<Root>) response.body();
                        callback.onResult(roots);
                    } else {
                        t.setVisibility(View.VISIBLE);
                        switch (response.code()) {
                            case 404:
                                Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 404: page not found");
                                break;
                            case 500:
                                Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 500: error on server");
                                break;
                            case 403:
                                Log.d(TAG, "CollectionDataSource: onResponse: loadRange: isNotSuccessful: error 403: have no permissions");
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
}
