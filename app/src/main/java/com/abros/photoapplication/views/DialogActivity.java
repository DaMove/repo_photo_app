package com.abros.photoapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abros.photoapplication.R;
/*
 Merely a temporary dialog activity workaround for displaying and hiding progress
 */
public class DialogActivity extends AppCompatActivity {
    public static final String ACTION_CLOSE_DIALOG = "ACTION_CLOSE_DIALOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setTitle("Loading...");
        Log.i("LOADING_STATE", "api call is taking place");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (ACTION_CLOSE_DIALOG.equals(intent.getAction())){
             finish();
        }
    }



}
