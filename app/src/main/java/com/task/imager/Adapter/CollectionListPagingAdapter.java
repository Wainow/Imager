package com.task.imager.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.imager.API.Collection;
import com.task.imager.Fragment.CollectionPhotoFragment;
import com.task.imager.R;

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

    @Override
    public void onBindViewHolder(@NonNull CollectionListPagingAdapter.ViewHolder holder, final int position) {
        holder.title.setText(getItem(position).title);
        holder.description.setText(getItem(position).description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: onClick");
                manager.beginTransaction()
                        .replace(R.id.fragment_container, CollectionPhotoFragment.newInstance((int) getItem(position).id))
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
