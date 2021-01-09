package com.task.data.source;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.task.data.api.APIService;
import com.task.domain.Results;
import com.task.domain.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.data.source.CollectionDataSource.CLIENT_ID;
import static com.task.data.source.CollectionDataSource.TAG;

public class SearchDataSource extends PositionalDataSource<Root> {
    private final String query;
    private Boolean isLoadRange;
    private TextView t;

    public SearchDataSource(String query1, TextView t) {
        this.query = query1;
        this.isLoadRange = true;
        this.t = t;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<Root> callback) {
        Log.d(TAG, "SearchDataSource: loadInitial: requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.searchKeywordPage(CLIENT_ID, query, 1).enqueue(new Callback<Results>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful() && response.body() != null){
                    t.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "SearchDataSource: loadInitial: onResponse: isSuccessful: " + response.body().getResults().size());
                    if(response.body().getResults().size() == 0){
                        Log.d(TAG, "SearchDataSource: loadRange: onResponse: isSuccessful: The images are not found");
                        t.setVisibility(View.VISIBLE);
                        t.setText("The images are not found");
                    } else if(response.body().getResults().size() < 10)
                        isLoadRange = false;
                    List<Root> roots = response.body().getResults();
                    callback.onResult(roots, 0);
                } else {
                    t.setVisibility(View.VISIBLE);
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "SearchDataSource: onResponse: loadInitial: isNotSuccessful: error 404: page not found");
                            t.setText("Error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "SearchDataSource: onResponse: loadInitial: isNotSuccessful: error 500: error on server");
                            t.setText("Error 500: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "SearchDataSource: onResponse: loadInitial: isNotSuccessful: error 403: have no permissions");
                            t.setText("Error 403: have no permissions");
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<Results> call, Throwable tr) {
                Log.d(TAG, "SearchDataSource: loadInitial: onFailure: " + t.toString());
                t.setVisibility(View.VISIBLE);
                t.setText("Unable to resolve host \"api.unsplash.com\"");
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback<Root> callback) {
        if(isLoadRange) {
            Log.d(TAG, "SearchDataSource: loadRange: startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService apiService = retrofit.create(APIService.class);
            apiService.searchKeywordPage(CLIENT_ID, query, getPage(params.startPosition)).enqueue(new Callback<Results>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        t.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "SearchDataSource: loadRange: onResponse: isSuccessful: " + response.body().getResults().size());
                        List<Root> roots = response.body().getResults();
                        callback.onResult(roots);
                    } else {
                        t.setVisibility(View.VISIBLE);
                        switch (response.code()) {
                            case 404:
                                Log.d(TAG, "SearchDataSource: onResponse: loadRange: isNotSuccessful: error 404: page not found");
                                t.setText("Error 404: page not found");
                                break;
                            case 500:
                                Log.d(TAG, "SearchDataSource: onResponse: loadRange: isNotSuccessful: error 500: error on server");
                                t.setText("Error 500: error on server");
                                break;
                            case 403:
                                Log.d(TAG, "SearchDataSource: onResponse: loadRange: isNotSuccessful: error 403: have no permissions");
                                t.setText("Error 403: have no permissions");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Results> call, Throwable tr) {
                    Log.d(TAG, "SearchDataSource: loadRange: onFailure: " + t.toString());
                }
            });
        }
    }

    static int getPage(int startPosition) {
        return (startPosition / 10) + 1;
    }
}
