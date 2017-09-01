package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

/**
 * Created by gabriel on 31/08/17.
 */

public interface UserDetailInterface {
    void showMapView(String address);
    void showEmailView(String email);
    void showPhoneNumberView(String homeNumber, String cellNumber);
    void updateFavoriteImageAndBinding();

    void saveUserInDatabase(RandomUser randomUser);
    void deleteUserFromDatabase(RandomUser randomUser);


    void callEditUserView();
}
