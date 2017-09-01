package br.com.rg.gabrielsalles.mydemoapp2017.helperclasses;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;


public class UsefulMethods {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return  (int) (dpWidth / 120);
    }

    public static void showErrorLog(String logTitle, String logMessage) {
        Log.e(logTitle, logMessage);
    }

    public static void showDebugLog(String logTitle, String logMessage) {
        Log.d(logTitle, logMessage);
    }

}
