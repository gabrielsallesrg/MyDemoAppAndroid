package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiClient;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.API.RandomUserApiInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.adapters.RandomUserRecyclerViewAdapter;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOptionDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUsersData;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_DETAIL;


public class RandomUserHomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private RandomUserRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RandomUserApiInterface mApiInterface;
    private boolean mIsLoading = true;
    ArrayList<RandomUser> mData;
    private int mPage = 0;
    private int mScrollState = 0;
    private int mChosenPos = -1;
    private RandomUserGenderOptionDao mRandomUserGenderOptionDao;
    private ArrayList<RandomUserGenderOption> mGenderOptions;


    public RandomUserHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        mRandomUserGenderOptionDao = daoSession.getRandomUserGenderOptionDao();
        mGenderOptions = (ArrayList<RandomUserGenderOption>) 
                        mRandomUserGenderOptionDao.queryBuilder()
                                .orderAsc(RandomUserGenderOptionDao.Properties.Gender)
                                .list();
        if (!contains(mGenderOptions, "--"))
            mGenderOptions.add(new RandomUserGenderOption(null, "--"));
        if (!contains(mGenderOptions, getString(R.string.ru_gender_other)))
            mGenderOptions.add(new RandomUserGenderOption(null, getString(R.string.ru_gender_other)));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.random_user_fragment_home, container, false);

        mApiInterface = RandomUserApiClient.getClient().create(RandomUserApiInterface.class);

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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mScrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
                int numItems = mAdapter.getItemCount();
                if (pos >= numItems - 1 && !mIsLoading) {
                    mRecyclerView.setItemAnimator(null);
                    mIsLoading = true;
                    prepareForRequestMoreData();
                    if (mScrollState == 0 || mScrollState == 2)
                        mRecyclerView.scrollToPosition(pos + 1);
                    requestMoreData();
                }
            }
        });

        prepareForRequestMoreData();
        requestMoreData();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void prepareForRequestMoreData() {
        mData = mAdapter.getAllData();
        mData.add(null);
        mAdapter.notifyItemInserted(mData.size() - 1);
    }

    private void requestMoreData() {
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
                mData.remove(mData.size() - 1);
                mAdapter.notifyItemRemoved(mData.size());
                mAdapter.addNewData(data);
                mIsLoading = false;
                saveGenders();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "Connection error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("CONNECTION ERROR", t.getMessage());
                Log.e("CONNECTION ERROR", t.getLocalizedMessage());
                Log.e("CONNECTION ERROR", t.toString());
                mPage--;
                requestMoreData();
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RANDOM_USER_DETAIL) : {
                if (resultCode == Activity.RESULT_OK && mChosenPos >= 0) {
                    Bundle bundle = data.getExtras();
                    if (data.getBooleanExtra(HAS_NEW_DATA, false)) {
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


