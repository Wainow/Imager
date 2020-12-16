package com.task.imager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import static com.task.imager.RandomImageFragment.TAG;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Root> res;
    LayoutInflater inflater;


    public SearchAdapter(Context context, List<Root> res) {
        this.inflater = LayoutInflater.from(context);
        this.res = res;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "SearchAdapter: onBindViewHolder: res.get(position): " + res.get(position).urls.thumb);
        Glide.with(holder.itemView.getContext())
                .load(res.get(position).urls.thumb)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(res.get(position), holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return res.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_resource);
        }
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
}
