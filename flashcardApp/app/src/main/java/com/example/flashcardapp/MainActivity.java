package com.example.flashcardapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    // firebase auth fields
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseReference;


    // Buttons login/signup variables
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind all variables to their respective view ids
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        // get a firebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // connect to the db and specific child "Users"
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        // mAuth listener to check if a user is logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // user signed in
                    final String email = user.getEmail();
                    // one time listener to read a snapshot of the values in the DB
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // call function to check email is in DB
                            checkUserValidation(dataSnapshot, email);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
        // onclick for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the email and password from the EditText fields and parse it
                final String userEmail, userPass;
                userEmail = edtEmail.getText().toString().trim();
                userPass = edtPass.getText().toString().trim();

                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)){ // check to see if either is empty
                    // built in mAuth function to sign in using email and password
                    mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // once logged in successful, add a one time listener to read values from DB
                                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // call function to verify email
                                        checkUserValidation(dataSnapshot, userEmail);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else{
                                // login unsuccessful
                                Toast.makeText(MainActivity.this, "Incorrect Email/Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        // onclick for sign up button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the email and password from the EditText fields and parse it
                final String userEmail, userPass;
                userEmail = edtEmail.getText().toString().trim();
                userPass = edtPass.getText().toString().trim();
                // same thing as login but this time call built in function to create user with that email and password
                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)){
                    mAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // if successful in creating account create a child reference of current DB
                                DatabaseReference mChildDatabase = mDatabaseReference.push();
                                final String key_user = mChildDatabase.getKey(); // get its key

                                // store the key and email into the DB for future reference
                                mChildDatabase.child("userKey").setValue(key_user);
                                mChildDatabase.child("userEmail").setValue(userEmail);

                                Toast.makeText(MainActivity.this, "User Account Created", Toast.LENGTH_SHORT).show();
                                // move to flash card activity and send the user_key for easy reference into the DB
                                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                        i.putExtra("USER_KEY", key_user);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                // Failed to create account
                                Toast.makeText(MainActivity.this, "Failed to Create User Account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    // Function for validating email along with finding the key to the specific email and send it
    // as a bundle to next activity
    private void checkUserValidation(DataSnapshot dataSnapshot, String emailForVer){
        // Iterator to iterate over the children of the snapshot of the DB.
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            DataSnapshot dataUser = (DataSnapshot)iterator.next();
            // check each "userEmail" in the DB to see if it matches the login user's email
            if (dataUser.child("userEmail").getValue().toString().equals(emailForVer)) {
                // if it does, move to Flash card activity and pass along the userKey
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("USER_KEY", dataUser.child("userKey").getValue().toString());
                startActivity(i);
            }
        }
    }
    // add and remove the authstatelistener during onStart and onStop
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
