package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;



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
