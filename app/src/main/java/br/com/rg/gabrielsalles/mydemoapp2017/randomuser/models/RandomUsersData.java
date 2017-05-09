package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by gabriel on 23/04/17.
 */

@Entity
public class RandomUsersData implements Parcelable {

    @SerializedName("results")
    @Expose
    @ToMany()
    private List<RandomUser> results = null;

    @SerializedName("info")
    @Expose
    @ToOne(joinProperty = "randomUserInfo")
    private RandomUserInfo info;
    public static final Creator<RandomUsersData> CREATOR = new Creator<RandomUsersData>() {
        @Override
        public RandomUsersData createFromParcel(Parcel in) {
            RandomUsersData instance = new RandomUsersData();
            in.readList(instance.results, (RandomUser.class.getClassLoader()));
            instance.info = ((RandomUserInfo) in.readValue((RandomUserInfo.class.getClassLoader())));
            return instance;
        }

        @Override
        public RandomUsersData[] newArray(int size) {
            return new RandomUsersData[size];
        }
    };

    public RandomUsersData() {
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
