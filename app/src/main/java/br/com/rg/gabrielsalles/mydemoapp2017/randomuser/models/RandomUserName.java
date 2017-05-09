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
public class RandomUserName implements Parcelable {

    @Id
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("first")
    @Expose
    private String first;

    @SerializedName("last")
    @Expose
    private String last;

    public static final Creator<RandomUserName> CREATOR = new Creator<RandomUserName>() {
        public RandomUserName createFromParcel(Parcel in) {
            RandomUserName instance = new RandomUserName();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.first = ((String) in.readValue((String.class.getClassLoader())));
            instance.last = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public RandomUserName[] newArray(int size) {
            return (new RandomUserName[size]);
        }
    };

    @Generated(hash = 1675400590)
    public RandomUserName(Long id, String title, String first, String last) {
        this.id = id;
        this.title = title;
        this.first = first;
        this.last = last;
    }

    @Generated(hash = 1885187841)
    public RandomUserName() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(first);
        dest.writeValue(last);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }
}
