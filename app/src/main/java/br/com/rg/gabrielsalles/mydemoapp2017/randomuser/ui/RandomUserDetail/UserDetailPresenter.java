package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;



public class UserDetailPresenter {

    private UserDetailInterface view;

    private RandomUser mRandomUser;
    private boolean mFavorite;
    private boolean mHasNewData = false;

    public UserDetailPresenter(UserDetailInterface view) {
        this.view = view;
    }

    public void saveUser() {
        view.saveUserInDatabase(mRandomUser);
    }

    public void deleteUser() {
        view.deleteUserFromDatabase(mRandomUser);
    }

    public void setRandomUser(RandomUser randomUser) {
        mRandomUser = randomUser;
        mFavorite = mRandomUser.isFavorite();
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
        mFavorite = favorite;
        view.updateFavoriteImageAndBinding();
        saveOrDelete();
    }

    public void updateFavorite(){
        mRandomUser.setFavorite(mFavorite);
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public boolean isHasNewData() {
        return mHasNewData;
    }

    public void setHasNewData(boolean hasNewData) {
        this.mHasNewData = hasNewData;
    }
}
