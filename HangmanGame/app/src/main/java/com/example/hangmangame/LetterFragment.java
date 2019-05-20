package com.example.hangmangame;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LetterFragment extends Fragment {

    // initializing all the required buttons
    private Button btnA; private Button btnB; private Button btnC;
    private Button btnD; private Button btnE; private Button btnF;
    private Button btnG; private Button btnH; private Button btnI;
    private Button btnJ; private Button btnK; private Button btnL;
    private Button btnM; private Button btnN; private Button btnO;
    private Button btnP; private Button btnQ; private Button btnR;
    private Button btnS; private Button btnT; private Button btnU;
    private Button btnV; private Button btnW; private Button btnX;
    private Button btnY; private Button btnZ;

    public char letter; // initialize letter variable
    private char[] usedButton = new char[26]; // array of stored pressed buttons
    private int index; // index into the usedButton array

    public LetterFragment() {
        // Required empty public constructor
    }
    // setting up the interface
    public interface LetterFragmentListener {
        public void checkLetter();
    }
    
    LetterFragmentListener LFL;
    
    public void onAttach(Context context){
        super.onAttach(context);
        LFL = (LetterFragmentListener) context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // store required data when changing orientation
        outState.putCharArray("usedButton", usedButton);
        outState.putInt("index", index);

        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_letter, container, false);

        // initiliaze all the buttons and connect them to their buttons in xml file
        btnA = (Button) view.findViewById(R.id.btnA); btnB = (Button) view.findViewById(R.id.btnB);
        btnC = (Button) view.findViewById(R.id.btnC); btnD = (Button) view.findViewById(R.id.btnD);
        btnE = (Button) view.findViewById(R.id.btnE); btnF = (Button) view.findViewById(R.id.btnF);
        btnG = (Button) view.findViewById(R.id.btnG); btnH = (Button) view.findViewById(R.id.btnH);
        btnI = (Button) view.findViewById(R.id.btnI); btnJ = (Button) view.findViewById(R.id.btnJ);
        btnK = (Button) view.findViewById(R.id.btnK); btnL = (Button) view.findViewById(R.id.btnL);
        btnM = (Button) view.findViewById(R.id.btnM); btnN = (Button) view.findViewById(R.id.btnN);
        btnO = (Button) view.findViewById(R.id.btnO); btnP = (Button) view.findViewById(R.id.btnP);
        btnQ = (Button) view.findViewById(R.id.btnQ); btnR = (Button) view.findViewById(R.id.btnR);
        btnS = (Button) view.findViewById(R.id.btnS); btnT = (Button) view.findViewById(R.id.btnT);
        btnU = (Button) view.findViewById(R.id.btnU); btnV = (Button) view.findViewById(R.id.btnV);
        btnW = (Button) view.findViewById(R.id.btnW); btnX = (Button) view.findViewById(R.id.btnX);
        btnY = (Button) view.findViewById(R.id.btnY); btnZ = (Button) view.findViewById(R.id.btnZ);

        if (savedInstanceState != null) {
            // get the required data after changing orientation
            usedButton = savedInstanceState.getCharArray("usedButton");
            index = savedInstanceState.getInt("index");

            resumeBtns(); // restore to default settings for buttons before changing orientation.
        }
        // binding each button to perform few actions on click: disable button, store the letter pressed into variable and array and call function
        // defined in activity
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnA.setEnabled(false);
                letter = 'A';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnB.setEnabled(false);
                letter = 'B';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnC.setEnabled(false);
                letter = 'C';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnD.setEnabled(false);
                letter = 'D';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnE.setEnabled(false);
                letter = 'E';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnF.setEnabled(false);
                letter = 'F';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnG.setEnabled(false);
                letter = 'G';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnH.setEnabled(false);
                letter = 'H';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnI.setEnabled(false);
                letter = 'I';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJ.setEnabled(false);
                letter = 'J';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnK.setEnabled(false);
                letter = 'K';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnL.setEnabled(false);
                letter = 'L';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnM.setEnabled(false);
                letter = 'M';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnN.setEnabled(false);
                letter = 'N';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnO.setEnabled(false);
                letter = 'O';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnP.setEnabled(false);
                letter = 'P';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnQ.setEnabled(false);
                letter = 'Q';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnR.setEnabled(false);
                letter = 'R';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnS.setEnabled(false);
                letter = 'S';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnT.setEnabled(false);
                letter = 'T';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnU.setEnabled(false);
                letter = 'U';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnV.setEnabled(false);
                letter = 'V';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnW.setEnabled(false);
                letter = 'W';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnX.setEnabled(false);
                letter = 'X';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnY.setEnabled(false);
                letter = 'Y';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });
        btnZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZ.setEnabled(false);
                letter = 'Z';
                usedButton[index] = letter;
                index++;
                LFL.checkLetter();
            }
        });

        return view;
    }
    private void resumeBtns(){
        // re-initialize all buttons and ones that are disabled from before, keep disabled
        for (int i = 0; i < index; i++){
            if (usedButton[i] == 'A'){
                btnA.setEnabled(false);
            }
            else if (usedButton[i] == 'B'){
                btnB.setEnabled(false);
            }
            else if (usedButton[i] == 'C'){
                btnC.setEnabled(false);
            }
            else if (usedButton[i] == 'D'){
                btnD.setEnabled(false);
            }
            else if (usedButton[i] == 'E'){
                btnE.setEnabled(false);
            }
            else if (usedButton[i] == 'F'){
                btnF.setEnabled(false);
            }
            else if (usedButton[i] == 'G'){
                btnG.setEnabled(false);
            }
            else if (usedButton[i] == 'H'){
                btnH.setEnabled(false);
            }
            else if (usedButton[i] == 'I'){
                btnI.setEnabled(false);
            }
            else if (usedButton[i] == 'J'){
                btnJ.setEnabled(false);
            }
            else if (usedButton[i] == 'K'){
                btnK.setEnabled(false);
            }
            else if (usedButton[i] == 'L'){
                btnL.setEnabled(false);
            }
            else if (usedButton[i] == 'M'){
                btnM.setEnabled(false);
            }
            else if (usedButton[i] == 'N'){
                btnN.setEnabled(false);
            }
            else if (usedButton[i] == 'O'){
                btnO.setEnabled(false);
            }
            else if (usedButton[i] == 'P'){
                btnP.setEnabled(false);
            }
            else if (usedButton[i] == 'Q'){
                btnQ.setEnabled(false);
            }
            else if (usedButton[i] == 'R'){
                btnR.setEnabled(false);
            }
            else if (usedButton[i] == 'S'){
                btnS.setEnabled(false);
            }
            else if (usedButton[i] == 'T'){
                btnT.setEnabled(false);
            }
            else if (usedButton[i] == 'U'){
                btnU.setEnabled(false);
            }
            else if (usedButton[i] == 'V'){
                btnV.setEnabled(false);
            }
            else if (usedButton[i] == 'W'){
                btnW.setEnabled(false);
            }
            else if (usedButton[i] == 'X'){
                btnX.setEnabled(false);
            }
            else if (usedButton[i] == 'Y'){
                btnY.setEnabled(false);
            }
            else if (usedButton[i] == 'Z'){
                btnZ.setEnabled(false);
            }
        }
    }

    // disables all alphabet buttons
    public void disableBtns() {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnE.setEnabled(false);
        btnF.setEnabled(false);
        btnG.setEnabled(false);
        btnH.setEnabled(false);
        btnI.setEnabled(false);
        btnJ.setEnabled(false);
        btnK.setEnabled(false);
        btnL.setEnabled(false);
        btnM.setEnabled(false);
        btnN.setEnabled(false);
        btnO.setEnabled(false);
        btnP.setEnabled(false);
        btnQ.setEnabled(false);
        btnR.setEnabled(false);
        btnS.setEnabled(false);
        btnT.setEnabled(false);
        btnU.setEnabled(false);
        btnV.setEnabled(false);
        btnW.setEnabled(false);
        btnX.setEnabled(false);
        btnY.setEnabled(false);
        btnZ.setEnabled(false);
    }

}
