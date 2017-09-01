package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityDetailBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.database.RandomUserDatabase;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog.RandomUserChoosePhoneDialog;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit.RandomUserEditActivity;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HOME_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.IS_FAVORITED;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.MOBILE_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_EDIT;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.TOOLBAR_TITLE;

public class RandomUserDetailActivity extends AppCompatActivity implements UserDetailInterface{

    private RandomUserActivityDetailBinding binding;

    private UserDetailPresenter presenter;
    private RandomUserDatabase mDatabase;
    private ImageButton mFavoriteImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        presenter = new UserDetailPresenter(this);
        mDatabase = new RandomUserDatabase(getApplication());
        binding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_detail);
        final Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        presenter.setRandomUser((RandomUser)bundle.getSerializable(RANDOM_USER));
        binding.setRandomuser(presenter.getRandomUser());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra(TOOLBAR_TITLE));

        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavoriteImageButton = (ImageButton) view;
                presenter.clickFavorite(!binding.getRandomuser().isFavorite());
                presenter.setHasNewData(true);
            }
        });

        binding.mapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showMap();
            }
        });

        binding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showEmail();
            }
        });

        binding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showPhoneNumbers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random_user_detail_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                prepareActivityResults();
                supportFinishAfterTransition();
                return true;

            case R.id.edit:
                presenter.editUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void imageClick(View v) {
        presenter.editUser();
    }

    @Override
    public void showMapView(String address) {
        Uri mapsUri = Uri.parse("geo:0,0?q=" + Uri.encode(binding.getRandomuser().getAddressForMaps()));
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
        mapsIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapsIntent);
    }

    @Override
    public void showEmailView(String email) {
        Uri mailUri = Uri.parse("mailto:" + email);
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        startActivity(mailIntent);
    }

    @Override
    public void showPhoneNumberView(String homeNumber, String cellNumber) {
        Intent phoneIntent = new Intent(this, RandomUserChoosePhoneDialog.class);
        phoneIntent.putExtra(HOME_NUMBER,   homeNumber);
        phoneIntent.putExtra(MOBILE_NUMBER, cellNumber);
        startActivity(phoneIntent);
    }

    @Override
    public void updateFavoriteImageAndBinding() {
        mFavoriteImageButton.setImageResource((presenter.ismIsFavorite()) ? (android.R.drawable.star_big_on) : (R.drawable.ic_star_outline_grey600_36dp));
        binding.getRandomuser().setFavorite(presenter.ismIsFavorite());
    }

    @Override
    public void saveUserInDatabase(RandomUser randomUser) {
        mDatabase.saveUser(randomUser);
    }

    @Override
    public void deleteUserFromDatabase(RandomUser randomUser) {
        mDatabase.deleteUser(randomUser);
    }

    @Override
    public void callEditUserView() {
        Pair<View, String> pair = new Pair<>(findViewById(R.id.thumbnail), getResources().getString(R.string.transition_random_user_image));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);

        Intent intent = new Intent(this, RandomUserEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RANDOM_USER, presenter.getRandomUser());
        intent.putExtras(bundle);
        startActivityForResult(intent, RANDOM_USER_EDIT, options.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RANDOM_USER_EDIT) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (data.getBooleanExtra(HAS_NEW_DATA, false)) {
                        presenter.setRandomUser((RandomUser)bundle.getSerializable(RANDOM_USER));
                        presenter.saveIfFavorite();
                        binding.setRandomuser(presenter.getRandomUser());
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        prepareActivityResults();
        super.onBackPressed();
    }

    private void prepareActivityResults() {
        presenter.updateFavorite();
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RANDOM_USER, presenter.getRandomUser());
        resultIntent.putExtras(bundle);
        resultIntent.putExtra(HAS_NEW_DATA, presenter.isHasNewData());
        resultIntent.putExtra(IS_FAVORITED, presenter.ismIsFavorite());
        setResult(RESULT_OK, resultIntent);
    }
}
