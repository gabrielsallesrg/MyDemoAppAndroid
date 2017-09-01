package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabriel on 23/04/17.
 */

@Entity
public class RandomUsersData implements Serializable {

    @SerializedName("results")
    @Expose
    @ToMany()
    private List<RandomUser> results = null;

    @SerializedName("info")
    @Expose
    @ToOne(joinProperty = "randomUserInfo")
    private RandomUserInfo info;

    private final static long serialVersionUID = 14539319122754602L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RandomUsersData() {
    }

    /**
     *
     * @param results
     * @param info
     */
    public RandomUsersData(List<RandomUser> results, RandomUserInfo info) {
        super();
        this.results = results;
        this.info = info;
    }

    public List<RandomUser> getResults() {
        return results;
    }

    public void setResults(List<RandomUser> results) {
        this.results = results;
    }

    public RandomUserInfo getInfo() {
        return info;
    }

    public void setInfo(RandomUserInfo info) {
        this.info = info;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
        dest.writeValue(info);
    }

    public int describeContents() {
        return 0;
    }
}
