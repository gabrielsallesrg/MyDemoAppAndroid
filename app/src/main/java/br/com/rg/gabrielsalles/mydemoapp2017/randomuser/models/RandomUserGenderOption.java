package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;



@Entity
public class RandomUserGenderOption {

    @Id
    private Long id;

    @Unique
    private String gender;

    @Generated(hash = 891702784)
    public RandomUserGenderOption(Long id, String gender) {
        this.id = id;
        this.gender = gender;
    }

    @Generated(hash = 1005223865)
    public RandomUserGenderOption() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
