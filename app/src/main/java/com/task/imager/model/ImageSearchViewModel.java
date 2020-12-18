package com.task.imager.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ImageSearchViewModel extends AndroidViewModel {
    private MutableLiveData<String> query = new MutableLiveData<>();

    public ImageSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getData(){
        if (query == null) {
            query = new MutableLiveData<>();
        }
        return query;
    }
}
