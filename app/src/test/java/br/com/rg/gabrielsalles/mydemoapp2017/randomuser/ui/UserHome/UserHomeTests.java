package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.UserHome;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome.HomeInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHome.HomePresenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserHomeTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    HomeInterface view;
    HomePresenter presenter;


    @Before
    public void setUp() {
        presenter = new HomePresenter(view);
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
}
