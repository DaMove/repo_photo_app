package com.abros.photoapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao /* A way to access data in the database: the place for your database operations */
public interface PhotoDAO {

    @Insert
    public void insertPhoto(Photo newPhoto);

    @Update
    public void updatePhoto(Photo newPhotoData);

    @Delete
    public void deletePhoto(Photo photoToDelete);

    @Query("DELETE FROM photos")
    public void deleteAllNotes();

    @Query("SELECT * FROM photos")
    public LiveData<List<Photo>> getAllPhotos();

    @Query("SELECT * FROM photos WHERE author LIKE :author")
    public List<Photo> getPhotosByAuthor(String author);

    @Query("SELECT * FROM photos WHERE author LIKE :author")
    public Photo getPhotoByAuthor(String author);

    @Query("SELECT author FROM photos WHERE id = :id")
    public String findNameById(int id);

    @Query("SELECT COUNT(*) FROM photos")
    public int countAllPhotos();

}

