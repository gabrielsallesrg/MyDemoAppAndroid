package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.UserDetail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail.UserDetailInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail.UserDetailPresenter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class UserDetailTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UserDetailInterface view;
    UserDetailPresenter presenter;

    @Before
    public void setUp() {
        presenter = new UserDetailPresenter(view);
        presenter.setRandomUser(new RandomUser());
    }

    @Test
    public void shouldSaveUserInDatabase() {
        presenter.getRandomUser().setFavorite(true);

        presenter.saveIfFavorite();

        verify(view).saveUserInDatabase(presenter.getRandomUser());
    }

    @Test
    public void shouldNotSaveUserInDatabase() {
        presenter.getRandomUser().setFavorite(false);

        presenter.saveIfFavorite();

        verify(view, never()).saveUserInDatabase(presenter.getRandomUser());
    }

    @Test
    public void shouldSaveUserInDatabaseWhenFavorite() {
        presenter.getRandomUser().setFavorite(true);

        presenter.saveOrDelete();

        verify(view).saveUserInDatabase(presenter.getRandomUser());
        verify(view, never()).deleteUserFromDatabase(presenter.getRandomUser());
    }

    @Test
    public void shouldNotSaveUserInDatabaseWhenNotFavorite() {
        presenter.getRandomUser().setFavorite(false);

        presenter.saveOrDelete();

        verify(view, never()).saveUserInDatabase(presenter.getRandomUser());
        verify(view).deleteUserFromDatabase(presenter.getRandomUser());
    }

    @Test
    public void shouldShowPhoneOptionsWithRightNumbers() {
        String phoneNumber = "123456";
        String cellNumber  = "654321";

        presenter.getRandomUser().setPhone(phoneNumber);
        presenter.getRandomUser().setCell(cellNumber);

        presenter.showPhoneNumbers();

        verify(view).showPhoneNumberView(phoneNumber, cellNumber);
    }
}
