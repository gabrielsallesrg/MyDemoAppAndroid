package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog;



public class ChoosePhonePresenter {

    private ChoosePhoneInterface view;
    private String homeNumber = "";
    private String cellNumber = "";

    public ChoosePhonePresenter(ChoosePhoneInterface view) {
        this.view = view;
        setNumbers();
    }

    public void setNumbers() {
        homeNumber = view.getHomeNumber();
        cellNumber = view.getCellNumber();
        view.setHomeNumberView(homeNumber);
        view.setCellNumberView(cellNumber);
    }

    public void callHome() {
        view.makePhoneCall(homeNumber);
    }

    public void callCell() {
        view.makePhoneCall(cellNumber);
    }
}
