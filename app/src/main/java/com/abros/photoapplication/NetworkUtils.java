package com.abros.photoapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.ProgressBar;


public class NetworkUtils {

    static ProgressDialog progressDialog;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void checkNetworkFromSettings(final Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Api call is taking place...");
        progressDialog.show();
    }

    NetworkUtils(){

    }

    public static void hideProgressDialog(Context context) {
        progressDialog.hide();
    }
}
