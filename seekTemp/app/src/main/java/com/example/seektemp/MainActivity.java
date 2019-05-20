package com.example.seektemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekFah;
    private SeekBar seekCel;
    private TextView txtCelResult;
    private TextView txtFahResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
