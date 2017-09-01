package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog;


public interface ChoosePhoneInterface {
    void setHomeNumberView(String number);
    void setCellNumberView(String number);
    String getHomeNumber();
    String getCellNumber();
    void makePhoneCall(String number);
}
