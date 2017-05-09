package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gabriel on 23/04/17.
 */

@Entity
public class RandomUserInfo implements Parcelable {

    @SerializedName("seed")
    @Expose
    private String seed;
    @SerializedName("results")
    @Expose
    private int results;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("version")
    @Expose
    private String version;
    public static final Creator<RandomUserInfo> CREATOR = new Creator<RandomUserInfo>() {
        public RandomUserInfo createFromParcel(Parcel in) {
            RandomUserInfo instance = new RandomUserInfo();
            instance.seed = ((String) in.readValue((String.class.getClassLoader())));
            instance.results = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.version = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public RandomUserInfo[] newArray(int size) {
            return (new RandomUserInfo[size]);
        }
    };

    @Generated(hash = 580202255)
    public RandomUserInfo(String seed, int results, int page, String version) {
        this.seed = seed;
        this.results = results;
        this.page = page;
        this.version = version;
    }

    @Generated(hash = 2116153070)
    public RandomUserInfo() {
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(seed);
        dest.writeValue(results);
        dest.writeValue(page);
        dest.writeValue(version);
    }

    public int describeContents() {
        return 0;
    }
}
