package com.task.imager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DebugLogs";
    private ImageView random_image;
    private TextView textView;
    private static final String CLIENT_ID = "xEBs2kNeIhxsB2NWUEiOKmWSOM5gZXamsjitk3j1NPc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random_image = findViewById(R.id.random_img);
        Button random_button = findViewById(R.id.random_image_btn);
        textView = findViewById(R.id.text_view);
        getRandomImage();

        random_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomImage();
            }
        });
    }

    public void getRandomImage(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getRandomPhoto(CLIENT_ID).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "MainActivity: onResponse: isSuccessful: " + response.body().urls.thumb);
                    Glide.with(getBaseContext())
                            .load(response.body().urls.small)
                            .into(random_image);
                } else {
                    switch(response.code()) {
                        case 404:
                            textView.setText("Страница не найдена");
                            break;
                        case 500:
                            textView.setText("Ошибка на сервере");
                            break;
                        case 403:
                            textView.setText("Превышенно количество запросов за 1 час");
                    }
                }
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d(TAG, "MainActivity: onFailure: " + t.toString());
            }
        });
    }
}