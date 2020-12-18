package com.task.imager.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.imager.API.Root;
import com.task.imager.API.APIService;
import com.task.imager.Custom.DialogInfo;
import com.task.imager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "DebugLogs";
    private ImageView random_image;
    private TextView textView;
    public static final String CLIENT_ID = "xEBs2kNeIhxsB2NWUEiOKmWSOM5gZXamsjitk3j1NPc";
    private Root currentRoot;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RandomImageFragment() {
    }

    public static RandomImageFragment newInstance() {
        RandomImageFragment fragment = new RandomImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_image, container, false);
        init(view);
        random_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(currentRoot);
            }
        });

        return view;
    }

    private void init(View view) {
        getRandomImage();
        random_image = view.findViewById(R.id.random_img);
        textView = view.findViewById(R.id.text_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
                    Log.d(TAG, "RandomImageFragment: onResponse: isSuccessful: " + response.body().urls.thumb);
                    random_image.setVisibility(View.VISIBLE);
                    currentRoot = response.body();
                    Glide.with(getContext())
                            .load(currentRoot.urls.small)
                            .into(random_image);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    Log.d(TAG, "RandomImageFragment: onResponse: isNotSuccessful: " + response.code());
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
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d(TAG, "RandomImageFragment: onFailure: " + t.toString());
            }
        });
    }

    private void getInfo(Root currentRoot) {
        DialogInfo dialogInfo = DialogInfo.newInstance(
                currentRoot.height,
                currentRoot.width,
                currentRoot.description,
                currentRoot.urls.full
        );
        dialogInfo.show(getActivity().getSupportFragmentManager(), "dlg");
    }

    @Override
    public void onRefresh() {
        getRandomImage();
    }
}