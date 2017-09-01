package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;



@Entity
public class RandomUser implements Serializable {

    @Id
    private Long ru_id;
    
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("name")
    @Expose
    @ToOne
    private RandomUserName name;

    @SerializedName("location")
    @Expose
    @ToOne()
    private RandomUserLocation location;

    @SerializedName("email")
    @Expose
    @Unique
    private String email;

    @SerializedName("login")
    @Expose
    @ToOne()
    private RandomUserLogin login;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("registered")
    @Expose
    private String registered;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("cell")
    @Expose
    private String cell;

    @SerializedName("id")
    @Expose
    @ToOne()
    private RandomUserDataId dataId;

    @SerializedName("picture")
    @Expose
    @ToOne()
    private RandomUserPicture picture;

    @SerializedName("nat")
    @Expose
    private String nat;

    private final static long serialVersionUID = 6858489505521393699L;

    private boolean favorite = false;

    /**
     * No args constructor for use in serialization
     *
     */
    public RandomUser() {
    }

    /**
     *
     * @param picture
     * @param id
     * @param phone
     * @param email
     * @param location
     * @param registered
     * @param cell
     * @param dob
     * @param name
     * @param gender
     * @param nat
     * @param login
     */
    @Keep
    public RandomUser(String gender, RandomUserName name, RandomUserLocation location, String email,
                      RandomUserLogin login, String dob, String registered, String phone, String cell,
                      RandomUserDataId id, RandomUserPicture picture, String nat) {
        super();
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.email = email;
        this.login = login;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.dataId = id;
        this.picture = picture;
        this.nat = nat;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1381933636)
    private transient RandomUserDao myDao;

    @Generated(hash = 1101639891)
    private transient boolean name__refreshed;

    @Generated(hash = 1732148129)
    private transient boolean location__refreshed;

    @Generated(hash = 2139356678)
    private transient boolean login__refreshed;

    @Generated(hash = 572682641)
    private transient boolean picture__refreshed;

    @Generated(hash = 1254489188)
    private transient boolean dataId__refreshed;

    /**
     *
     * @param phone
     * @param email
     * @param registered
     * @param cell
     * @param dob
     * @param gender
     * @param nat
     */
    @Keep
    public RandomUser(Long ru_id, String gender, String email, String dob, String registered, String phone, String cell, String nat,
            boolean favorite) {
        super();
        this.ru_id = ru_id;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.nat = nat;
        this.favorite = favorite;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNiceName() {
        return name.getTitle() + " " + name.getLast();
    }

    public String getFullName() {
        return name.getTitle() + " " + name.getFirst() + " " + name.getLast();
    }

    public String getFullAddress() {
        return location.getStreet() + "\n" + location.getCity() + " / " + location.getState() + " / " + nat + "\n" + location.getPostcode();
    }

    public String getAddressForMaps() {
        return location.getCity() + "," + nat;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(gender);
        dest.writeValue(name);
        dest.writeValue(location);
        dest.writeValue(email);
        dest.writeValue(login);
        dest.writeValue(dob);
        dest.writeValue(registered);
        dest.writeValue(phone);
        dest.writeValue(cell);
        dest.writeValue(dataId);
        dest.writeValue(picture);
        dest.writeValue(nat);
        dest.writeValue(favorite);
        dest.writeValue(ru_id);
    }

    public int describeContents() {
        return 0;
    }

    public boolean getFavorite() {
        return this.favorite;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 1656031368)
    public RandomUserName peakName() {
        return name;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 2028043018)
    public RandomUserLocation peakLocation() {
        return location;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 1545006540)
    public RandomUserLogin peakLogin() {
        return login;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 1966166136)
    public RandomUserPicture peakPicture() {
        return picture;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public RandomUserPicture getPicture() {
        if (picture != null) {
            return picture;
        }
        if (!picture__refreshed) {
            if (daoSession == null) {
                return picture;
            }
            RandomUserPictureDao targetDao = daoSession.getRandomUserPictureDao();
            targetDao.refresh(picture);
            picture__refreshed = true;
        }
        return picture;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1924730055)
    public void setPicture(RandomUserPicture picture) {
        synchronized (this) {
            this.picture = picture;
            picture__refreshed = true;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public RandomUserName getName() {
        if (name != null) {
            return name;
        }
        if (!name__refreshed) {
            if (daoSession == null) {
                return name;
            }
            RandomUserNameDao targetDao = daoSession.getRandomUserNameDao();
            targetDao.refresh(name);
            name__refreshed = true;
        }
        return name;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1629596786)
    public void setName(RandomUserName name) {
        synchronized (this) {
            this.name = name;
            name__refreshed = true;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public RandomUserLocation getLocation() {
        if (location != null) {
            return location;
        }
        if (!location__refreshed) {
            if (daoSession == null) {
                return location;
            }
            RandomUserLocationDao targetDao = daoSession.getRandomUserLocationDao();
            targetDao.refresh(location);
            location__refreshed = true;
        }
        return location;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 93607280)
    public void setLocation(RandomUserLocation location) {
        synchronized (this) {
            this.location = location;
            location__refreshed = true;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public RandomUserLogin getLogin() {
        if (login != null) {
            return login;
        }
        if (!login__refreshed) {
            if (daoSession == null) {
                return login;
            }
            RandomUserLoginDao targetDao = daoSession.getRandomUserLoginDao();
            targetDao.refresh(login);
            login__refreshed = true;
        }
        return login;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 824686715)
    public void setLogin(RandomUserLogin login) {
        synchronized (this) {
            this.login = login;
            login__refreshed = true;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public RandomUserDataId getDataId() {
        if (dataId != null) {
            return dataId;
        }
        if (!dataId__refreshed) {
            if (daoSession == null) {
                return dataId;
            }
            RandomUserDataIdDao targetDao = daoSession.getRandomUserDataIdDao();
            targetDao.refresh(dataId);
            dataId__refreshed = true;
        }
        return dataId;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 809571553)
    public RandomUserDataId peakDataId() {
        return dataId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1748551008)
    public void setDataId(RandomUserDataId dataId) {
        synchronized (this) {
            this.dataId = dataId;
            dataId__refreshed = true;
        }
    }

    public Long getRu_id() {
        return this.ru_id;
    }

    public void setRu_id(Long ru_id) {
        this.ru_id = ru_id;
    }

    public void deleteId(){
        this.ru_id = null;
        this.getPicture().setId(null);
        this.getName().setId(null);
        this.getLogin().setId(null);
        this.getLocation().setId(null);
        this.getDataId().setId(null);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1640775040)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRandomUserDao() : null;
    }

}
