package com.example.incluvie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // firebase auth fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    //DatabaseReference mDatabaseRef;

    // username/password for login
    String userEmailString, userPasswordString;

    // UI references.
    private View mProgressView;
    private View mLoginFormView;
    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPass = (EditText)findViewById(R.id.edtPass);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    Intent i = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(i);
                }else{}
            }
        };

        // login with account
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform login ops
                userEmailString = edtUser.getText().toString().trim();
                userPasswordString = edtPass.getText().toString().trim();

                if (!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)){
                    mAuth.signInWithEmailAndPassword(userEmailString, userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(getApplicationContext(), Welcome.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "username/password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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


