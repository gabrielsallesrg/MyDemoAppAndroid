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
public class RandomUserLocation implements Parcelable {

    @Id
    private Long id;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    public static final Creator<RandomUserLocation> CREATOR = new Creator<RandomUserLocation>() {
        public RandomUserLocation createFromParcel(Parcel in) {
            RandomUserLocation instance = new RandomUserLocation();
            instance.street = ((String) in.readValue((String.class.getClassLoader())));
            instance.city = ((String) in.readValue((String.class.getClassLoader())));
            instance.state = ((String) in.readValue((String.class.getClassLoader())));
            instance.postcode = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            return instance;
        }

        public RandomUserLocation[] newArray(int size) {
            return (new RandomUserLocation[size]);
        }
    };

    @Generated(hash = 1279659819)
    public RandomUserLocation(Long id, String street, String city, String state, String postcode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    @Generated(hash = 1429131324)
    public RandomUserLocation() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(street);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(postcode);
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