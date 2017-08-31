package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog;

/**
 * Created by gabriel on 31/08/17.
 */

public interface ChoosePhoneInterface {
    void setHomeNumberView(String number);
    void setCellNumberView(String number);
    String getHomeNumber();
    String getCellNumber();
    void makePhoneCall(String number);
}
