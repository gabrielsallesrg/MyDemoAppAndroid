package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiClient;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.adapters.RandomUserRecyclerViewAdapter;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOptionDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUsersData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabriel on 31/08/17.
 */

public class HomePresenter {

    HomeInterface view;
    private RandomUserGenderOptionDao mRandomUserGenderOptionDao;
    private ArrayList<RandomUserGenderOption> mGenderOptions;
    private RandomUserApiInterface mApiInterface;
    private RandomUserRecyclerViewAdapter mAdapter;
    private int mPage = 0;
    private boolean mIsLoading = true;
    ArrayList<RandomUser> mData = new ArrayList<>();
    private int mConnectionFail = 0;

    public HomePresenter(HomeInterface view) {
        this.view = view;
        mApiInterface = RandomUserApiClient.getClient().create(RandomUserApiInterface.class);
    }

    public void prepareGenders(Application application, String other){
        final DaoSession daoSession = ((App) application).getDaoSession();
        mRandomUserGenderOptionDao = daoSession.getRandomUserGenderOptionDao();
        mGenderOptions = (ArrayList<RandomUserGenderOption>)
                mRandomUserGenderOptionDao.queryBuilder()
                        .orderAsc(RandomUserGenderOptionDao.Properties.Gender)
                        .list();
        if (!contains(mGenderOptions, "--"))
            mGenderOptions.add(new RandomUserGenderOption(null, "--"));
        if (!contains(mGenderOptions, other))
            mGenderOptions.add(new RandomUserGenderOption(null, other));
    }

    public void setAdapter() {
        mAdapter = new RandomUserRecyclerViewAdapter(mData);
    }

    public RandomUserRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    private boolean contains(ArrayList<RandomUserGenderOption> genderOptions , String gender) {
        for (RandomUserGenderOption genderOption: genderOptions) {
            if (genderOption.getGender().equals(gender))
                return true;
        }
        return false;
    }

    private void saveGenders() {
        for (RandomUserGenderOption genderOption: mGenderOptions) {
            mRandomUserGenderOptionDao.insertOrReplace(genderOption);
        }
    }

    public boolean isIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
    }

    public ArrayList<RandomUser> getData() {
        return mData;
    }

    public void setData(ArrayList<RandomUser> data) {
        this.mData = data;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void endOfTheListRequestMoreData() {
        mIsLoading = true;
        requestMoreData();
    }

    public void prepareToFirstRequestMoreData() {
        mData = mAdapter.getAllData();
        mData.add(null);
        mAdapter.notifyItemInserted(mData.size() - 1);
    }


    public void requestMoreData() {
        mPage++;
        Call call = mApiInterface.doGetRandomUsersData(mPage);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("RESPONSE CODE ", response.code() + "");

                RandomUsersData randomUsersData = (RandomUsersData) response.body();
                ArrayList<RandomUser> data = (ArrayList<RandomUser>) randomUsersData.getResults();
                for (RandomUser randomUser:data) {
                    if (!contains(mGenderOptions, randomUser.getGender())) {
                        mGenderOptions.add(new RandomUserGenderOption(null, randomUser.getGender()));
                    }
                }
                int previousLastPosition = mData.size() - 1;
                mData.remove(previousLastPosition);
                mAdapter.notifyItemRemoved(previousLastPosition);
                mData.addAll(data);
                mData.add(null);
                mAdapter.notifyItemRangeInserted(previousLastPosition, mData.size());
                mIsLoading = false;
                saveGenders();
                mConnectionFail = 0;
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("CONNECTION ERROR", t.getMessage());
                Log.e("CONNECTION ERROR", t.getLocalizedMessage());
                Log.e("CONNECTION ERROR", t.toString());
                mPage--;
                mConnectionFail++;
                if (mConnectionFail >= 3) {
                    view.hideRandomUsersView();
                    mConnectionFail = 0;
                } else {
                    requestMoreData();
                }
            }
        });
    }
}
