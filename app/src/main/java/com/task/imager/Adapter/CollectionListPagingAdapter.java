package com.task.imager.Adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.imager.API.APIService;
import com.task.imager.API.Collection;
import com.task.imager.API.Root;
import com.task.imager.Fragment.CollectionPhotoFragment;
import com.task.imager.R;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.task.imager.Fragment.RandomImageFragment.CLIENT_ID;
import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class CollectionListPagingAdapter extends PagedListAdapter<Collection, CollectionListPagingAdapter.ViewHolder> {
    private FragmentManager manager;

    public CollectionListPagingAdapter(@NonNull DiffUtil.ItemCallback<Collection> diffCallback, FragmentManager manager) {
        super(diffCallback);
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionListPagingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull CollectionListPagingAdapter.ViewHolder holder, final int position) {
        try {
            holder.title.setText(Objects.requireNonNull(getItem(position)).title);
            holder.description.setText(Objects.requireNonNull(getItem(position)).description);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: onClick");
                    manager.beginTransaction()
                            .replace(R.id.fragment_container, CollectionPhotoFragment.newInstance((int) Objects.requireNonNull(getItem(position)).id))
                            .addToBackStack(null)
                            .commit();
                }
            });
            setFirstPhoto((int) Objects.requireNonNull(getItem(position)).id, holder);
        } catch (NullPointerException e){
            Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: NullPointerException");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private CircleImageView mini_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
            mini_img = itemView.findViewById(R.id.mini_collection_img);
        }
    }

    public void setFirstPhoto(int id, final ViewHolder holder){
        Log.d(TAG, "CollectionListPagingAdapter: getFirstPhoto: starting");
        Log.d(TAG, "CollectionListPagingAdapter: getFirstPhoto: id: " + id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getCollectionPhoto(id, CLIENT_ID).enqueue(new Callback<List<Root>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                Log.d(TAG, "CollectionListPagingAdapter: onResponse");
                if(response.isSuccessful() && response.body() != null){
                    Log.d(TAG, "CollectionListPagingAdapter: onResponse: loadInitial: isSuccessful: " + response.body().size());
                    Glide.with(holder.itemView.getContext())
                            .load(response.body().get(0).urls.thumb)
                            .into(holder.mini_img);
                } else {
                    Log.d(TAG, "CollectionListPagingAdapter: onResponse: loadInitial: isNotSuccessful: " + response.toString());
                    switch(response.code()) {
                        case 404:
                            Log.d(TAG, "CollectionListPagingAdapter: onResponse: loadInitial: isNotSuccessful: error 404: page not found");
                            break;
                        case 500:
                            Log.d(TAG, "CollectionListPagingAdapter: onResponse: loadInitial: isNotSuccessful: error 500: error on server");
                            break;
                        case 403:
                            Log.d(TAG, "CollectionListPagingAdapter: onResponse: loadInitial: isNotSuccessful: error 403: have no permissions");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.d(TAG, "CollectionListPagingAdapter: onFailure: loadInitial: " + t.toString());
            }
        });
    }
}
