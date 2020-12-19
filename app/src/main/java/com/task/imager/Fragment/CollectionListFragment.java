package com.task.imager.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.imager.API.Collection;
import com.task.imager.Adapter.CollectionListPagingAdapter;
import com.task.imager.DataSource.CollectionListDataSource;
import com.task.imager.Custom.MainThreadExecutor;
import com.task.imager.R;

import java.util.concurrent.Executors;

public class CollectionListFragment extends Fragment {
    private RecyclerView recyclerView;
    private CollectionListPagingAdapter adapter;

    public CollectionListFragment() {
    }


    public static CollectionListFragment newInstance() {
        CollectionListFragment fragment = new CollectionListFragment();
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
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        recyclerView =  view.findViewById(R.id.collections_recycler);

        getCollections();

        return view;
    }

    private void getCollections(){
        // SearchDataSource
        CollectionListDataSource dataSource = new CollectionListDataSource();


        // PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        PagedList<Collection> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(new MainThreadExecutor())
                .build();

        // Adapter
        adapter = new CollectionListPagingAdapter(new DiffUtil.ItemCallback<Collection>() {
            @Override
            public boolean areItemsTheSame(@NonNull Collection oldItem, @NonNull Collection newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Collection oldItem, @NonNull Collection newItem) {
                return false;
            }
        }, getChildFragmentManager());
        adapter.submitList(pagedList);

        // RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}