package com.abros.photoapplication.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Photo.class, version = 1)
public abstract class PhotoDatabase extends RoomDatabase {
    private static PhotoDatabase instance;


    public abstract  PhotoDAO photoDAO();

    public static synchronized PhotoDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PhotoDatabase.class, "photos_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

     /*
      Dummy data for app first creation time
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTask(instance).execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void >{

        private PhotoDAO photoDAO;

        public PopulateDbTask(PhotoDatabase database) {
            this.photoDAO = database.photoDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            photoDAO.insertPhoto(new Photo("Will Bell", "https://picsum.photos/id/0/5616/3744", 6000));
            photoDAO.insertPhoto(new Photo("Mo Peete", "https://picsum.photos/id/0/5616/3744", 4000));
            photoDAO.insertPhoto(new Photo("Ben Jason", "https://picsum.photos/id/0/5616/3744", 3000));
            photoDAO.insertPhoto(new Photo("Wendy Mann", "https://picsum.photos/id/0/5616/3744", 5000));
            return null;
        }
    }

}

