package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites;

import android.app.Application;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.adapters.RandomUserRecyclerViewAdapter;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataIdDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocationDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLoginDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserNameDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPictureDao;


public class FavoritesPresenter {

    private RandomUserPictureDao mRandomUserPictureDao;
    private RandomUserNameDao mRandomUserNameDao;
    private RandomUserLoginDao mRandomUserLoginDao;
    private RandomUserLocationDao mRandomUserLocationDao;
    private RandomUserDataIdDao mRandomUserDataIdDao;
    private RandomUserDao mRandomUserDao;

    private ArrayList<RandomUser> mData = new ArrayList<>();
    private RandomUserRecyclerViewAdapter mAdapter;

    FavoritesInterface view;

    public FavoritesPresenter(FavoritesInterface view) {
        this.view = view;
    }

    public void prepareDaos(Application application) {
        final DaoSession daoSession = ((App) application).getDaoSession();

        mRandomUserPictureDao = daoSession.getRandomUserPictureDao();
        mRandomUserNameDao = daoSession.getRandomUserNameDao();
        mRandomUserLoginDao = daoSession.getRandomUserLoginDao();
        mRandomUserLocationDao = daoSession.getRandomUserLocationDao();
        mRandomUserDataIdDao = daoSession.getRandomUserDataIdDao();
        mRandomUserDao = daoSession.getRandomUserDao();
    }

    public void prepareToRequestMoreData() {
        mData = mAdapter.getAllData();
        mData.add(null);
        mAdapter.notifyItemInserted(mData.size() - 1);
    }

    public void requestMoreData() {
        int previousLastPosition = mData.size() - 1;
        ArrayList<RandomUser> data = (ArrayList<RandomUser>) mRandomUserDao.queryBuilder().list();

        for (RandomUser user: data) {
            WhereCondition whereCondition = new WhereCondition.StringCondition("_id = " + user.getRu_id());
            user.setPicture(mRandomUserPictureDao.queryBuilder().where(whereCondition).unique());
            user.setName(mRandomUserNameDao.queryBuilder().where(whereCondition).unique());
            user.setLogin(mRandomUserLoginDao.queryBuilder().where(whereCondition).unique());
            user.setLocation(mRandomUserLocationDao.queryBuilder().where(whereCondition).unique());
            user.setDataId(mRandomUserDataIdDao.queryBuilder().where(whereCondition).unique());
        }

        mData.remove(previousLastPosition);
        mAdapter.notifyItemRemoved(previousLastPosition);
        mData.addAll(data);
        mAdapter.notifyItemRangeInserted(previousLastPosition, mData.size());
    }

    public ArrayList<RandomUser> getData() {
        return mData;
    }

    public void setData(ArrayList<RandomUser> data) {
        this.mData = data;
    }

    public RandomUserRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter() {
        mAdapter = new RandomUserRecyclerViewAdapter(mData);
    }
}
