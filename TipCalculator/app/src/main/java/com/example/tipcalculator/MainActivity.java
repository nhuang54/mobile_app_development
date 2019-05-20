package com.example.tipcalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends Activity {

    // currency and percentage formatter
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount at the beginning
    private double percent = 0.15; // initial percentage

    private TextView txtAmount; // shows formatted bill amount
    private TextView txtPercent; // shows tip percentage
    private TextView txtTip; // shows the calculated tip amount
    private TextView txtTotal; // shows the calculated total amount
    private EditText EdtTxtAmount; // displays bill amount
    private SeekBar percentSeekBar; // initialize seekbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a bind between the variable names and their respective id
        txtAmount = (TextView)findViewById(R.id.txtAmount);
        txtPercent = (TextView)findViewById(R.id.txtPercent);
        txtTip = (TextView)findViewById(R.id.txtTip);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        EdtTxtAmount = (EditText)findViewById(R.id.EdtTxtAmount);
        percentSeekBar = (SeekBar)findViewById(R.id.percentSeekBar);

        txtTip.setText(currencyFormat.format(0)); // set text to 0
        txtTotal.setText(currencyFormat.format(0)); // set text to 0
        // create a on seekbar change listener
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percent = i / 100.0; // set percent based on progress
                calculate(); // call calculate function
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // create a text change listener for EdtTextAmount
        EdtTxtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // for if billAmount is empty or invalid input
                try{
                    billAmount = Double.parseDouble(charSequence.toString()) / 100.0; // try to get the billAmount and / 100.0
                    txtAmount.setText(currencyFormat.format(billAmount)); // show that amount in txtAmount
                }
                // if billAmount is empty or invalid
                catch (NumberFormatException e) {
                    txtAmount.setText(""); // set txtAmount to be empty
                    billAmount = 0.0;
                }
                calculate(); // call calculate function
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    // given tip and billAmount, calculates the tip cost and total cost
    private void calculate() {

        txtPercent.setText(percentFormat.format(percent)); // reflect the percent based on seekbar to textView

        double tip = billAmount * percent; // get tip amount
        double total = billAmount + tip; // add tip to the billAmount to get total amount

        txtTip.setText(currencyFormat.format(tip)); // show tip in txtTip textView
        txtTotal.setText(currencyFormat.format(total)); // show total cost in txtTotal textView

    }
}
