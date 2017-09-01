package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.database.RandomUserDatabase;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.CURRENT_PAGE;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USERS_DATAS;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_DETAIL;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.UsefulMethods.calculateNoOfColumns;


public class RandomUserHomeFragment extends Fragment implements HomeInterface {

    private SwipeRefreshLayout mNoInternetLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mRecyclerViewPosition = -1;
    private int mChosenPos = -1;

    private HomePresenter presenter;


    public RandomUserHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this);
        RandomUserDatabase database = new RandomUserDatabase(getActivity().getApplication());
        presenter.prepareGenders(getString(R.string.ru_gender_other));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.random_user_fragment_home, container, false);

        mNoInternetLayout = (SwipeRefreshLayout) view.findViewById(R.id.no_internet_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.randomuser_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        presenter.setAdapter();
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(presenter.getAdapter());
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));
        mLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRecyclerViewPosition = ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                int numItems = presenter.getAdapter().getItemCount();
                if (mRecyclerViewPosition >= numItems - 1 && !presenter.isIsLoading()) {
                    //mRecyclerView.setItemAnimator(null);
                    presenter.endOfTheListRequestMoreData();
                }
            }
        });

        mNoInternetLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRandomUsersView();
                mNoInternetLayout.setRefreshing(false);
                presenter.requestMoreData();
            }
        });

        if (savedInstanceState != null) {
            ArrayList<RandomUser> randomUsers = (ArrayList<RandomUser>)savedInstanceState.getSerializable(RANDOM_USERS_DATAS);
            presenter.setData(randomUsers);
            presenter.getAdapter().notifyItemInserted(0);
            presenter.setPage(savedInstanceState.getInt(CURRENT_PAGE, 0));
            presenter.setIsLoading(false);
        } else {
            presenter.prepareToFirstRequestMoreData();
            presenter.requestMoreData();
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RANDOM_USER_DETAIL) : {
                if (resultCode == Activity.RESULT_OK && mChosenPos >= 0) {
                    Bundle bundle = data.getExtras();
                    if (data.getBooleanExtra(HAS_NEW_DATA, false)) {
                        RandomUser randomUser = (RandomUser) bundle.getSerializable(RANDOM_USER);
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
    public void showRandomUsersView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoInternetLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideRandomUsersView() {
        mNoInternetLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(RANDOM_USERS_DATAS, presenter.getData());
        savedInstanceState.putInt(CURRENT_PAGE, presenter.getPage());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public ArrayList<RandomUserGenderOption> getGendersFromDatabase() {
        RandomUserDatabase database = new RandomUserDatabase(getActivity().getApplication());
        return database.getAllGenderOptions();
    }

    @Override
    public void saveGendersInDatabase(ArrayList<RandomUserGenderOption> genderOptions) {
        RandomUserDatabase database = new RandomUserDatabase(getActivity().getApplication());
        database.saveGenders(genderOptions);
    }
}