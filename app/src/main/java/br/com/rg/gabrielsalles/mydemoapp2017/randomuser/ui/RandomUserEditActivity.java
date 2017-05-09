package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityEditBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;

public class RandomUserEditActivity extends AppCompatActivity {
    RandomUser mRandomUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        final RandomUserActivityEditBinding binding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_edit);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mRandomUser = bundle.getParcelable(RANDOM_USER);
        binding.setRandomuser(mRandomUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mRandomUser.getNiceName());
    }

}
