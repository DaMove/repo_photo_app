package com.abros.photoapplication.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abros.photoapplication.NetworkUtils;
import com.abros.photoapplication.repositories.NetworkRepository;
import com.abros.photoapplication.room.Photo;
import com.abros.photoapplication.repositories.PhotoRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
 To hold and process data for the View layer (the User Interface) and communicate with the model
 It requests data from the photoRepository so the Activity or Fragment observing it can use that data to update the UI
 and it can use the photoRepository to launch database operations

 This ViewModel also holds a reference to our network repository
 */
public class PhotoViewModel extends AndroidViewModel {
    PhotoRepository photoRepository;
    NetworkRepository networkRepository;


    LiveData<List<Photo>> allPhotosData;

    public PhotoViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
        networkRepository = new NetworkRepository(application);

        try {
            if (photoRepository.countAllPhotos() == 0) { // if database empty then load data from network repo

                networkRepository.fetchDataFromNetwork();
                            }
            allPhotosData = photoRepository.getAllPhotosData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Photo photo) {
        photoRepository.insert(photo);
    }

    public void update(Photo photo) {
        photoRepository.update(photo);
    }

    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }

    public void deleteAll() {
        photoRepository.deleteAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotosData() {
        return allPhotosData;
    }
}
