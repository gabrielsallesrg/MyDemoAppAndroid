package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;


public class FavoritesPresenter {

    private ArrayList<RandomUser> mData = new ArrayList<>();

    FavoritesInterface view;

    public FavoritesPresenter(FavoritesInterface view) {
        this.view = view;
    }


    public void prepareToRequestMoreData() {
        mData = view.getCurrentViewData();
        mData.add(null);
        view.dataAddedInPosition(mData.size() - 1);
    }

    public void requestMoreData() {
        int previousLastPosition = mData.size() - 1;
        ArrayList<RandomUser> data = view.getAllRandomUsersFromDatabase();

        mData.remove(previousLastPosition);
        view.dataRemovedFromPosition(previousLastPosition);
        mData.addAll(data);
        view.dataAddedInRange(previousLastPosition, mData.size());
    }

    public ArrayList<RandomUser> getData() {
        return mData;
    }

    public void setData(ArrayList<RandomUser> data) {
        this.mData = data;
    }
}
