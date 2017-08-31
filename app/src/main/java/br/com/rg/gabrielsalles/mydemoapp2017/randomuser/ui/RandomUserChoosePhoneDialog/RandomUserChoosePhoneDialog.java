package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserChoosePhoneDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.rg.gabrielsalles.mydemoapp2017.R;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HOME_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.MOBILE_NUMBER;

public class RandomUserChoosePhoneDialog extends AppCompatActivity implements ChoosePhoneInterface {

    ChoosePhonePresenter presenter;
    TextView mHomeNumTextView;
    TextView mCellNumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_user_activity_choose_phone);
        ConstraintLayout callHomeLayout   = (ConstraintLayout) findViewById(R.id.callHomeLayout);
        ConstraintLayout callMobileLayout = (ConstraintLayout) findViewById(R.id.callMobileLayout);
        mHomeNumTextView = (TextView) findViewById(R.id.homeNumView);
        mCellNumTextView = (TextView) findViewById(R.id.mobileNumView);
        presenter = new ChoosePhonePresenter(this);

        callHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callHome();
            }
        });

        callMobileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callCell();
            }
        });
    }

    @Override
    public void setHomeNumberView(String number) {
        mHomeNumTextView.setText(number);
    }

    @Override
    public void setCellNumberView(String number) {
        mCellNumTextView.setText(number);
    }

    @Override
    public String getHomeNumber() {
        return getIntent().getStringExtra(HOME_NUMBER);
    }

    @Override
    public String getCellNumber() {
        return getIntent().getStringExtra(MOBILE_NUMBER);
    }

    @Override
    public void makePhoneCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
}
