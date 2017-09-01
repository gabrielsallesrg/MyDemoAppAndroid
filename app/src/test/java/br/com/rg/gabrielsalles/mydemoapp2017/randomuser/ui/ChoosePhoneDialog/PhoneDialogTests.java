package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.ChoosePhoneDialog;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog.ChoosePhoneInterface;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog.ChoosePhonePresenter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PhoneDialogTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ChoosePhoneInterface view;
    ChoosePhonePresenter presenter;
    String homeNumber = "12345678";
    String cellNumber = "87654321";

    @Before
    public void setUp() {
        presenter = new ChoosePhonePresenter(view);
    }

    @Test
    public void shouldSetHomeNumbersInScreen() {
        when(view.getHomeNumber()).thenReturn(homeNumber);
        when(view.getCellNumber()).thenReturn(cellNumber);

        presenter.setNumbers();

        verify(view).setHomeNumberView(homeNumber);
        verify(view).setCellNumberView(cellNumber);
    }

    @Test
    public void shouldCallGetPhoneNumbersMethodWithHomeNumber() {
        presenter.callHome();

        verify(view).makePhoneCall(anyString());
    }

    @Test
    public void shouldCallGetCellNumbersMethodWithCellNumber() {
        presenter.callCell();

        verify(view).makePhoneCall(anyString());
    }
}























