package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;


public class FavoritesPresenter {

    private ArrayList<RandomUser> mData = new ArrayList<>();
    private FavoritesInterface view;

    public FavoritesPresenter(FavoritesInterface view) {
        this.view = view;
    }

    public void addLoading() {
        mData = view.getCurrentViewData();
        mData.add(null);
        view.dataAddedInPosition(mData.size() - 1);
    }

    public void requestData() {
        int loadingPosition = mData.size() - 1;
        ArrayList<RandomUser> data = view.getAllRandomUsersFromDatabase();

        mData.remove(loadingPosition);
        view.dataRemovedFromPosition(loadingPosition);
        mData.addAll(data);
        view.dataAddedInRange(loadingPosition, mData.size());
    }

    public ArrayList<RandomUser> getData() {
        return mData;
    }

    public void setData(ArrayList<RandomUser> data) {
        this.mData = data;
    }
}
