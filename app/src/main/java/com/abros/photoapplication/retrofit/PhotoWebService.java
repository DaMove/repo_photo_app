package com.abros.photoapplication.retrofit;

import com.abros.photoapplication.room.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoWebService {
    @GET("list")
    Call<List<Photo>> getAllPhotos();
}
