package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gabriel on 23/04/17.
 */

@Entity
public class RandomUserLogin implements Parcelable {

    @Id
    private Long id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("sha256")
    @Expose
    private String sha256;
    public static final Creator<RandomUserLogin> CREATOR = new Creator<RandomUserLogin>() {

        public RandomUserLogin createFromParcel(Parcel in) {
            RandomUserLogin instance = new RandomUserLogin();
            instance.username = ((String) in.readValue((String.class.getClassLoader())));
            instance.password = ((String) in.readValue((String.class.getClassLoader())));
            instance.salt = ((String) in.readValue((String.class.getClassLoader())));
            instance.md5 = ((String) in.readValue((String.class.getClassLoader())));
            instance.sha1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.sha256 = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public RandomUserLogin[] newArray(int size) {
            return (new RandomUserLogin[size]);
        }
    };

    @Generated(hash = 1916180083)
    public RandomUserLogin(Long id, String username, String password, String salt, String md5,
            String sha1, String sha256) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.md5 = md5;
        this.sha1 = sha1;
        this.sha256 = sha256;
    }

    @Generated(hash = 801948537)
    public RandomUserLogin() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(username);
        dest.writeValue(password);
        dest.writeValue(salt);
        dest.writeValue(md5);
        dest.writeValue(sha1);
        dest.writeValue(sha256);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}