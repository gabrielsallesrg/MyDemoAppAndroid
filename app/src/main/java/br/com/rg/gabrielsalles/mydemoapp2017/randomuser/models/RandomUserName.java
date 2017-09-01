package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;



@Entity
public class RandomUserName implements Serializable {

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

    private final static long serialVersionUID = -3671783955901145710L;


    /**
     *
     * @param title
     * @param last
     * @param first
     */
    @Keep
    public RandomUserName(Long id, String title, String first, String last) {
        super();
        this.id = id;
        this.title = title;
        this.first = first;
        this.last = last;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    @Keep
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
