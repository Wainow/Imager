package com.task.imager.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.data.RandomImageData;
import com.task.domain.RandomImageResponseCallback;
import com.task.domain.Root;
import com.task.imager.custom.DialogInfo;
import com.task.imager.R;

public class RandomImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RandomImageResponseCallback {
    public static final String TAG = "DebugLogs";
    private ImageView random_image;
    private TextView textView;
    public static final String CLIENT_ID = "xEBs2kNeIhxsB2NWUEiOKmWSOM5gZXamsjitk3j1NPc";
    private Root currentRoot;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RandomImageFragment() {
    }

    public static RandomImageFragment newInstance() {
        return new RandomImageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_image, container, false);
        init(view);
        random_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo(currentRoot);
            }
        });

        return view;
    }

    private void init(View view) {
        random_image = view.findViewById(R.id.random_img);
        textView = view.findViewById(R.id.text_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        setRandomImage();
    }

    public void setRandomImage(){
        RandomImageData randomImageData = new RandomImageData(textView);
        random_image.setVisibility(View.VISIBLE);
        randomImageData.getRandomImage(this);
    }

    private void getInfo(Root currentRoot) {
        if(getActivity() != null) {
            DialogInfo dialogInfo = DialogInfo.newInstance(
                    currentRoot.getHeight(),
                    currentRoot.getWidth(),
                    currentRoot.getDescription(),
                    currentRoot.getUrls()
            );
            dialogInfo.show(getActivity().getSupportFragmentManager(), "dlg");
        }
    }

    @Override
    public void onRefresh() {
        setRandomImage();
    }

    @Override
    public void RandomImageResponse(Root body) {
        currentRoot = body;
        checkResult();
    }

    public void checkResult(){
        if(currentRoot != null) {
            Log.d(TAG, "RandomImageFragment: onResponse: isSuccessful");
            Glide.with(getContext())
                    .load(currentRoot.getUrls().getSmall())
                    .into(random_image);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            Log.d(TAG, "RandomImageFragment: onResponse: isNotSuccessful ");
            random_image.setVisibility(View.GONE);
        }
    }
}