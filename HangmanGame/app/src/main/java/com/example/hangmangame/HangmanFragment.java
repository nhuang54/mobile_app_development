package com.example.hangmangame;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class HangmanFragment extends Fragment {

    public LinearLayout wordLayout; // reference to the word layout
    public TextView[] charViews; // textviews to be populated later

    public String hint; // the hint will be stored here
    private String[] fruits; // list of fruit words
    private String[] animals; // list of animal words
    private Random rand; // random number generator
    public String currWord; // the word being guessed
    public char[] corrLetter = new char[26]; // correct letters stored in an array
    public int index; // index into corrLetter array

    //body part images
    public ImageView[] bodyParts;
    //number of body parts
    public int numParts = 6;
    //current part - will increment when wrong answers are chosen
    public int currPart;
    //number of characters in current word
    public int numChars;
    //number correctly guessed
    public int numCorr;

    public HangmanFragment() {
        // Required empty public constructor
    }
    // setting up the interface
    public interface HangmanFragmentListener {
        public void setBodyParts();
    }

    HangmanFragmentListener HFL; // future reference to an object that implements HangmanFragmentListener

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HFL = (HangmanFragmentListener) context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // store required data when changing orientation
        outState.putInt("currPart", currPart);
        outState.putInt("numChars", numChars);
        outState.putInt("numCorr", numCorr);
        outState.putString("currWord", currWord);
        outState.putString("hint", hint);
        outState.putCharArray("corrLetter", corrLetter);
        outState.putInt("index", index);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hangman, container, false);

        // bind each body part's id to its respective variable
        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) view.findViewById(R.id.head);
        bodyParts[1] = (ImageView) view.findViewById(R.id.body);
        bodyParts[2] = (ImageView) view.findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) view.findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) view.findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) view.findViewById(R.id.leg2);

        wordLayout = (LinearLayout) view.findViewById(R.id.word); // find layout id and bind it

        if (savedInstanceState != null) {
            // get required data after changing orientation
            currPart = savedInstanceState.getInt("currPart");
            numChars = savedInstanceState.getInt("numChars");
            numCorr = savedInstanceState.getInt("numCorr");
            currWord = savedInstanceState.getString("currWord");
            hint = savedInstanceState.getString("hint");
            index = savedInstanceState.getInt("index");
            corrLetter = savedInstanceState.getCharArray("corrLetter");

            initializeLayout(); // initilize the layout for hangman and the word to guess

            // re-initialize hangman and only show the parts from before changing modes
            for (int p = 0; p < currPart; p++) {
                bodyParts[p].setVisibility(View.VISIBLE);
            }

            // re-populate the letters that's been guessed right before changing modes
            for (int i = 0; i < corrLetter.length; i++) {
                for (int k = 0; k < currWord.length(); k++) {
                    if (currWord.charAt(k) == corrLetter[i]) {
                        charViews[k].setTextColor(Color.BLACK);
                    }
                }
            }
        }
        else {
            currWord = ""; // initialize current word
            startGame(); // start the game, which is implementing the fragment layout
        }
        //Toast.makeText(getActivity(), currWord, Toast.LENGTH_LONG).show();

        return view;
    }
    // function to initialize the layout of the hangman and currWord at the start of the game
    public void initializeLayout(){
        charViews = new TextView[currWord.length()]; // initialize the textViews for the currWord

        wordLayout.removeAllViews(); // hide all the textViews

        // re-initialize everything and  hide all the letters and only show the underline for each letter
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(getActivity());
            charViews[c].setText("" + currWord.charAt(c));

            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);
        }
        // hide all the body parts to only show the gallow.
        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }
    }
    // call this function at the start of the game to initialize everything for the hangman game.
    public void startGame(){
        Resources res = getResources();  // request the application's resources
        fruits = res.getStringArray(R.array.fruits); // read the collection of words in fruits and store in fruits
        animals = res.getStringArray(R.array.animals); // read the collection of words in animals and store in animals

        // generate rand and initialize currWord to empty string
        rand = new Random();
        int i = rand.nextInt(2); // picks a number from 0 and 1 to decide which group to pick the word from
        String newWord; // stores the newly picked word
        // if i == 0 then choose a word from fruits
        if (i == 0) {
            hint = "fruits"; // set hint to fruits
            newWord = fruits[rand.nextInt(fruits.length)];
            // make sure the new word is not equal to the previous word
            while (newWord.equals(currWord)) {
                newWord = fruits[rand.nextInt(fruits.length)];
            }
        }
        // if i == 1 then choose a word from animals
        else {
            hint = "animals"; // set hint to animals
            newWord = animals[rand.nextInt(animals.length)];
            // make sure the new word is not equal to the previous word
            while (newWord.equals(currWord)) {
                newWord = animals[rand.nextInt(animals.length)];
            }
        }
        index = 0; // initialize index
        currWord = newWord; // store newWord into currWord

        initializeLayout(); // initilize hangman game to corresponding currWord

        currPart = 0; // initialize the number of wrong answer to 0
        numChars = currWord.length(); // set numChars to length of currWord
        numCorr = 0; // initialize number of correct answers
    }

}
