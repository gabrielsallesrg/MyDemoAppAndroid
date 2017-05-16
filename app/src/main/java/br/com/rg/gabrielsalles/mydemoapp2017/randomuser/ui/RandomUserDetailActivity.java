package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

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

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityDetailBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataIdDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocationDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLoginDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserNameDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPictureDao;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HOME_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.IS_FAVORITED;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.MOBILE_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER_EDIT;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.TOOLBAR_TITLE;

public class RandomUserDetailActivity extends AppCompatActivity {

    private RandomUserPictureDao randomUserPictureDao;
    private RandomUserNameDao randomUserNameDao;
    private RandomUserLoginDao randomUserLoginDao;
    private RandomUserLocationDao randomUserLocationDao;
    private RandomUserDataIdDao randomUserDataIdDao;
    private RandomUserDao randomUserDao;
    private RandomUser mRandomUser;
    private boolean isFavorite;
    private boolean hasNewData = false;
    private RandomUserActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);

        final DaoSession daoSession = ((App) getApplication()).getDaoSession();
        prepareDaos(daoSession);

        binding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_detail);
        final Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        mRandomUser = bundle.getParcelable(RANDOM_USER);
        isFavorite = mRandomUser.isFavorite();
        binding.setRandomuser(mRandomUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra(TOOLBAR_TITLE));

        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite = !binding.getRandomuser().isFavorite();
                ((ImageButton)view).setImageResource((isFavorite) ? (android.R.drawable.star_big_on) : (R.drawable.ic_star_outline_grey600_36dp));
                binding.getRandomuser().setFavorite(isFavorite);
                hasNewData = true;

                if (isFavorite) {
                    // Save
                    saveUser(binding.getRandomuser());

                } else {
                    // Delete
                    deleteUser(binding.getRandomuser());
                }
            }
        });

        binding.mapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapsUri = Uri.parse("geo:0,0?q=" + Uri.encode(binding.getRandomuser().getAddressForMaps()));
                Intent mapsIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
                mapsIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapsIntent);
            }
        });

        binding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mailUri = Uri.parse("mailto:" + binding.getRandomuser().getEmail());
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
                startActivity(mailIntent);
            }
        });

        binding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(v.getContext(), RandomUserChoosePhoneDialog.class);
                phoneIntent.putExtra(HOME_NUMBER,   binding.getRandomuser().getPhone());
                phoneIntent.putExtra(MOBILE_NUMBER, binding.getRandomuser().getCell());
                startActivity(phoneIntent);
            }
        });
    }

    private void saveUser(RandomUser randomUser) {
        randomUserPictureDao.insertOrReplace(randomUser.getPicture());
        randomUserNameDao.insertOrReplace(randomUser.getName());
        randomUserLoginDao.insertOrReplace(randomUser.getLogin());
        randomUserLocationDao.insertOrReplace(randomUser.getLocation());
        randomUserDataIdDao.insertOrReplace(randomUser.getDataId());
        randomUserDao.insertOrReplace(randomUser);
    }

    private void deleteUser(RandomUser randomUser) {
        randomUserPictureDao.delete(randomUser.getPicture());
        randomUserNameDao.delete(randomUser.getName());
        randomUserLoginDao.delete(randomUser.getLogin());
        randomUserLocationDao.delete(randomUser.getLocation());
        randomUserDataIdDao.delete(randomUser.getDataId());
        randomUserDao.delete(randomUser);
        randomUser.deleteId();
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
                editUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void imageClick(View v) {
        editUser();
    }

    private void editUser() {
        Pair<View, String> pair = new Pair<>(findViewById(R.id.thumbnail), getResources().getString(R.string.transition_random_user_image));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);

        Intent intent = new Intent(this, RandomUserEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RANDOM_USER, mRandomUser);
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
                        mRandomUser = bundle.getParcelable(RANDOM_USER);
                        binding.setRandomuser(mRandomUser);
                        hasNewData = true;
                        if (mRandomUser.isFavorite()) {
                            saveUser(mRandomUser);
                        }
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
        mRandomUser.setFavorite(isFavorite);
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RANDOM_USER, mRandomUser);
        resultIntent.putExtras(bundle);
        resultIntent.putExtra(HAS_NEW_DATA, hasNewData);
        resultIntent.putExtra(IS_FAVORITED, isFavorite);
        setResult(RESULT_OK, resultIntent);
    }
}
