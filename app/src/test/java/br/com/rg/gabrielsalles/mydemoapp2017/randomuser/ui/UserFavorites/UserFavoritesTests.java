package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.UserFavorites;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites.FavoritesInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFavorites.FavoritesPresenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserFavoritesTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    FavoritesInterface view;
    FavoritesPresenter presenter;


    @Before
    public void setUp() {
        presenter = new FavoritesPresenter(view);
    }

    @Test
    public void shouldCallDataAddedInPositionMethodWithZero() {
            when(view.getCurrentViewData()).thenReturn(new ArrayList<RandomUser>());

            presenter.addLoading();

            verify(view).dataAddedInPosition(0);
    }

    @Test
    public void shouldCallDataAddedInPositionMethodWithOne() {
        when(view.getCurrentViewData()).thenReturn(
                new ArrayList<>(Arrays.asList(new RandomUser())));

        presenter.addLoading();

        verify(view).dataAddedInPosition(1);
    }

    @Test
    public void shouldCallDataAddedInPositionMethodWithThree() {
        when(view.getCurrentViewData()).thenReturn(
                new ArrayList<>(
                        Arrays.asList(
                        new RandomUser(),
                        new RandomUser(),
                        new RandomUser()
                        )
                )
        );

        presenter.addLoading();

        verify(view).dataAddedInPosition(3);
    }

    @Test
    public void shouldRemoveLoadingAndAddNewData(){
        presenter.setData(new ArrayList<>(
                Arrays.asList(
                        new RandomUser(),
                        new RandomUser(),
                        null // Loading
                )
        ));

        when(view.getAllRandomUsersFromDatabase()).thenReturn(new ArrayList<>(
                Arrays.asList(
                        new RandomUser(),
                        new RandomUser(),
                        new RandomUser()
                )
        ));

        presenter.requestData();

        verify(view).dataRemovedFromPosition(2);
        verify(view).dataAddedInRange(2, 5);
    }
}
