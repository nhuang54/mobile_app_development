package com.example.incluvie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends Main2Activity {

    Button btnLogout;
    private GoogleApiClient mGoogleApiClient;
    LinearLayout fragmentContainer2;
    Button btnProfile;
    Button btnRated;
    userProfFragment profile;
    ratedMoviesFrag rated;
    FragmentManager fm;

    // firebase auth fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnLogout = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);
        btnRated = findViewById(R.id.btnRated);

        profile = new userProfFragment();
        rated = new ratedMoviesFrag();

        fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragmentContainer2, profile);//now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("profFrag");  //why do we do this?
        ft.commit ();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{

                }

            }
        };

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });

        btnRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRated();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fire base signout
                // facebook signout
                LoginManager.getInstance().logOut();
                // google signout
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
                mAuth.signOut();
                finish();
            }
        });

    }



    public void showProfile(){


        FragmentTransaction ft = fm.beginTransaction ();
        if (profile.isAdded()) { // if the fragment is already in container
            ft.show(profile);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragmentContainer2, profile, "profileFrag");
        }
        // Hide fragment C
        if (rated.isAdded()) {
            ft.hide(rated);
        }
        // Commit changes
        ft.commit();
    }

    public void showRated(){


        FragmentTransaction ft = fm.beginTransaction ();
        if (rated.isAdded()) { // if the fragment is already in container
            ft.show(rated);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragmentContainer2, rated, "ratedFrag");
        }
        // Hide fragment C
        if (profile.isAdded()) {
            ft.hide(profile);
        }
        // Commit changes
        ft.commit();
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
