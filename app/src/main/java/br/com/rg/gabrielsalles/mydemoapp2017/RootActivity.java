package br.com.rg.gabrielsalles.mydemoapp2017;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFragmentAbout;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserFragmentFavorites;
import br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui.RandomUserHomeFragment;

import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.CURRENT_FRAGMENT;
import static br.com.rg.gabrielsalles.mydemoapp2017.helperclasses.Constants.CURRENT_TITLE;


public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mViewIsAtHome;
    private Fragment mCurrentFragment = null;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, CURRENT_FRAGMENT);
            getSupportActionBar().setTitle(savedInstanceState.getString(CURRENT_TITLE, ""));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        getSupportFragmentManager().putFragment(savedInstanceState, CURRENT_FRAGMENT, mCurrentFragment);
        savedInstanceState.putString(CURRENT_TITLE, getSupportActionBar().getTitle().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        if (!mViewIsAtHome) { //if the current view is not the News fragment
            displayView(R.id.nav_randomuser); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {
        String title = getString(R.string.app_name);
        boolean skip = false;
        switch (viewId) {
            case R.id.nav_randomuser:
                if (mCurrentFragment instanceof RandomUserHomeFragment) {
                    skip = true;
                }
                mCurrentFragment = new RandomUserHomeFragment();
                title  = getResources().getString(R.string.randomuser);
                mViewIsAtHome = true;
                break;

            case R.id.nav_randomuser_favorites:
                if (mCurrentFragment instanceof RandomUserFragmentFavorites) {
                    skip = true;
                }
                mCurrentFragment = new RandomUserFragmentFavorites();
                title  = getResources().getString(R.string.randomuser_favorites);
                break;

            case R.id.nav_randomuser_about:
                if (mCurrentFragment instanceof RandomUserFragmentAbout) {
                    skip = true;
                }
                mCurrentFragment = new RandomUserFragmentAbout();
                title = getResources().getString(R.string.randomuser_about);
                break;

            case R.id.nav_contact_info:
                if (mCurrentFragment instanceof ContactInformationFragment) {
                    skip = true;
                }
                mCurrentFragment = new ContactInformationFragment();
                title = getResources().getString(R.string.contact_info);
                break;
        }

        if (!skip) {
            if (mCurrentFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, mCurrentFragment);
                ft.commit();
            }
            // set the toolbar title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
        mDrawer.closeDrawer(GravityCompat.START);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawer;
    }
}
