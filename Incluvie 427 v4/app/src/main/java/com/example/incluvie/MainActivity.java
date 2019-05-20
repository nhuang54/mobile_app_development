package com.example.incluvie;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private final static int RC_SIGN_IN = 123;

    // Firebase auth fields
    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        if (mAuth.getCurrentUser() != null) {
            // user already signed in
            //Log.d("AUTH", mAuth.getCurrentUser().getEmail());
            Intent i = new Intent(getApplicationContext(), movieselection.class);
            startActivity(i);
        } else {
            // user not signed in
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(), RC_SIGN_IN);
                }
            }, 1500);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                // user logged in
                Log.d("AUTH", mAuth.getCurrentUser().getEmail());
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator iterator = dataSnapshot.getChildren().iterator();
                        String userEmail = mAuth.getCurrentUser().getEmail();

                        while (iterator.hasNext()) {
                            DataSnapshot dataUser = (DataSnapshot) iterator.next();
                            Log.e("email", dataUser.child("email").getValue().toString());
                            Log.e("email", userEmail);

                            if (dataUser.child("email").getValue().toString().equals(userEmail)) {
                                Log.e("email", "we came here1");
                                Intent i = new Intent(getApplicationContext(), movieselection.class);
                                startActivity(i);
                                Log.e("email", "we came here2");
                                finish();
                                return;
                            }
                        }
                        Log.e("email", "we came here3");
                        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                        startActivity(i);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

            }
            else {
                Log.d("AUTH", "NOT AUTHENTICATED");
            }
        }
    }
}
