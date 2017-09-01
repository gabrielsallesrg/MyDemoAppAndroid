package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome;


import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiClient;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUsersData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.UsefulMethods.showDebugLog;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.UsefulMethods.showErrorLog;


public class HomePresenter {

    private ArrayList<RandomUserGenderOption> mGenderOptions;
    private RandomUserApiInterface mApiInterface;
    private int mPage = 0;
    private boolean mLoading = true;
    private ArrayList<RandomUser> mData = new ArrayList<>();
    private int mConnectionFail = 0;
    private HomeInterface view;


    public HomePresenter(HomeInterface view) {
        this.view = view;
        mApiInterface = RandomUserApiClient.getClient().create(RandomUserApiInterface.class);
    }

    public void prepareGenders(String other){
        mGenderOptions = view.getGendersFromDatabase();
        if (!contains(mGenderOptions, "--"))
            mGenderOptions.add(new RandomUserGenderOption(null, "--"));
        if (!contains(mGenderOptions, other))
            mGenderOptions.add(new RandomUserGenderOption(null, other));
    }

    private boolean contains(ArrayList<RandomUserGenderOption> genderOptions , String gender) {
        for (RandomUserGenderOption genderOption: genderOptions) {
            if (genderOption.getGender().equals(gender))
                return true;
        }
        return false;
    }

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean isLoading) {
        this.mLoading = isLoading;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void lastItemRequestData() {
        mLoading = true;
        requestData();
    }

    public void addLoading() {
        mData = view.getCurrentViewData();
        mData.add(null);
        view.dataAddedInPosition(mData.size() - 1);
    }

    public void requestData() {
        mPage++;
        Call call = mApiInterface.doGetRandomUsersData(mPage);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                showDebugLog("RESPONSE CODE ", response.code() + "");
                RandomUsersData randomUsersData = (RandomUsersData) response.body();
                ArrayList<RandomUser> data = (ArrayList<RandomUser>) randomUsersData.getResults();
                for (RandomUser randomUser:data) {
                    if (!contains(mGenderOptions, randomUser.getGender())) {
                        mGenderOptions.add(new RandomUserGenderOption(null, randomUser.getGender()));
                    }
                }
                int loadingPosition = mData.size() - 1;
                mData.remove(loadingPosition);
                view.dataRemovedFromPosition(loadingPosition);
                mData.addAll(data);
                mData.add(null);
                view.dataAddedInRange(loadingPosition, mData.size());
                mLoading = false;
                view.saveGendersInDatabase(mGenderOptions);
                mConnectionFail = 0;
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                showErrorLog("CONNECTION ERROR", t.getMessage());
                showErrorLog("CONNECTION ERROR", t.getLocalizedMessage());
                showErrorLog("CONNECTION ERROR", t.toString());
                mPage--;
                mConnectionFail++;
                if (mConnectionFail >= 3) {
                    view.hideRandomUsersView();
                    mConnectionFail = 0;
                } else {
                    requestData();
                }
            }
        });
    }

    public ArrayList<RandomUser> getData() {
        return mData;
    }

    public void setData(ArrayList<RandomUser> data) {
        this.mData = data;
    }
}
