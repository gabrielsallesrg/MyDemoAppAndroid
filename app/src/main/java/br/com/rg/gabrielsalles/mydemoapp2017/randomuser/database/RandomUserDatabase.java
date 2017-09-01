package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.database;

import android.app.Application;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.mydemoapp2017.App;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataIdDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOption;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserGenderOptionDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocationDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLoginDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserNameDao;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPictureDao;


public class RandomUserDatabase {

    private RandomUserPictureDao mRandomUserPictureDao;
    private RandomUserNameDao mRandomUserNameDao;
    private RandomUserLoginDao mRandomUserLoginDao;
    private RandomUserLocationDao mRandomUserLocationDao;
    private RandomUserDataIdDao mRandomUserDataIdDao;
    private RandomUserDao mRandomUserDao;
    private RandomUserGenderOptionDao mRandomUserGenderOptionDao;

    public RandomUserDatabase(Application application) {
        final DaoSession daoSession = ((App) application).getDaoSession();

        mRandomUserPictureDao = daoSession.getRandomUserPictureDao();
        mRandomUserNameDao = daoSession.getRandomUserNameDao();
        mRandomUserLoginDao = daoSession.getRandomUserLoginDao();
        mRandomUserLocationDao = daoSession.getRandomUserLocationDao();
        mRandomUserDataIdDao = daoSession.getRandomUserDataIdDao();
        mRandomUserDao = daoSession.getRandomUserDao();
        mRandomUserGenderOptionDao = daoSession.getRandomUserGenderOptionDao();
    }

    public ArrayList<RandomUser> getAllRandomUsers() {
        ArrayList<RandomUser> data = (ArrayList<RandomUser>) mRandomUserDao.queryBuilder().list();

        for (RandomUser user: data) {
            WhereCondition whereCondition = new WhereCondition.StringCondition("_id = " + user.getRu_id());
            user.setPicture(mRandomUserPictureDao.queryBuilder().where(whereCondition).unique());
            user.setName(mRandomUserNameDao.queryBuilder().where(whereCondition).unique());
            user.setLogin(mRandomUserLoginDao.queryBuilder().where(whereCondition).unique());
            user.setLocation(mRandomUserLocationDao.queryBuilder().where(whereCondition).unique());
            user.setDataId(mRandomUserDataIdDao.queryBuilder().where(whereCondition).unique());
        }
        return data;
    }

    public void saveGenders(ArrayList<RandomUserGenderOption> genderOptions) {
        for (RandomUserGenderOption genderOption: genderOptions) {
            mRandomUserGenderOptionDao.insertOrReplace(genderOption);
        }
    }

    public ArrayList<RandomUserGenderOption> getAllGenderOptions() {
        return  (ArrayList<RandomUserGenderOption>) mRandomUserGenderOptionDao.queryBuilder()
                        .orderAsc(RandomUserGenderOptionDao.Properties.Gender)
                        .list();
    }

    public ArrayList<String> getAllGenderOptionsInString() {
        ArrayList<RandomUserGenderOption> genderOptions = getAllGenderOptions();
        ArrayList<String> genderOptionsStr = new ArrayList<>();
        for (RandomUserGenderOption genderOption: genderOptions) {
            genderOptionsStr.add(genderOption.getGender());
        }
        return genderOptionsStr;
    }

    public void saveUser(RandomUser randomUser) {
        mRandomUserPictureDao.insertOrReplace(randomUser.getPicture());
        mRandomUserNameDao.insertOrReplace(randomUser.getName());
        mRandomUserLoginDao.insertOrReplace(randomUser.getLogin());
        mRandomUserLocationDao.insertOrReplace(randomUser.getLocation());
        mRandomUserDataIdDao.insertOrReplace(randomUser.getDataId());
        mRandomUserDao.insertOrReplace(randomUser);
    }

    public void deleteUser(RandomUser randomUser) {
        mRandomUserPictureDao.delete(randomUser.getPicture());
        mRandomUserNameDao.delete(randomUser.getName());
        mRandomUserLoginDao.delete(randomUser.getLogin());
        mRandomUserLocationDao.delete(randomUser.getLocation());
        mRandomUserDataIdDao.delete(randomUser.getDataId());
        mRandomUserDao.delete(randomUser);
        randomUser.deleteId();
    }
}
