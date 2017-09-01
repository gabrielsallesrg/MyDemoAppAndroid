package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.UserDetail;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail.UserDetailInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserDetail.UserDetailPresenter;



public class UserDetailTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UserDetailInterface view;
    UserDetailPresenter presenter;

    @Before
    public void setUp() {
        presenter = new UserDetailPresenter(view);
    }
}
