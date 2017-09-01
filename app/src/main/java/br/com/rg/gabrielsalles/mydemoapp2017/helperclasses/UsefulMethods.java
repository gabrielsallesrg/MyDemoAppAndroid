package br.com.rg.gabrielsalles.mydemoapp2017.helperclasses;

import android.content.Context;
import android.util.DisplayMetrics;



public class UsefulMethods {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return  (int) (dpWidth / 120);
    }
}
