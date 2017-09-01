package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;

/**
 * Created by gabriel on 31/08/17.
 */

public interface HomeInterface {

    void showRandomUsersView();
    void hideRandomUsersView();
    void saveGendersInDatabase(ArrayList<RandomUserGenderOption> genderOptions);
    ArrayList<RandomUserGenderOption> getGendersFromDatabase();
}
