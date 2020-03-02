package com.abros.photoapplication.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abros.photoapplication.room.Photo;
import com.abros.photoapplication.R;
import com.abros.photoapplication.room.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
 The supporting View- the adapter and ViewHolder for inflating and displaying each View unit for the RecyclerView
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(inflatedView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo eachPhoto = photos.get(position);
        holder.tvAuthor.setText(eachPhoto.getAuthor());
        holder.tvDimensions.setText(eachPhoto.getWidth() + " x " + eachPhoto.getHeight());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
                Log.i("IMAGE_LOAD_FAIL", "Image Error"+ exception.getMessage());
//                Toast.makeText(context, "Image Error"+ exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Picasso picasso = builder.build();
        picasso
                .load(eachPhoto.getImgUrl())
                .fit()
                .centerCrop()
                //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .config(Bitmap.Config.RGB_565)
                .into(holder.imgPhoto);




    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        ImageView imgPhoto;
        TextView tvDimensions;


        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvDimensions = itemView.findViewById(R.id.tvDimensions);
        }
    }
}
