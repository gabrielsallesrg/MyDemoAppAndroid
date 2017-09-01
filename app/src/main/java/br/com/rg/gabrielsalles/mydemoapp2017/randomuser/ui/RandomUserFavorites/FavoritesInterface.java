package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;



public interface FavoritesInterface {

    ArrayList<RandomUser> getAllRandomUsersFromDatabase();
    ArrayList<RandomUser> getCurrentViewData();
    void dataAddedInPosition(int position);
    void dataAddedInRange(int positionStart, int itemCount);
    void dataRemovedFromPosition(int position);
}
