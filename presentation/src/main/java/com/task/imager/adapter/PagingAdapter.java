package com.task.imager.adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.domain.Root;
import com.task.imager.custom.DialogInfo;
import com.task.imager.R;

import java.util.Objects;

import static com.task.imager.fragment.RandomImageFragment.TAG;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            Glide.with(holder.itemView.getContext())
                    .load(Objects.requireNonNull(getItem(position)).getUrls().getSmall())
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getInfo(Objects.requireNonNull(getItem(position)), holder);
                }
            });
        } catch (NullPointerException e){
            Log.d(TAG, "PagingAdapter: onBindViewHolder: NullPointerException");
        }
    }

    private void getInfo(Root currentRoot, ViewHolder holder) {
        DialogInfo dialogInfo = DialogInfo.newInstance(
                currentRoot.getHeight(),
                currentRoot.getWidth(),
                currentRoot.getDescription(),
                currentRoot.getUrls()
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
