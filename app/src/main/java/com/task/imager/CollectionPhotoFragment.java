package com.task.imager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.RandomImageFragment.CLIENT_ID;
import static com.task.imager.RandomImageFragment.TAG;
public class CollectionPhotoFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;
    private ArrayList<Root> roots;

    public CollectionPhotoFragment() {
    }
    public static CollectionPhotoFragment newInstance(int id) {
        CollectionPhotoFragment fragment = new CollectionPhotoFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getCollectionImage(getArguments().getInt("id"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection_photo, container, false);
        recyclerView = view.findViewById(R.id.collection_photo_recycler);
        return view;
    }

    private void getCollectionImage(int id) {
        Log.d(TAG, "CollectionPhotoFragment: id: " + id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionPhoto(id, CLIENT_ID).enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "ImageSearchFragment: onResponse: isSuccessful: " + response.body().size());
                    roots = (ArrayList<Root>) response.body();
                    setRecyclerView();
                } else {
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "ImageSearchFragment: onResponse: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "ImageSearchFragment: onResponse: isNotSuccessful: error 404: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "ImageSearchFragment: onResponse: isNotSuccessful: error 404: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.d(TAG, "ImageSearchFragment: onFailure: " + t.toString());
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new SearchAdapter(getContext(), roots);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}