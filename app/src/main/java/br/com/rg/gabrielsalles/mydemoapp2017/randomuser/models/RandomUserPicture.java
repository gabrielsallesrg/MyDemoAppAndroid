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
public class RandomUserPicture implements Parcelable {

    @Id
    private Long id;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public static final Creator<RandomUserPicture> CREATOR = new Creator<RandomUserPicture>() {

        public RandomUserPicture createFromParcel(Parcel in) {
            RandomUserPicture instance = new RandomUserPicture();
            instance.large = ((String) in.readValue((String.class.getClassLoader())));
            instance.medium = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public RandomUserPicture[] newArray(int size) {
            return (new RandomUserPicture[size]);
        }
    };

    @Generated(hash = 293845696)
    public RandomUserPicture(Long id, String large, String medium, String thumbnail) {
        this.id = id;
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    @Generated(hash = 156617564)
    public RandomUserPicture() {
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(large);
        dest.writeValue(medium);
        dest.writeValue(thumbnail);
        dest.writeValue(id);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int describeContents() {
        return 0;
    }
}
