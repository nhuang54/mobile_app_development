package com.example.sse.lect2_activitylifecycle_logging_savingstate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String MyFlag = "LECT2_FLAG";  //this will be our trail of breadcrumbs for logging events.
    private static int eventCount = 0;


    private SeekBar seekFah;
    private SeekBar seekCel;
    private TextView txtCelResult;
    private TextView txtFahResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onCreate State Transition");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//Let's find our view references

        seekCel = (SeekBar) findViewById(R.id.seekCel);
        seekFah = (SeekBar) findViewById(R.id.seekFah);
        txtCelResult = (TextView) findViewById(R.id.txtCelResult);
        txtFahResult = (TextView) findViewById(R.id.txtFahResult);

        int max = 212;
        final int min = 32;
        seekFah.setMax(max - min);

        int max2 = 100;
        final int min2 = 0;
        seekCel.setMax(max2 - min2);

        seekCel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double val = i + min2;
                txtCelResult.setText(String.valueOf(val));
                if (b) {
                    double DegF = val*9.0/5.0 + 32;
                    txtFahResult.setText(String.valueOf(DegF));
                    seekFah.setProgress((int) DegF - 32);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekFah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double val = i + min;
                txtFahResult.setText(String.valueOf(val));
                if (b) {
                    double DegC = (val - 32) / 1.8;
                    txtCelResult.setText(String.valueOf(DegC));
                    seekCel.setProgress((int) DegC);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Useful Notes:
        // ctrl-O is a shortcut to override base methods
        // Alt-Ins is a shortcut to overriding base methods and more.

    @Override
    protected void onPause() {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onPause State Transition");
        super.onPause();
    }


    @Override
    protected void onStart() {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onStart State Transition");
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onSaveInstanceState State Transition");
        Log.i(MyFlag, "Bundling State of our views before they get destroyed");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onRestoreInstanceState State Transition");
        Log.i(MyFlag, "Retrieving our saved state from before... ");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        eventCount++;
        Log.i(MyFlag, intToStr(eventCount) + ": Activity onRestoreInstanceState State Transition");
        super.onResume();
    }


//Handy Helpers...
    public String intToStr(Integer i)
    {
        return i.toString();
    }

    public int strToInt(String S)
    {
       return Integer.parseInt(S);
    }


}

