package br.com.rg.gabrielsalles.mydemoapp2017.helperclasses;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by gabriel.guedes on 16/05/17.
 */

public class UsefulMethods {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 120);
        return noOfColumns;
    }
}
