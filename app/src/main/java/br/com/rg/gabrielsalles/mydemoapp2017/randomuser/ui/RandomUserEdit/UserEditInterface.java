package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit;

import java.io.File;
import java.io.IOException;

/**
 * Created by gabriel on 31/08/17.
 */

public interface UserEditInterface {

    void confirmBackButton();
    void takeAPic();
    File createImageFile() throws IOException;
}
