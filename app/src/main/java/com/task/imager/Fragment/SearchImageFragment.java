package com.task.imager.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.imager.API.Root;
import com.task.imager.Adapter.PagingAdapter;
import com.task.imager.DataSource.SearchDataSource;
import com.task.imager.Custom.MainThreadExecutor;
import com.task.imager.R;
import com.task.imager.model.ImageSearchViewModel;

import java.util.concurrent.Executors;

import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class SearchImageFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageSearchViewModel model;
    private PagingAdapter adapter;

    public SearchImageFragment() {
    }

    public static SearchImageFragment newInstance() {
        return new SearchImageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_search, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        recyclerView =  view.findViewById(R.id.search_recycler);

        model = ViewModelProviders.of(getActivity()).get(ImageSearchViewModel.class);
        LiveData<String> data = model.getData();
        data.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String query) {
                Log.d(TAG, "SearchImageFragment: onChanged: query: " + query);
                searchPagingKeyword(query);
            }
        });
    }

    private void searchPagingKeyword(String query){
        Log.d(TAG, "SearchImageFragment: searchPagingKeyword: query: " + query);
        // SearchDataSource
        SearchDataSource searchDataSource = new SearchDataSource(query);


        // PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        PagedList<Root> pagedList = new PagedList.Builder<>(searchDataSource, config)
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}