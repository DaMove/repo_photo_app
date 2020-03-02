package com.abros.photoapplication.repositories;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abros.photoapplication.NetworkUtils;
import com.abros.photoapplication.room.Photo;
import com.abros.photoapplication.retrofit.PhotoWebService;
import com.abros.photoapplication.retrofit.RetrofitClientInstance;
import com.abros.photoapplication.views.DialogActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abros.photoapplication.views.DialogActivity.ACTION_CLOSE_DIALOG;

/*
 This repository is responsible for fetching the data from the web service using Retrofit
 */
public class NetworkRepository {
    Application application;

    public NetworkRepository(Application application) {
        this.application = application;
    }

    private List<Photo> photosRetrieved = new ArrayList<>();

    public LiveData<List<Photo>> fetchDataFromNetwork() {

        final MutableLiveData<List<Photo>> liveData = new MutableLiveData<>();

        application.startActivity(new Intent(application, DialogActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        /*Create handle for the RetrofitInstance interface*/
        PhotoWebService service = RetrofitClientInstance.getRetrofitInstance().create(PhotoWebService.class);
        Call<List<Photo>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {

                photosRetrieved = response.body();
                PhotoRepository photoRepository = new PhotoRepository(application);
                for (Photo photo : photosRetrieved) {
                    photoRepository.insert(photo);
                }
                liveData.setValue(photosRetrieved);

                Intent hideDialogIntent = new Intent().setAction(ACTION_CLOSE_DIALOG).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(hideDialogIntent);

                Log.i("SUCCESS", "Working " + response.body().toString());
                Log.i("CONTENT_STATE", "There is data to display");
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.i("ERROR_STATE", "API call failed!");
                Log.i("ERROR", "Error Message: " + t.getMessage() + "Cause: " + t.getCause());
                Toast.makeText(application, "Error Message: " + t.getMessage() + "Cause: " + t.getCause(), Toast.LENGTH_SHORT).show();
                NetworkUtils.checkNetworkFromSettings(application);
            }
        });

        return liveData;
    }


}
