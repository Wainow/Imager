package com.task.imager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.task.imager.RandomImageFragment.TAG;

public class CollectionPagingAdapter extends PagedListAdapter<Collection, CollectionPagingAdapter.ViewHolder> {
    private FragmentManager manager;

    protected CollectionPagingAdapter(@NonNull DiffUtil.ItemCallback<Collection> diffCallback, FragmentManager manager) {
        super(diffCallback);
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionPagingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionPagingAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "CollectionAdapter: onBindViewHolder: res.get(position): " + getItem(position).title);
        holder.title.setText(getItem(position).title);
        holder.description.setText(getItem(position).description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CollectionAdapter: onBindViewHolder: onClick");
                manager.beginTransaction()
                        .add(R.id.fragment_container, CollectionPhotoFragment.newInstance((int) getItem(position).id))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
        }
    }
}
