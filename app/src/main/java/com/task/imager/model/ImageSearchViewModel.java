package com.task.imager.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.imager.MainActivity;

import java.lang.reflect.Type;

import static com.task.imager.RandomImageFragment.TAG;

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
