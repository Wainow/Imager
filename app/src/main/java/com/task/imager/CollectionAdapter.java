package com.task.imager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.task.imager.RandomImageFragment.TAG;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>  {
    LayoutInflater inflater;
    private ArrayList<Collection> collections;
    private FragmentManager manager;

    public CollectionAdapter(Context context, ArrayList<Collection> collections, FragmentManager manager) {
        this.inflater = LayoutInflater.from(context);
        this.collections = collections;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.collection_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "CollectionAdapter: onBindViewHolder: res.get(position): " + collections.get(position).title);
        holder.title.setText(collections.get(position).title);
        holder.description.setText(collections.get(position).description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CollectionAdapter: onBindViewHolder: onClick");
                manager.beginTransaction()
                        .add(R.id.fragment_container, CollectionPhotoFragment.newInstance((int) collections.get(position).id))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return collections.size();
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
