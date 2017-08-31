package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import android.app.Application;
import android.widget.ImageButton;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityDetailBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataIdDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocationDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLoginDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserNameDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPictureDao;

/**
 * Created by gabriel on 31/08/17.
 */

public class UserDetailPresenter {

    UserDetailInterface view;

    private RandomUserPictureDao randomUserPictureDao;
    private RandomUserNameDao randomUserNameDao;
    private RandomUserLoginDao randomUserLoginDao;
    private RandomUserLocationDao randomUserLocationDao;
    private RandomUserDataIdDao randomUserDataIdDao;
    private RandomUserDao randomUserDao;
    private RandomUser mRandomUser;
    private boolean mIsFavorite;
    private boolean mHasNewData = false;
    private RandomUserActivityDetailBinding binding;

    public UserDetailPresenter(UserDetailInterface view) {
        this.view = view;
    }

    public void prepareDaos(Application application) {
        final DaoSession daoSession = ((App) application).getDaoSession();
        randomUserPictureDao  = daoSession.getRandomUserPictureDao();
        randomUserNameDao     = daoSession.getRandomUserNameDao();
        randomUserLoginDao    = daoSession.getRandomUserLoginDao();
        randomUserLocationDao = daoSession.getRandomUserLocationDao();
        randomUserDataIdDao   = daoSession.getRandomUserDataIdDao();
        randomUserDao         = daoSession.getRandomUserDao();
    }

    private void saveUser() {
        randomUserPictureDao.insertOrReplace(mRandomUser.getPicture());
        randomUserNameDao.insertOrReplace(mRandomUser.getName());
        randomUserLoginDao.insertOrReplace(mRandomUser.getLogin());
        randomUserLocationDao.insertOrReplace(mRandomUser.getLocation());
        randomUserDataIdDao.insertOrReplace(mRandomUser.getDataId());
        randomUserDao.insertOrReplace(mRandomUser);
    }

    public void deleteUser() {
        randomUserPictureDao.delete(mRandomUser.getPicture());
        randomUserNameDao.delete(mRandomUser.getName());
        randomUserLoginDao.delete(mRandomUser.getLogin());
        randomUserLocationDao.delete(mRandomUser.getLocation());
        randomUserDataIdDao.delete(mRandomUser.getDataId());
        randomUserDao.delete(mRandomUser);
        mRandomUser.deleteId();
    }

    public void setRandomUser(RandomUser randomUser) {
        mRandomUser = randomUser;
        mIsFavorite = mRandomUser.isFavorite();
    }

    public RandomUser getRandomUser() {
        return mRandomUser;
    }

    public void showMap() {
        view.showMapView(mRandomUser.getAddressForMaps());
    }

    public void showEmail() {
        view.showEmailView(mRandomUser.getEmail());
    }

    public void showPhoneNumbers() {
        view.showPhoneNumberView(mRandomUser.getPhone(), mRandomUser.getCell());
    }

    public void editUser() {
        view.callEditUserView();
    }

    public void saveIfFavorite() {
        mHasNewData = true;
        if (mRandomUser.isFavorite()) {
            saveUser();
        }
    }

    public void saveOrDelete() {
        if (mRandomUser.isFavorite()) {
            saveUser();
        } else {
            deleteUser();
        }
    }

    public void clickFavorite(boolean favorite, ImageButton favoriteButton) {
        mIsFavorite = favorite;
        view.updateFavoriteImageAndBinding(favoriteButton);
        saveOrDelete();
    }

    public void updateFavorite(){
        mRandomUser.setFavorite(mIsFavorite);
    }

    public boolean ismIsFavorite() {
        return mIsFavorite;
    }

    public boolean isHasNewData() {
        return mHasNewData;
    }

    public void setHasNewData(boolean hasNewData) {
        this.mHasNewData = hasNewData;
    }
}
