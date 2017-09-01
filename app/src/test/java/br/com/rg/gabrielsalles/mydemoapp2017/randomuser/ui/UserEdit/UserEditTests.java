package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.UserEdit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserDataId;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLocation;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserLogin;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserName;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUserPicture;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit.UserEditInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit.UserEditPresenter;

import static org.junit.Assert.assertEquals;


public class UserEditTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UserEditInterface view;
    UserEditPresenter presenter;
    String title = "Mr.";
    String firstName = "Gabriel";
    String lastName = "Guedes";
    String phone = "123456";
    String cell = "654321";
    String email = "gabriesalles.rg@gmail.com";
    String gender = "M";
    String street = "Street 1";
    String city = "City 1";
    String state = "State 1";
    String postcode = "321654";
    String nat = "BR";
    String dob = "dob";
    String registred = "registred";
    Long id = Long.valueOf("1");

    RandomUserName RUName = new RandomUserName(id, title, firstName, lastName);
    RandomUserLocation RULocation = new RandomUserLocation(id, street, city, state, postcode);
    RandomUserLogin RULogin = new RandomUserLogin();
    RandomUserDataId RUDataId = new RandomUserDataId();
    RandomUserPicture RUPicture = new RandomUserPicture();

    @Before
    public void setUp() {
        presenter = new UserEditPresenter(view);
        presenter.setRandomUser(new RandomUser(gender, RUName, RULocation, email,
                RULogin, dob, registred, phone, cell, RUDataId, RUPicture, nat));
    }

    @Test
    public void shouldReturnFalseForChangesMade() {
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, city,
                state, postcode, nat), false);
    }

    @Test
    public void shouldReturnTrueForChangesMade() {
        assertEquals(presenter.changesWereMade("burica", firstName, lastName, phone,
                cell, email, gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, "burica", lastName, phone,
                cell, email, gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, "burica", phone,
                cell, email, gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, "burica",
                cell, email, gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                "burica", email, gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, "burica", gender, street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, "burica", street, city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, "burica", city,
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, "burica",
                state, postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, city,
                "burica", postcode, nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, city,
                state, "burica", nat), true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, city,
                state, postcode, "burica"), true);
        presenter.setPictureChanged(true);
        assertEquals(presenter.changesWereMade(title, firstName, lastName, phone,
                cell, email, gender, street, city,
                state, postcode, nat), true);
    }
}
