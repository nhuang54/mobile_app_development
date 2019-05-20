package com.example.flashcardapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    // firebase auth fields
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseReference;

    // initilize all txt/btn views
    private TextView txtNum1;
    private TextView txtNum2;
    private TextView txtUser;
    private EditText txtAnswer;
    private Button btnLogout;
    private Button btnSubmit;

    // keys to access db
    private String keyUser;
    private String key;

    // for db
    private int counter = 1;
    private String prob_num = "Problem " + counter;
    // score, operand 1, operand2
    private int count, total = 0;
    Random rand = new Random();
    private int x, y, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // bind all variables to their respective view ids
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtNum1 = (TextView) findViewById(R.id.txtNum1);
        txtNum2 = (TextView) findViewById(R.id.txtNum2);
        txtAnswer = (EditText) findViewById(R.id.txtAnswer);
        txtUser = (TextView) findViewById(R.id.txtUser);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // get user key from intent
        keyUser = getIntent().getStringExtra("USER_KEY");

        // Firebase Database reference with user's key
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(keyUser);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // user signed in
                    String email = user.getEmail();
                    txtUser.setText(email); // display the user's email at the bottom textview
                }
                else{
                    // user not signed in
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };
        // get key to new problem
        key = mDatabaseReference.child("userTest").push().getKey();
        generateProblem(); // call function to generate problem onCreate
        // onclick for submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if the answer is empty or not
                if (!TextUtils.isEmpty(txtAnswer.getText().toString())) {
                    if (Integer.parseInt(txtAnswer.getText().toString()) == y) { // check if its the answer
                        // if so increase, the number of correct answers(count) and update DB with a boolean for that problem
                        mDatabaseReference.child("userTest").child(key).child(prob_num).child("AnsweredCorrectly").setValue(true);
                        count++;
                    }
                    else{
                        // if incorrect, update DB to reflect it in the problem
                        mDatabaseReference.child("userTest").child(key).child(prob_num).child("AnsweredCorrectly").setValue(false);
                    }
                }
                total++; // increment the total number of problems
                if (total == 10) { // if 10 problems has been generated
                    // show a toast of how they did
                    String s = "Congratulations, you score is " + count + " out of " + total;
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    // store their score for their problem session into the db and update their recent score
                    mDatabaseReference.child("userTest").child(key).child("problemScore").setValue(count);
                    mDatabaseReference.child("userScore").setValue(count);
                    // move to menu activity with top scores and pass along with USER_KEY and USER_SCORE
                    Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                    i.putExtra("USER_KEY", keyUser);
                    i.putExtra("USER_SCORE", count);
                    startActivity(i);
                } else {
                    // if theres more problem to generate, call generateProblem function
                    generateProblem();
                    txtAnswer.setText(""); // clear answer field
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }
    private void generateProblem(){
        // generate two random numbers
        x = rand.nextInt(50) + 1;
        y = rand.nextInt(25) + 1;
        // multiply them to get a new number
        z = x * y;
        // show two of the numbers
        txtNum1.setText(String.valueOf(z));
        txtNum2.setText(String.valueOf(x));

        // store operands and operation into db
        prob_num = "Problem " + counter;
        mDatabaseReference.child("userTest").child(key).child(prob_num).child("Operand1").setValue(z);
        mDatabaseReference.child("userTest").child(key).child(prob_num).child("Operand2").setValue(x);
        mDatabaseReference.child("userTest").child(key).child(prob_num).child("Operation").setValue("/");
        mDatabaseReference.child("userTest").child(key).child(prob_num).child("AnsweredCorrectly").setValue(null);

        counter++;
    }
    // bundle for changing orientation for previous simplier version of flashcard app, now locked to portrait
    // so disregard this
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("val1", x);
        outState.putInt("val2", y);
        outState.putInt("val3", z);
        outState.putInt("val4", count);
        outState.putInt("val5", total);

        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        x = savedInstanceState.getInt("val1");
        y = savedInstanceState.getInt("val2");
        z = savedInstanceState.getInt("val3");
        count = savedInstanceState.getInt("val4");
        total = savedInstanceState.getInt("val5");

        txtNum1.setText(String.valueOf(z));
        txtNum2.setText(String.valueOf(x));
    }
    // add and remove AuthStateListener before and after.
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

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
