package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit;

import java.io.File;
import java.io.IOException;



public interface UserEditInterface {

    void confirmBackButton();
    void takeAPic();
    File createImageFile() throws IOException;
}
