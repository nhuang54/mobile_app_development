package com.example.incluvie;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
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

        // get instance of firebase Auth and firebase Database
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        // check to see if user is logged in already
        if (mAuth.getCurrentUser() != null) {
            // user already signed in
            //Log.d("AUTH", mAuth.getCurrentUser().getEmail());
            Intent i = new Intent(getApplicationContext(), movieselection.class);
            startActivity(i);
        } else {
            // user not signed in
            // handler for delaying the login screen with FirebaseUI
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
                            .setLogo(R.mipmap.background)
                            .build(), RC_SIGN_IN);
                }
            }, 1500);
        }
    }
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                // user logged in
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator iterator = dataSnapshot.getChildren().iterator();
                        String userEmail = mAuth.getCurrentUser().getEmail();

                        while (iterator.hasNext()) {
                            DataSnapshot dataUser = (DataSnapshot) iterator.next();

                            if (dataUser.child("email").getValue().toString().equals(userEmail)) {
                                Intent i = new Intent(getApplicationContext(), movieselection.class);
                                startActivity(i);
                                finish();
                                return;
                            }
                        }
                        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                        startActivity(i);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            } else {
                // sign in failed, handle all cases
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Sign in Cancelled", Toast.LENGTH_SHORT).show();;
                    finish();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();;
                    finish();
                    return;
                }
                Toast.makeText(this, "Unknown Error, please try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
