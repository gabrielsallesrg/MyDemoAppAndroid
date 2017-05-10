package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityEditBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;

public class RandomUserEditActivity extends AppCompatActivity {
    private RandomUser mRandomUser;
    private RandomUserActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_edit);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random_user_edit_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //back
                confirmBackButton();
                return true;

            case R.id.save:
                //save
                prepareActivityResults();
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        confirmBackButton();
    }

    private void confirmBackButton() {
        if (changesWereMade()) {
            // Dialog box asking if keep editing or discard
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.discard_changes);
            builder.setPositiveButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            builder.setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        supportFinishAfterTransition();
                    }
                });
            builder.create().show();
        } else {
            supportFinishAfterTransition();
        }
    }

    private void prepareActivityResults() {
        Intent resultIntent = new Intent();
        if (changesWereMade()) {
            updateRandomUser();
            Bundle bundle = new Bundle();
            bundle.putParcelable(RANDOM_USER, mRandomUser);
            resultIntent.putExtras(bundle);
            resultIntent.putExtra(HAS_NEW_DATA, true);
        } else {
            resultIntent.putExtra(HAS_NEW_DATA, false);
        }
        setResult(RESULT_OK, resultIntent);
    }

    private boolean changesWereMade() {
        return !(
                binding.nameTitleET.        getText().toString().equals(mRandomUser.getName().getTitle())        &&
                binding.nameFirstET.        getText().toString().equals(mRandomUser.getName().getFirst())        &&
                binding.nameLastET.         getText().toString().equals(mRandomUser.getName().getLast())         &&
                binding.phoneHomeET.        getText().toString().equals(mRandomUser.getPhone())                  &&
                binding.phoneCellET.        getText().toString().equals(mRandomUser.getCell())                   &&
                binding.emailET.            getText().toString().equals(mRandomUser.getEmail())                  &&
                binding.genderET.           getText().toString().equals(mRandomUser.getGender())                 &&
                binding.locationStreetET.   getText().toString().equals(mRandomUser.getLocation().getStreet())   &&
                binding.locationCityET.     getText().toString().equals(mRandomUser.getLocation().getCity())     &&
                binding.locationStateET.    getText().toString().equals(mRandomUser.getLocation().getState())    &&
                binding.locationPostcodeET. getText().toString().equals(mRandomUser.getLocation().getPostcode()) &&
                binding.nationalityET.      getText().toString().equals(mRandomUser.getNat())                    );
    }

    private void updateRandomUser() {
        mRandomUser.getName().setTitle(binding.nameTitleET.getText().toString());
        mRandomUser.getName().setFirst(binding.nameFirstET.getText().toString());
        mRandomUser.getName().setLast(binding.nameLastET.getText().toString());
        mRandomUser.setPhone(binding.phoneHomeET.getText().toString());
        mRandomUser.setCell( binding.phoneCellET.getText().toString());
        mRandomUser.setEmail(binding.emailET.getText().toString());
        mRandomUser.setGender(binding.genderET.getText().toString());
        mRandomUser.getLocation().setStreet(binding.locationStreetET.getText().toString());
        mRandomUser.getLocation().setCity(binding.locationCityET.getText().toString());
        mRandomUser.getLocation().setState(binding.locationStateET.getText().toString());
        mRandomUser.getLocation().setPostcode(binding.locationPostcodeET.getText().toString());
        mRandomUser.setNat(binding.nationalityET.getText().toString());
    }
}
