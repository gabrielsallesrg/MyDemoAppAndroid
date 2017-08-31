package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import android.widget.ImageButton;

/**
 * Created by gabriel on 31/08/17.
 */

public interface UserDetailInterface {
    void showMapView(String address);
    void showEmailView(String email);
    void showPhoneNumberView(String homeNumber, String cellNumber);
    void updateFavoriteImageAndBinding(ImageButton favoriteButton);

    void callEditUserView();
}
