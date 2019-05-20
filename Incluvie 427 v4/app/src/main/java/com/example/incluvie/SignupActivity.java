package com.example.incluvie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nathanwu on 4/24/18.
 */

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    FirebaseAuth.AuthStateListener mAuthListener;

    TextView welcome;
    EditText editBday;
    EditText editEthnicity;
    EditText editGender;
    Button btnProf;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editBday = findViewById(R.id.editBday);
        editEthnicity = findViewById(R.id.editEthnicity);
        editGender = findViewById(R.id.editGender);
        welcome = findViewById(R.id.welcome);
        btnProf = findViewById(R.id.btnProf);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        };

        welcome.setText("Welcome " + mAuth.getCurrentUser().getDisplayName()+"!");



        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bday, gender, ethnicity;

                bday = editBday.getText().toString().trim();
                gender = editGender.getText().toString().trim();
                ethnicity = editEthnicity.getText().toString().trim();

                if(bday.isEmpty() || gender.isEmpty() || ethnicity.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference mChild = mDatabaseReference.push();

                    mChild.child("email").setValue(mAuth.getCurrentUser().getEmail().toString());
                    mChild.child("name").setValue(mAuth.getCurrentUser().getDisplayName().toString());
                    mChild.child("birthday").setValue(bday);
                    mChild.child("ethnicity").setValue(ethnicity);
                    mChild.child("gender").setValue(gender);
                    Toast.makeText(SignupActivity.this, "Profile created!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), movieselection.class);
                    startActivity(i);
                }

            }
        });






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
