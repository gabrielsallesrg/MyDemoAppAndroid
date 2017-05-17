package br.com.rg.gabrielsalles.mydemoapp2017;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoMaster;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoMaster.DevOpenHelper;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.DaoSession;

/**
 * Created by gabriel on 23/04/17.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

//        Stetho.initializeWithDefaults(this);

        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "mydemoapp-db-encrypted" : "mydemoapp-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
