package com.example.incluvie;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    // REFERENCE
    // https://medium.com/@janishar.ali/navigation-drawer-android-example-8dfe38c66f59
    // https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer
    // https://github.com/dipenptl1/Navigation-Drawer-With-Multiple-Activity/blob/master/NavigationDrawerActivity/src/com/navigation/drawer/activity/BaseActivity.java

    private DrawerLayout mDrawerLayout;
    private TextView txtActivity;
    private NavigationView navigationView;
    private TextView displayName;
    private TextView displayEmail;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }






    protected void onCreateDrawer(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        txtActivity = findViewById(R.id.txtActivity);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View navView = navigationView.getHeaderView(0);
//reference to views
        displayName = navView.findViewById(R.id.txtProfileName);
        displayEmail = navView.findViewById(R.id.txtProfileEmail);

//set views

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }

        };

        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        displayName.setText(user.getDisplayName());
        displayEmail.setText(email);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        TextView txtProfileName = findViewById(R.id.txtProfileName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.avengers_1:
                        Intent h = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(h);
                        return true;
                    case R.id.avengers_2:
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                        return true;
                    case R.id.avengers_3:
                        Intent j = new Intent(getApplicationContext(), movieselection.class);
                        startActivity(j);
                        return true;
                    case R.id.avengers_4:
                        Intent k = new Intent(getApplicationContext(), Welcome.class);
                        startActivity(k);
                        return true;
                    default:
                        txtActivity.setText("nothing");
                        return true;
                }
                // good to use fragments here for seamless drawer animation
            }
        });
    }

      public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

    //    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("Title", query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Intent i = new Intent(this.getApplicationContext(), Welcome.class );
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}



