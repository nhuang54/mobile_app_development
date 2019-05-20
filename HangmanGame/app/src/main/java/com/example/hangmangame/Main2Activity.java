package com.example.hangmangame;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements HangmanFragment.HangmanFragmentListener,
        LetterFragment.LetterFragmentListener, HintFragment.HintFragmentListener {

    private String currWord; // initialization of currWord

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras(); // get bundle
        currWord = bundle.getString("currWord"); // get the stored string from bundle

        HangmanFragment hangmanFrag = (HangmanFragment) getFragmentManager().findFragmentById(R.id.hangmanFragment); // bind hangman fragment

        // check to see if the currWord from previous game is the same as this game.
        if (hangmanFrag.currWord == currWord){
            // if the same, pick another one
            hangmanFrag.startGame();
        }
        // check to see which orientation it is to either show hint or remove it.
        int orientation = this.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            HintFragment hintFrag = (HintFragment) getFragmentManager().findFragmentById(R.id.hintFragment);
            setHint(hangmanFrag.hint);
        }

    }

    // Recieving end for displaying hint
    public void setHint(String hint){
        HintFragment hintFrag = (HintFragment)getFragmentManager().findFragmentById(R.id.hintFragment); // bind hint fragment
        if (hintFrag != null) {
            // set txtHint in hint fragment to the corresponding hint
            String s = "HINT: " + hint;
            hintFrag.txtHint.setText(s);
        }
    }
    // does most of the gameplay here, when a button is pressed, we do the checking here
    public void checkLetter() {
        LetterFragment letterFrag = (LetterFragment)getFragmentManager().findFragmentById(R.id.letterFragment); // bind letter fragment
        HangmanFragment hangmanFrag = (HangmanFragment)getFragmentManager().findFragmentById(R.id.hangmanFragment); // bind hangman fragment

        boolean correct = false;
        // loop through the currWord and check if each letter match the pressed letter
        for (int k = 0; k < hangmanFrag.currWord.length(); k++) {
            // if match, correct = true, increment numCorr and make the letter visible in textView
            if (hangmanFrag.currWord.charAt(k) == letterFrag.letter) {
                hangmanFrag.corrLetter[hangmanFrag.index] = letterFrag.letter;
                hangmanFrag.index++;
                correct = true;
                hangmanFrag.numCorr++;
                hangmanFrag.charViews[k].setTextColor(Color.BLACK);
            }
        }
        // if boolean correct is true
        if (correct) {
            // check to see if number of correct answer is equal to length of currWord
            if (hangmanFrag.numCorr == hangmanFrag.numChars) {
                // if so, player has won. Sends a toast to congratulate user and shows the winner screen
                Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                i.putExtra("currWord", hangmanFrag.currWord);
                String s = "Yay, you won! The answer was " + hangmanFrag.currWord;
                startActivity(i);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
        // if guessed the wrong letter
        else if (hangmanFrag.currPart < hangmanFrag.numParts) {
            setBodyParts(); // call function to set the next body part when guessed wrong
        }
        // if player has lost
        else{
            // sends a toast to let player know they have lost and shows the loser screen
            Intent i = new Intent(getApplicationContext(), Main4Activity.class);
            i.putExtra("currWord", hangmanFrag.currWord);
            String s = "Boo, you lost! The answer was " + hangmanFrag.currWord;
            startActivity(i);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setBodyParts() {
        HangmanFragment hangmanFrag = (HangmanFragment)getFragmentManager().findFragmentById(R.id.hangmanFragment); // bind hangman fragment
        // show a part of hangman and increment currPart
        hangmanFrag.bodyParts[hangmanFrag.currPart].setVisibility(View.VISIBLE);
        hangmanFrag.currPart++;

    }
}
