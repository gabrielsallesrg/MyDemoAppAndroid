package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

/**
 * Created by gabriel on 31/08/17.
 */

public class UserDetailPresenter {

    UserDetailInterface view;

    private RandomUser mRandomUser;
    private boolean mIsFavorite;
    private boolean mHasNewData = false;

    public UserDetailPresenter(UserDetailInterface view) {
        this.view = view;
    }

    private void saveUser() {
        view.saveUserInDatabase(mRandomUser);
    }

    public void deleteUser() {
        view.deleteUserFromDatabase(mRandomUser);
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

    public void clickFavorite(boolean favorite) {
        mIsFavorite = favorite;
        view.updateFavoriteImageAndBinding();
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
