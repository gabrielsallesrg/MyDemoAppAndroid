package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.RootActivity;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.adapters.RandomUserRecyclerViewAdapter;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataIdDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocationDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLoginDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserNameDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPicture;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPictureDao;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.IS_FAVORITED;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_DETAIL;

public class RandomUserFragmentFavorites extends Fragment {

    private RandomUserHomeFragment.OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private RandomUserRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<RandomUser> mData;
    private int mChosenPos = -1;
    private boolean mDrawerNotLoaded = true;

    private RandomUserPictureDao randomUserPictureDao;
    private RandomUserNameDao randomUserNameDao;
    private RandomUserLoginDao randomUserLoginDao;
    private RandomUserLocationDao randomUserLocationDao;
    private RandomUserDataIdDao randomUserDataIdDao;
    private RandomUserDao randomUserDao;


    public RandomUserFragmentFavorites() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        prepareDaos(daoSession);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.random_user_fragment_favorites, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.randomuser_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RandomUserRecyclerViewAdapter(new ArrayList<RandomUser>(), mRecyclerView);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        //mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        prepareForRequestMoreData();

        DrawerLayout drawerLayout = ((RootActivity)view.getContext()).getDrawerLayout();
        drawerLayout.addDrawerListener(new ActionBarDrawerToggle(
                (RootActivity)view.getContext(),
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mDrawerNotLoaded) {
                    requestMoreData();
                    mDrawerNotLoaded = false;
                }
            }
        });

        return view;
    }

    private void prepareForRequestMoreData() {
        mData = mAdapter.getAllData();
        mData.add(null);
        mAdapter.notifyItemInserted(mData.size() - 1);
    }

    private void requestMoreData() {
        mData.remove(mData.size() - 1);
        ArrayList<RandomUser> data = (ArrayList<RandomUser>) randomUserDao.queryBuilder().list();

        for (RandomUser user: data) {
            WhereCondition whereCondition = new WhereCondition.StringCondition("_id = " + user.getRu_id());
            user.setPicture(randomUserPictureDao.queryBuilder().where(whereCondition).unique());
            user.setName(randomUserNameDao.queryBuilder().where(whereCondition).unique());
            user.setLogin(randomUserLoginDao.queryBuilder().where(whereCondition).unique());
            user.setLocation(randomUserLocationDao.queryBuilder().where(whereCondition).unique());
            user.setDataId(randomUserDataIdDao.queryBuilder().where(whereCondition).unique());
        }

        mAdapter.notifyItemRemoved(mData.size());
        mAdapter.addNewData(data);
    }

    private void prepareDaos(DaoSession daoSession) {
        randomUserPictureDao  = daoSession.getRandomUserPictureDao();
        randomUserNameDao     = daoSession.getRandomUserNameDao();
        randomUserLoginDao    = daoSession.getRandomUserLoginDao();
        randomUserLocationDao = daoSession.getRandomUserLocationDao();
        randomUserDataIdDao   = daoSession.getRandomUserDataIdDao();
        randomUserDao         = daoSession.getRandomUserDao();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RANDOM_USER_DETAIL) : {
                if (resultCode == Activity.RESULT_OK && mChosenPos >= 0) {
                    Bundle bundle = data.getExtras();
                    if (!data.getBooleanExtra(IS_FAVORITED, true)) {
                        mAdapter.removePosition(mChosenPos);
                    } else if (data.getBooleanExtra(HAS_NEW_DATA, false)) {
                        RandomUser randomUser = bundle.getParcelable(RANDOM_USER);
                        mAdapter.updatePosition(mChosenPos, randomUser);
                    }
                }
                break;
            }
        }
    }

    public void setChosenUserPos(int pos){
        mChosenPos = pos;
    }
}
