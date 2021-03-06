package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;



public interface HomeInterface {

    void showRandomUsersView();
    void hideRandomUsersView();
    void saveGendersInDatabase(ArrayList<RandomUserGenderOption> genderOptions);
    ArrayList<RandomUserGenderOption> getGendersFromDatabase();
    ArrayList<RandomUser> getCurrentViewData();
    void dataAddedInPosition(int position);
    void dataAddedInRange(int positionStart, int itemCount);
    void dataRemovedFromPosition(int position);
}
