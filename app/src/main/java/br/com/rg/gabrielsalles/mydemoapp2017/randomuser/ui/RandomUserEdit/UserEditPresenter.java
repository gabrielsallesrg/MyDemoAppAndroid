package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit;

import android.app.Application;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOptionDao;

/**
 * Created by gabriel on 31/08/17.
 */

public class UserEditPresenter {

    UserEditInterface view;
    private RandomUser mRandomUser;
    private String mCurrentPhotoPath = "";
    private boolean mPictureChanged = false;

    public UserEditPresenter(UserEditInterface view) {
        this.view = view;
    }

    public RandomUser getRandomUser() {
        return mRandomUser;
    }

    public void setRandomUser(RandomUser mRandomUser) {
        this.mRandomUser = mRandomUser;
    }

    public boolean changesWereMade(String title, String firstName, String lastName,
                                   String homePhone, String cellPhone, String email,
                                   String gender, String street, String city, String state,
                                   String postcode, String nationality) {

        return mPictureChanged || !(
                        title.equals(mRandomUser.getName().getTitle())           &&
                        firstName.equals(mRandomUser.getName().getFirst())       &&
                        lastName.equals(mRandomUser.getName().getLast())         &&
                        homePhone.equals(mRandomUser.getPhone())                 &&
                        cellPhone.equals(mRandomUser.getCell())                  &&
                        email.equals(mRandomUser.getEmail())                     &&
                        gender.equals(mRandomUser.getGender())                   &&
                        street.equals(mRandomUser.getLocation().getStreet())     &&
                        city.equals(mRandomUser.getLocation().getCity())         &&
                        state.equals(mRandomUser.getLocation().getState())       &&
                        postcode.equals(mRandomUser.getLocation().getPostcode()) &&
                        nationality.equals(mRandomUser.getNat())
        );
    }

    public void updateRandomUser(String title, String firstName, String lastName,
                            String homePhone, String cellPhone, String email,
                            String gender, String street, String city, String state,
                            String postcode, String nationality) {

        mRandomUser.getName().setTitle(title);
        mRandomUser.getName().setFirst(firstName);
        mRandomUser.getName().setLast(lastName);
        mRandomUser.setPhone(homePhone);
        mRandomUser.setCell(cellPhone);
        mRandomUser.setEmail(email);
        mRandomUser.setGender(gender);
        mRandomUser.getLocation().setStreet(street);
        mRandomUser.getLocation().setCity(city);
        mRandomUser.getLocation().setState(state);
        mRandomUser.getLocation().setPostcode(postcode);
        mRandomUser.setNat(nationality);
        if (mPictureChanged)
            mRandomUser.getPicture().setLarge(mCurrentPhotoPath);
    }

    public ArrayList<String> getGenderOptions(Application application) {
        final DaoSession daoSession = ((App) application).getDaoSession();
        final RandomUserGenderOptionDao randomUserGenderOptionDao = daoSession.getRandomUserGenderOptionDao();
        ArrayList<RandomUserGenderOption> genderOptions = (ArrayList<RandomUserGenderOption>)
                randomUserGenderOptionDao.queryBuilder()
                        .orderAsc(RandomUserGenderOptionDao.Properties.Gender)
                        .list();
        ArrayList<String> genderOptionsStr = new ArrayList<>();
        for (RandomUserGenderOption genderOption: genderOptions) {
            genderOptionsStr.add(genderOption.getGender());
        }
        return genderOptionsStr;
    }

    public void setPictureChanged(boolean pictureChanged) {
        this.mPictureChanged = pictureChanged;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.mCurrentPhotoPath = currentPhotoPath;
    }
}
