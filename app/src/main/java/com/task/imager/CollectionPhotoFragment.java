package com.task.imager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.RandomImageFragment.CLIENT_ID;
import static com.task.imager.RandomImageFragment.TAG;
public class CollectionPhotoFragment extends Fragment {
    private RecyclerView recyclerView;
    private PagingAdapter adapter;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection_photo, container, false);
        recyclerView = view.findViewById(R.id.collection_photo_recycler);

        if (getArguments() != null) {
            getCollectionImage(getArguments().getInt("id"));
        }
        return view;
    }

    private void getCollectionImage(int id){
        // DataSource
        CollectionDataSource dataSource = new CollectionDataSource(id);


        // PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        PagedList<Root> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(new MainThreadExecutor())
                .build();


        // Adapter
        adapter = new PagingAdapter(new DiffUtil.ItemCallback<Root>() {
            @Override
            public boolean areItemsTheSame(@NonNull Root oldItem, @NonNull Root newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Root oldItem, @NonNull Root newItem) {
                return false;
            }
        });
        adapter.submitList(pagedList);

        // RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}