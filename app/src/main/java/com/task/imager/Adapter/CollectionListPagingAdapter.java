package com.task.imager.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

    public CollectionListPagingAdapter(@NonNull DiffUtil.ItemCallback<Collection> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionListPagingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final CollectionListPagingAdapter.ViewHolder holder, final int position) {
        try {
            holder.title.setText(Objects.requireNonNull(getItem(position)).getTitle());
            holder.description.setText(Objects.requireNonNull(getItem(position)).getDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: onClick");
                    ((AppCompatActivity)holder.itemView.getContext()).getSupportFragmentManager().getFragments().get(1).getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, CollectionPhotoFragment.newInstance((int) Objects.requireNonNull(getItem(position)).getId()))
                            .addToBackStack(null)
                            .commit();
                }
            });
            Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: getItem(position).toString(): " + getItem(position).toString());
            try {
                holder.mini_img.setColorFilter(Color.parseColor(
                        getColorFromId(
                                (int) Objects.requireNonNull(getItem(position)).getId()
                        )
                ));
            } catch (IllegalArgumentException e){
                holder.mini_img.setColorFilter(Color.parseColor(
                        "#000000"
                ));
            }
        } catch (NullPointerException | OutOfMemoryError e ){
            Log.d(TAG, "CollectionListPagingAdapter: onBindViewHolder: NullPointerException OR OutOfMemoryError");
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

    public String getColorFromId(int id){
        Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: id: " + id);
        String str = String.valueOf(id);
        Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str.length(): " + str.length());
        if(str.length() < 7) {
            switch (str.length()) {
                case 1:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#00000" + str;
                case 2:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#0000" + str;
                case 3:
                case 6:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#" + str;
                case 4:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#" + str + "00";
                case 5:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#" + str + "0";
                default:
                    Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
                    return "#000000";
            }
        } else{
            int pow = str.length() - 6;
            id = (int) (id / Math.pow(10, pow));
            str = String.valueOf(id);
            Log.d(TAG, "CollectionListPagingAdapter: getColorFromId: str: " + str);
            return "#" + str;
        }
    }
}
