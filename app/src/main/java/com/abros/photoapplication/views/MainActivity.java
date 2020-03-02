package com.abros.photoapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.abros.photoapplication.NetworkUtils;
import com.abros.photoapplication.room.Photo;
import com.abros.photoapplication.retrofit.PhotoWebService;
import com.abros.photoapplication.R;
import com.abros.photoapplication.retrofit.RetrofitClientInstance;
import com.abros.photoapplication.viewmodels.PhotoViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 The main central View of the application which observes the PhotoViewModel
  also includes an overflow menu of dummy insert, delete and fetch test calls
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Photo> mPhotos;
    PhotoAdapter mAdapter;

    GridLayoutManager mGridLayoutManager;
    PhotoViewModel mPhotoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerV);

        mGridLayoutManager = new GridLayoutManager(this, 2);

       mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
       mPhotoViewModel.getAllPhotosData().observe(this, new Observer<List<Photo>>() {
           @Override
           public void onChanged(List<Photo> photos) {
               updateRecyclerViewWithAdapterData(photos);
           }
       });

    }



    public void fetchDataFromNetwork(MenuItem menuItem) {
        /*Create handle for the RetrofitInstance interface*/
        NetworkUtils.showProgressDialog(this);
        PhotoWebService service = RetrofitClientInstance.getRetrofitInstance().create(PhotoWebService.class);
        Call<List<Photo>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
               NetworkUtils.hideProgressDialog(MainActivity.this);
                mPhotos = response.body();
                updateRecyclerViewWithAdapterData(mPhotos);

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.i("NETWORK_FAILURE", t.getMessage()+" Cause: "+t.getCause());
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error Update")
                        .setMessage("Something went wrong...Please try later!\n" + t.getMessage())
                        .create();

            }
        });
    }

    private void updateRecyclerViewWithAdapterData(List<Photo> photoList) {
        mAdapter = new PhotoAdapter( photoList, this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void deleteAll(MenuItem item) {
        mPhotoViewModel.deleteAll();
        if (mPhotos!= null && mPhotos.size()!= 0) {
            mPhotos.clear();
            mAdapter.notifyDataSetChanged();
        }
        Log.i("EMPTY_STATE", "No data");
        Toast.makeText(this, "All items deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void insertDummyDataIntoDb(MenuItem item) {
        mPhotoViewModel.insert(new Photo("Will Bell", "https://picsum.photos/id/0/5616/3744", 6000));
        mPhotoViewModel.insert(new Photo("Bo Jackson", "https://picsum.photos/id/0/5616/3744", 4000));
        mPhotoViewModel.insert(new Photo("Albie Willows", "https://picsum.photos/id/0/5616/3744", 3000));
        Toast.makeText(this, "Dummy Data inserted", Toast.LENGTH_SHORT).show();
    }


}
