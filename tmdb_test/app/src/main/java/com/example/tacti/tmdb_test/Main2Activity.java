package com.example.tacti.tmdb_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    // REFERENCE
    // https://medium.com/@janishar.ali/navigation-drawer-android-example-8dfe38c66f59
    // https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer
    // https://github.com/dipenptl1/Navigation-Drawer-With-Multiple-Activity/blob/master/NavigationDrawerActivity/src/com/navigation/drawer/activity/BaseActivity.java

    private DrawerLayout mDrawerLayout;
    private TextView txtActivity;
    private NavigationView navigationView;

    @Override
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

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
                        Intent j = new Intent(getApplicationContext(), Main3Activity.class);
                        startActivity(j);
                        return true;
                    default:
                        txtActivity.setText("nothing");
                        return true;
                }
                // good to use fragments here for seamless drawer animation
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
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
}
