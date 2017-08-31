package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.RootActivity;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.IS_FAVORITED;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USERS_DATAS;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_DETAIL;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.UsefulMethods.calculateNoOfColumns;

public class RandomUserFragmentFavorites extends Fragment implements FavoritesInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mChosenPos = -1;
    private boolean mDrawerNotLoaded = true;
    private boolean mInstanceSaved = false;

    private FavoritesPresenter presenter;


    public RandomUserFragmentFavorites() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavoritesPresenter(this);
        presenter.prepareDaos((App) getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.random_user_fragment_favorites, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.randomuser_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        presenter.setAdapter();
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(presenter.getAdapter());
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));
        mLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (savedInstanceState != null) {
            mInstanceSaved = true;
            ArrayList<RandomUser> data = savedInstanceState.getParcelableArrayList(RANDOM_USERS_DATAS);
            presenter.setData(data);
            presenter.getAdapter().notifyItemInserted(0);
        } else {
            presenter.prepareToRequestMoreData();
        }

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
                    if (!mInstanceSaved) {
                        presenter.requestMoreData();
                    }
                    mDrawerNotLoaded = false;
                }
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RANDOM_USER_DETAIL) : {
                if (resultCode == Activity.RESULT_OK && mChosenPos >= 0) {
                    Bundle bundle = data.getExtras();
                    if (!data.getBooleanExtra(IS_FAVORITED, true)) {
                        presenter.getAdapter().removePosition(mChosenPos);
                    } else if (data.getBooleanExtra(HAS_NEW_DATA, false)) {
                        RandomUser randomUser = bundle.getParcelable(RANDOM_USER);
                        presenter.getAdapter().updatePosition(mChosenPos, randomUser);
                    }
                }
                break;
            }
        }
    }

    public void setChosenUserPos(int pos){
        mChosenPos = pos;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(RANDOM_USERS_DATAS, presenter.getData());
        super.onSaveInstanceState(savedInstanceState);
    }
}
