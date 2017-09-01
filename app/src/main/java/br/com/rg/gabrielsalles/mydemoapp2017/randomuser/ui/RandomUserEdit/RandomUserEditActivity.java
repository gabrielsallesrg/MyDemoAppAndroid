package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserEdit;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityEditBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.RandomUserImageDataBinder;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.database.RandomUserDatabase;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.REQUEST_IMAGE_CAPTURE;

public class RandomUserEditActivity extends AppCompatActivity implements UserEditInterface {

    private RandomUserActivityEditBinding mBinding;
    private UserEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        presenter = new UserEditPresenter(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_edit);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        presenter.setRandomUser((RandomUser)bundle.getSerializable(RANDOM_USER));
        mBinding.setRandomuser(presenter.getRandomUser());
        RandomUserDatabase database = new RandomUserDatabase(getApplication());
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, database.getAllGenderOptionsInString());
        mBinding.genderSpinner.setAdapter(genderAdapter);
        int currentGenderPosition = genderAdapter.getPosition(presenter.getRandomUser().getGender());
        mBinding.genderSpinner.setSelection(currentGenderPosition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(presenter.getRandomUser().getNiceName());
        
        mBinding.nameTitleET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateActionBarTitle();
                }
            }
        });
        
        mBinding.nameLastET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateActionBarTitle();
                }
            }
        });
    }

    private void updateActionBarTitle() {
        String newTitle = mBinding.nameTitleET.getText().toString() + " " + mBinding.nameLastET.getText().toString();
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(newTitle);
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

    @Override
    public void confirmBackButton() {
        if (changesWereMade()) {
            showConfirmBackDialog();
        } else {
            supportFinishAfterTransition();
        }
    }

    public void showConfirmBackDialog() {
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
    }

    private void prepareActivityResults() {
        Intent resultIntent = new Intent();
        if (changesWereMade()) {
            updateRandomUser();
            Bundle bundle = new Bundle();
            bundle.putSerializable(RANDOM_USER, presenter.getRandomUser());
            resultIntent.putExtras(bundle);
            resultIntent.putExtra(HAS_NEW_DATA, true);
        } else {
            resultIntent.putExtra(HAS_NEW_DATA, false);
        }
        setResult(RESULT_OK, resultIntent);
    }

    private boolean changesWereMade() {
        return presenter.changesWereMade(
                mBinding.nameTitleET.getText().toString(),           mBinding.nameFirstET.getText().toString(),
                mBinding.nameLastET.getText().toString(),            mBinding.phoneHomeET.getText().toString(),
                mBinding.phoneCellET.getText().toString(),           mBinding.emailET.getText().toString(),
                mBinding.genderSpinner.getSelectedItem().toString(), mBinding.locationStreetET.getText().toString(),
                mBinding.locationCityET.getText().toString(),        mBinding.locationStateET.getText().toString(),
                mBinding.locationPostcodeET.getText().toString(),    mBinding.nationalityET.getText().toString()
        );
    }

    private void updateRandomUser() {
        presenter.updateRandomUser(
                mBinding.nameTitleET.getText().toString(),           mBinding.nameFirstET.getText().toString(),
                mBinding.nameLastET.getText().toString(),            mBinding.phoneHomeET.getText().toString(),
                mBinding.phoneCellET.getText().toString(),           mBinding.emailET.getText().toString(),
                mBinding.genderSpinner.getSelectedItem().toString(), mBinding.locationStreetET.getText().toString(),
                mBinding.locationCityET.getText().toString(),        mBinding.locationStateET.getText().toString(),
                mBinding.locationPostcodeET.getText().toString(),    mBinding.nationalityET.getText().toString()
        );
    }

    public void takeAPicture(View view) {
        takeAPic();
    }

    @Override
    public void takeAPic() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.com.rg.gabrielsalles.mydemoapp2017.randomuser.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            RandomUserImageDataBinder.setImageUrlGlide(mBinding.thumbnail, presenter.getCurrentPhotoPath());
            presenter.setPictureChanged(true);
        }
    }

    @Override
    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        presenter.setCurrentPhotoPath(image.getAbsolutePath());
        return image;
    }
}
