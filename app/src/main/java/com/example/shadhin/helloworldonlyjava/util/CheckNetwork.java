package com.example.shadhin.helloworldonlyjava.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.UUID;

/**
 * Created by enamul on 5/9/18.
 */

public class CheckNetwork {

    public static String ramdomChar() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8);
    }


    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
