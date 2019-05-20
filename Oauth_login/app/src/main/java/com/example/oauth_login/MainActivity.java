package com.example.oauth_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // facebook
    private CallbackManager callbackManager;
    private LoginButton btn_fb_login;

    // google
    private SignInButton btn_google_login;
    private Button btn_logout;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    private TextView txtResult; // display login infos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        // facebook
        btn_fb_login = (LoginButton)findViewById(R.id.btn_fb_login);
        callbackManager = CallbackManager.Factory.create();
        // google
        btn_google_login = (SignInButton)findViewById(R.id.btn_google_login);
        btn_logout = (Button)findViewById(R.id.btn_logout);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        txtResult = (TextView)findViewById(R.id.txtResult);

        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        btn_fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                txtResult.setText("Login Success \n" +
//                    loginResult.getAccessToken().getUserId()+ "\n" +
//                    loginResult.getAccessToken().getToken());
                String userID = loginResult.getAccessToken().getUserId();

                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                txtResult.setText("Login Unsuccessful");
            }


            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, REQ_CODE);
    }

    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            txtResult.setText(name + "\n" + email);

            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
            i.putExtra("name", name);
            i.putExtra("email", email);
            startActivity(i);
        }
    }
}
