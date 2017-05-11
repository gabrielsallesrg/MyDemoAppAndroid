package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.rg.gabrielsalles.mydemoapp2017.R;
import br.com.rg.gabrielsalles.mydemoapp2017.databinding.RandomUserActivityEditBinding;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.RandomUserDataBinder;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.models.RandomUser;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.HAS_NEW_DATA;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.RANDOM_USER;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.REQUEST_IMAGE_CAPTURE;

public class RandomUserEditActivity extends AppCompatActivity {
    private RandomUser mRandomUser;
    private RandomUserActivityEditBinding mBinding;
    private String mCurrentPhotoPath = "";
    private boolean mPictureChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.random_user_activity_edit);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mRandomUser = bundle.getParcelable(RANDOM_USER);
        mBinding.setRandomuser(mRandomUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mRandomUser.getNiceName());
        
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
        return mPictureChanged || !(
                mBinding.nameTitleET.        getText().toString().equals(mRandomUser.getName().getTitle())        &&
                mBinding.nameFirstET.        getText().toString().equals(mRandomUser.getName().getFirst())        &&
                mBinding.nameLastET.         getText().toString().equals(mRandomUser.getName().getLast())         &&
                mBinding.phoneHomeET.        getText().toString().equals(mRandomUser.getPhone())                  &&
                mBinding.phoneCellET.        getText().toString().equals(mRandomUser.getCell())                   &&
                mBinding.emailET.            getText().toString().equals(mRandomUser.getEmail())                  &&
                mBinding.genderET.           getText().toString().equals(mRandomUser.getGender())                 &&
                mBinding.locationStreetET.   getText().toString().equals(mRandomUser.getLocation().getStreet())   &&
                mBinding.locationCityET.     getText().toString().equals(mRandomUser.getLocation().getCity())     &&
                mBinding.locationStateET.    getText().toString().equals(mRandomUser.getLocation().getState())    &&
                mBinding.locationPostcodeET. getText().toString().equals(mRandomUser.getLocation().getPostcode()) &&
                mBinding.nationalityET.      getText().toString().equals(mRandomUser.getNat())                    );
    }

    private void updateRandomUser() {
        mRandomUser.getName().setTitle(mBinding.nameTitleET.getText().toString());
        mRandomUser.getName().setFirst(mBinding.nameFirstET.getText().toString());
        mRandomUser.getName().setLast(mBinding.nameLastET.getText().toString());
        mRandomUser.setPhone(mBinding.phoneHomeET.getText().toString());
        mRandomUser.setCell( mBinding.phoneCellET.getText().toString());
        mRandomUser.setEmail(mBinding.emailET.getText().toString());
        mRandomUser.setGender(mBinding.genderET.getText().toString());
        mRandomUser.getLocation().setStreet(mBinding.locationStreetET.getText().toString());
        mRandomUser.getLocation().setCity(mBinding.locationCityET.getText().toString());
        mRandomUser.getLocation().setState(mBinding.locationStateET.getText().toString());
        mRandomUser.getLocation().setPostcode(mBinding.locationPostcodeET.getText().toString());
        mRandomUser.setNat(mBinding.nationalityET.getText().toString());
        if (mPictureChanged)
            mRandomUser.getPicture().setLarge(mCurrentPhotoPath);
    }

    public void takeAPicture(View view) {
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
            Uri newImage = Uri.parse(mCurrentPhotoPath);

            RandomUserDataBinder.setImageUrlGlide(mBinding.thumbnail, mCurrentPhotoPath);
            mPictureChanged = true;
        }
    }

    private File createImageFile() throws IOException {
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
