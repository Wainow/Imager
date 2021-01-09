package com.task.data;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.task.data.api.APIService;
import com.task.domain.RandomImageResponseCallback;
import com.task.domain.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.data.source.CollectionDataSource.CLIENT_ID;
import static com.task.data.source.CollectionDataSource.TAG;

public class RandomImageData {
    private TextView textView;

    public RandomImageData(TextView textView){
        this.textView = textView;
    }

    public void getRandomImage(final RandomImageResponseCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getRandomPhoto(CLIENT_ID).enqueue(new Callback<Root>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d(TAG, "RandomImageData: onResponse: isSuccessful: " + response.body().getUrls().getThumb());
                    callback.RandomImageResponse(response.body());
                } else {
                    Log.d(TAG, "RandomImageData: onResponse: isNotSuccessful: " + response.code());
                    switch(response.code()) {
                        case 404:
                            textView.setText("Error 404: page not found");
                            break;
                        case 500:
                            textView.setText("Error 500: error on server");
                            break;
                        case 403:
                            textView.setText("Error 403: have no permissions");
                    }
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d(TAG, "RandomImageData: onFailure: " + t.toString());
                textView.setVisibility(View.VISIBLE);
                textView.setText("Unable to resolve host \"api.unsplash.com\"");
            }
        });
    }
}
