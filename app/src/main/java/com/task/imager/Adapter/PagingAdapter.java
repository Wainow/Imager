package com.task.imager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.imager.API.Root;
import com.task.imager.Custom.DialogInfo;
import com.task.imager.R;

public class PagingAdapter extends PagedListAdapter<Root, PagingAdapter.ViewHolder>{
    public PagingAdapter(@NonNull DiffUtil.ItemCallback<Root> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new PagingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //Log.d(TAG, "PagingAdapter: onBindViewHolder: res.get(position): " + getCurrentList().get(position).urls.thumb);
        Glide.with(holder.itemView.getContext())
                .load(getItem(position).urls.thumb)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(getItem(position), holder);
            }
        });
    }

    private void getInfo(Root currentRoot, ViewHolder holder) {
        DialogInfo dialogInfo = DialogInfo.newInstance(
                currentRoot.height,
                currentRoot.width,
                currentRoot.description,
                currentRoot.urls.full
        );
        dialogInfo.show(((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager(), "dlg");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_resource);
        }
    }
}
