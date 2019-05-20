package com.example.flashcardapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Main3Activity extends AppCompatActivity {

    // firebase auth fields
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseReference;

    // variables for layout views
    private Button btnLogout;
    private Button btnRestart;
    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;
    private TextView txtFourth;
    private TextView txtFifth;

    // keys to access db
    private String keyUser;
    private String key;
    // variables needed to display top scores and keep track of everything
    private int score; // score passed from previous Activity
    private String userEmail; // email to be displayed in top score
    // arrays for keep record of top user scores along with their respective emails
    private String[] userList = new String[5];
    private int[] scoreList = new int[5];
    // loop variable
    private int i;
    // array to index the 5 textViews for display top score results
    private TextView[] viewlist = new TextView[5];
    private int currentMin; // find the min score
    private int numScore; // keep track of how many top scores are there so far
    // keep track of top 5 scores
    private int N = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // bind all variables to their respective view ids
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnRestart = (Button) findViewById(R.id.btnRestart);
        txtFirst = (TextView) findViewById(R.id.txtFirst);
        txtSecond = (TextView) findViewById(R.id.txtSecond);
        txtThird = (TextView) findViewById(R.id.txtThird);
        txtFourth = (TextView) findViewById(R.id.txtFourth);
        txtFifth = (TextView) findViewById(R.id.txtFifth);
        // store all the TextViews into the array for easy reference
        viewlist[0] = txtFirst;
        viewlist[1] = txtSecond;
        viewlist[2] = txtThird;
        viewlist[3] = txtFourth;
        viewlist[4] = txtFifth;

        // get user key from intent
        keyUser = getIntent().getStringExtra("USER_KEY");
        score = getIntent().getExtras().getInt("USER_SCORE");

        // Firebase Database reference
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("topScores");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // user signed in
                }
                else{
                    // user not signed in
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };
        userEmail = mAuth.getCurrentUser().getEmail(); // get current user's Email
        // STEP 1:
        updateScore(); // call updateScore function
        // onclick for play again button
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to Main2Activity and pass along same user's key
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("USER_KEY", keyUser);
                startActivity(i);
            }
        });
        // onclick for user to logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }
    // function to update scores in the Firebase Database
    private void updateScore(){
        // add a singleValueEvent listener to mDatabaseReference for a one time deal
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the value from the snapshot, returns as a object, converted to long before
                // able to cast to int
                long longScore = (long)dataSnapshot.child("numScores").getValue();
                numScore = (int)longScore;
                // check if there are 5 top scores in the DB already
                if (numScore < N) { // if less than 5
                    // just add it into the database with the associated key, email and score
                    numScore++;
                    mDatabaseReference.child("numScores").setValue(numScore);
                    DatabaseReference mChildDatabase = mDatabaseReference.push();
                    key = mChildDatabase.getKey();

                    mChildDatabase.child("scoreKey").setValue(key);
                    mChildDatabase.child("userEmail").setValue(userEmail);
                    mChildDatabase.child("userScore").setValue(score);
                }
                else{
                    // STEP 2:
                    // if there are 5 top scores already, we will need to find the min score and replace and replace
                    // if the current user's score is higher than the min score in the DB by calling findMinUpdate
                    findMinUpdate();
                }
                // STEP 3:
                // finally we display the top 5 scores
                showScore();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // Function to find the min score within the DB
    private void findMinUpdate(){
        key = null; // set key to null for upcoming check
        // get a snapshot of the DB
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentMin = 10; // set min to a initial value
                Iterator iterator = dataSnapshot.getChildren().iterator();
                // loop for finding the minimum score from all the scores stored in the DB
                for (i = 0; i < N; i++){ // can use N here because function only called when there is N scores
                    DataSnapshot dataUser = (DataSnapshot)iterator.next(); // get next children
                    // get value from DB and convert to long before able to cast to int, thats the way it is
                    // as far as I know it
                    long longScore = (long)dataUser.child("userScore").getValue();
                    int currentScore = (int)longScore;
                    if (currentScore < currentMin) { // once we find a score less than the currentMin
                        // update currentMin and save the key
                        currentMin = currentScore;
                        key = dataUser.child("scoreKey").getValue().toString();
                    }
                }
                if (key != null) { // if the key is not null
                    if (score > currentMin) { // check to see if current user's score is higher than the min score from DB
                        // if true, replace the score and email of that min score in DB to the current user's one
                        mDatabaseReference.child(key).child("userEmail").setValue(userEmail);
                        mDatabaseReference.child(key).child("userScore").setValue(score);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // after updating DB, its time to show the top scores
    private void showScore(){
        // need this any time u want to read from the DB
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                // grab all the scores within the DB and store them into userList and scoreList array
                for (i = 0; i < numScore; i++){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    userList[i] = dataUser.child("userEmail").getValue().toString();
                    long longScore = (long)dataUser.child("userScore").getValue();
                    int currentScore = (int)longScore;
                    scoreList[i] = currentScore;
                }
                // STEP 4: process the two list: sort userList and scoreList from hi to lo
                sortScores();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    // sort the scores and store them in a new array: scoreList2 and userList2 where the scores a sorted
    // from hi to lo. Then display them onto the top Score TextViews
    private void sortScores(){
        int[] scoreList2 = new int[5];
        String[] userList2 = new String[5];
        // loop for finding the next highest score from the old list and store it into the new list
        for (int j = 0; j < numScore; j++) {
            int currentMax = 0;
            int index = 0;
            for (i = 0; i < numScore; i++) {
                if (scoreList[i] > currentMax) {
                    currentMax = scoreList[i];
                    index = i;
                }
            }
            scoreList2[j] = scoreList[index];
            userList2[j] = userList[index];
            scoreList[index] = 0;
        }
        // after sorting, display them one by one to the TextViews
        for (i = 0; i < numScore; i++) {
            String topScore = userList2[i] + ":  " + scoreList2[i];
            viewlist[i].setText(topScore);
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

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
