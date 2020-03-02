package com.abros.photoapplication.repositories;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.abros.photoapplication.room.Photo;
import com.abros.photoapplication.room.PhotoDAO;
import com.abros.photoapplication.room.PhotoDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
 This acts as an intermediary abstraction layer between the data and the ViewModel
 As a best practice because it provides an abstraction layer on top of the data sources
 */
public class PhotoRepository {
    private PhotoDAO photoDAO;
    private LiveData<List<Photo>> allPhotosData;

    public PhotoRepository(Application application) {
        PhotoDatabase database =PhotoDatabase.getInstance(application);
        photoDAO = database.photoDAO();
        allPhotosData = photoDAO.getAllPhotos();
    }

    /*
     The ff are the methods the repository exposes to the outside ie the ViewModel
     */
    public void insert(Photo photo) {
        new InsertPhotoTask(photoDAO).execute(photo);
    }

    public void update(Photo photo) {
        new UpdatePhotoTask(photoDAO).execute(photo);
    }

    public void delete(Photo photo) {
        new DeletePhotoTask(photoDAO).execute(photo);
    }

    public void deleteAllPhotos() {
        new DeleteAllPhotosTask(photoDAO).execute();
    }

    public LiveData<List<Photo>> getAllPhotosData() {
        return allPhotosData;
    }

    public Integer countAllPhotos() throws ExecutionException, InterruptedException {
        return new CountALLPhotosTask(photoDAO).execute().get();
    }


    private static class InsertPhotoTask extends AsyncTask<Photo, Void, Void>{

        private PhotoDAO photoDAO;

        private InsertPhotoTask(PhotoDAO photoDAO){
            this.photoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDAO.insertPhoto(photos[0]);
            return null;
        }
    }

    private static class UpdatePhotoTask extends AsyncTask<Photo, Void, Void>{

        private PhotoDAO photoDAO;

        private UpdatePhotoTask(PhotoDAO photoDAO){
            this.photoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDAO.updatePhoto(photos[0]);
            return null;
        }
    }

    private static class DeletePhotoTask extends AsyncTask<Photo, Void, Void>{

        private PhotoDAO photoDAO;

        private DeletePhotoTask(PhotoDAO photoDAO){
            this.photoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDAO.deletePhoto(photos[0]);
            return null;
        }
    }

    private static class DeleteAllPhotosTask extends AsyncTask<Void, Void, Void>{

        private PhotoDAO photoDAO;

        private DeleteAllPhotosTask(PhotoDAO photoDAO){
            this.photoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            photoDAO.deleteAllNotes();
            return null;
        }
    }

    private static class CountALLPhotosTask extends AsyncTask<Void, Void, Integer> {

        private PhotoDAO photoDAO;
        private CountALLPhotosTask(PhotoDAO photoDAO){
            this.photoDAO = photoDAO;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return photoDAO.countAllPhotos();
        }
    }


}

