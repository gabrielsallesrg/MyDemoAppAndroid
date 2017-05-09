package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import br.com.rg.gabrielsalles.mydemoapp2017.R;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HOME_NUMBER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.MOBILE_NUMBER;

public class RandomUserChoosePhoneDialog extends AppCompatActivity {

    private String homeNumber = "";
    private String cellNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_user_activity_choose_phone);
        ConstraintLayout callHomeLayout   = (ConstraintLayout) findViewById(R.id.callHomeLayout);
        ConstraintLayout callMobileLayout = (ConstraintLayout) findViewById(R.id.callMobileLayout);
        TextView homeNumTextView = (TextView) findViewById(R.id.homeNumView);
        TextView cellNumTextView = (TextView) findViewById(R.id.mobileNumView);
        homeNumber = getIntent().getStringExtra(HOME_NUMBER);
        cellNumber = getIntent().getStringExtra(MOBILE_NUMBER);
        homeNumTextView.setText(homeNumber);
        cellNumTextView.setText(cellNumber);

        callHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCallIntent(homeNumber);
            }
        });

        callMobileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCallIntent(cellNumber);
            }
        });
    }

    private void phoneCallIntent(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }

}
