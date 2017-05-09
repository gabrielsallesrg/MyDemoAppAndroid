package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by gabriel on 23/04/17.
 */

@Entity
public class RandomUserDataId implements Parcelable {

    @Id
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;
    public static final Creator<RandomUserDataId> CREATOR = new Creator<RandomUserDataId>() {
        public RandomUserDataId createFromParcel(Parcel in) {
            RandomUserDataId instance = new RandomUserDataId();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.value = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public RandomUserDataId[] newArray(int size) {
            return (new RandomUserDataId[size]);
        }

    };


    @Generated(hash = 184172047)
    public RandomUserDataId(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Generated(hash = 601754519)
    public RandomUserDataId() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(value);
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