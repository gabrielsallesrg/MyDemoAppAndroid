package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;



@Entity
public class RandomUserLocation implements Serializable {

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
    private final static long serialVersionUID = 1442976029917924539L;

    /**
     *
     * @param street
     * @param state
     * @param postcode
     * @param city
     */
    @Keep
    public RandomUserLocation(Long id, String street, String city, String state, String postcode) {
        super();
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    @Keep
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