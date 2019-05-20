package com.example.hangmanmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    //private ImageView[] bodyParts;
    private int numParts = 6;
    private int currPart;
    private int numChars;
    private int numCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //bodyParts = new ImageView[numParts];
        //bodyParts[0] = (ImageView)findViewById(R.id.head);
        //bodyParts[1] = (ImageView)findViewById(R.id.body);
        //bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        //bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        //bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        //bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        //for(int p = 0; p < numParts; p++) {
        //    bodyParts[p].setVisibility(View.INVISIBLE);
        //}
    }
}
