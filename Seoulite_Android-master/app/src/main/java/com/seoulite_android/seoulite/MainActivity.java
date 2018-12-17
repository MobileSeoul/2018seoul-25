package com.seoulite_android.seoulite;


import android.content.res.Configuration;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements NavigationHost,
        DistrictSelectionFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    public static FragmentManager fragmentManager;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.app_bar) Toolbar mToolbar;

    ActionBarDrawerToggle mToggle;

    List<String> mDistrictList = DistrictEntry.getDistrictList();

    // Fragments
    private HomeFragment mHomeFragment;
    private DistrictSelectionFragment mDistrictSelectionFragment;
    private AgencyByDistrictFragment mAgencyByDistrictFragment;
    private LivingInfoFragment mLivingInfoFragment;
    private FavoritesFragment mFavoritesFragment;

    @BindView(R.id.search_view) MaterialSearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, new HomeFragment())
                    .commit();
        }
        setNavigationViewListener();

        setSearchView();


        // Initialize Fragments
        mDistrictSelectionFragment = new DistrictSelectionFragment();
        mAgencyByDistrictFragment = new AgencyByDistrictFragment();
        mLivingInfoFragment = new LivingInfoFragment();
        mFavoritesFragment = new FavoritesFragment();

        Log.d(TAG, "onCreate: ");

        //Start DB
        DbHelper dbHelper = new DbHelper(getApplicationContext());

    }



    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mSearchView.isSearchOpen()){
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_container);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem search = menu.findItem(R.id.app_bar_search);
        mSearchView.setMenuItem(search);

        return true;
    }



    // Pass the district name from DistrictSelectionFragment to AgencyByDistrictFragment
    @Override
    public void onFragmentInteraction(String districtName) {
        if (mAgencyByDistrictFragment != null) {
            Log.d(TAG, "onFragmentInteraction: mAgencyByDistrictFragment is not null");
            Bundle args = new Bundle();
            args.putString("districtName", districtName);
            mAgencyByDistrictFragment.setArguments(args);
            replaceFragment(mAgencyByDistrictFragment, true);

        } else {
            Log.d(TAG, "onFragmentInteraction: mAgencyBydistrictFragment is null");
        }
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                fragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fragment);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setSearchView() {
        mSearchView.setSuggestions((String[])mDistrictList.toArray());
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (query.charAt(0) >= 'a' && query.charAt(0) <= 'z') {
//                    query = Character.toUpperCase(query.charAt(0)) + query.substring(1);
//                }
//                if (query.length() < 4) {
//                    Toast.makeText(MainActivity.this, "Result not found. Please Try Again.", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (query.substring(query.length() - 2, query.length()).equals("gu")
//                        && (query.charAt(query.length()-3) != '-')) {
//                    query = query.substring(0, query.length() - 2) + "-gu";
//                }
//                if (!query.substring(query.length() - 3, query.length()).equals("-gu")) {
//                    query += "-gu";
//                }
                for (String district : mDistrictList) {
                    if (district.equals(query)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("districtName", query);
                        AgencyByDistrictFragment f = new AgencyByDistrictFragment();
                        f.setArguments(bundle);
                        replaceFragment(f, true);
                        return false;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("searching", query);
                Log.d("검색어:", query);
                SearchResultFragment resultFragment = new SearchResultFragment();
                resultFragment.setArguments(bundle);
                replaceFragment(resultFragment, true);
                //Toast.makeText(MainActivity.this, "Result not found. Please Try Again.", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                replaceFragment(new HomeFragment(), false);
                break;
            case R.id.nav_agency:
                replaceFragment(new DistrictSelectionFragment(), true);
                break;
            case R.id.nav_district_info:
                replaceFragment(new DistrictSelectionLivingInfoFragment(), true);
                break;
            case R.id.nav_favorites:
                replaceFragment(new FavoritesFragment(), true);
                break;
//            case R.id.nav_languages:
//                replaceFragment(new LanguageSettingFragment(), true);
//                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
