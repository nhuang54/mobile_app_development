package com.example.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnDiv;
    private Button btnMul;
    private Button btnSub;
    private Button btnAdd;
    private Button btnDot;
    private Button btnEnter;
    private Button btnClear;
    private Button btnSqrt;
    private EditText txtResult;
    private TextView txtDisplay;
    private double valueOne = Double.NaN;
    private double valueTwo;

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    //private static final char SQRT = 's';

    private char CURRENT_ACTION;

    DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        // operations
        btnDot = (Button) findViewById(R.id.btnDot);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnSqrt = (Button) findViewById(R.id.btnSqrt);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnEnter = (Button) findViewById(R.id.btnEnter);

        txtResult = (EditText) findViewById(R.id.txtResult);
        txtDisplay = (TextView) findViewById(R.id.txtDisplay);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + "9");
            }
        });
        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(txtResult.getText() + ".");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(null);
                txtDisplay.setText(null);
                CURRENT_ACTION = '0';
                valueOne = Double.NaN;
                valueTwo = Double.NaN;
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeValue();
                txtDisplay.setText(txtDisplay.getText().toString() + decimalFormat.format(valueTwo) + " = " + decimalFormat.format(valueOne));
                //valueOne = Double.NaN;
                CURRENT_ACTION = '0';
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeValue();
                CURRENT_ACTION = ADDITION;
                txtDisplay.setText(decimalFormat.format(valueOne) + "+");
                txtResult.setText(null);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeValue();
                CURRENT_ACTION = SUBTRACTION;
                txtDisplay.setText(decimalFormat.format(valueOne) + "-");
                txtResult.setText(null);
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeValue();
                CURRENT_ACTION = MULTIPLICATION;
                txtDisplay.setText(decimalFormat.format(valueOne) + "*");
                txtResult.setText(null);
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeValue();
                CURRENT_ACTION = DIVISION;
                txtDisplay.setText(decimalFormat.format(valueOne) + "/");
                txtResult.setText(null);
            }
        });
        btnSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_ACTION = '0';
                if (txtResult.getText().toString().matches("")) {
                    if (!Double.isNaN(valueOne)) {
                        txtDisplay.setText("sqrt(" + decimalFormat.format(valueOne) + ") = " + decimalFormat.format(Math.sqrt(valueOne)));
                        valueOne = Math.sqrt(valueOne);
                    }
                }
                else{
                    double tmp = Double.parseDouble(txtResult.getText().toString());
                    txtDisplay.setText("sqrt(" + tmp + ") = " + decimalFormat.format(Math.sqrt(tmp)));
                    valueOne = Math.sqrt(tmp);
                }
                txtResult.setText(null);
            }
        });
    }

    private void computeValue() {
        if (!Double.isNaN(valueOne)) {
            if (txtResult.getText().toString().matches("")) {

            }
            else {
                valueTwo = Double.parseDouble(txtResult.getText().toString());
                txtResult.setText(null);

                if (CURRENT_ACTION == ADDITION)
                    valueOne = this.valueOne + valueTwo;
                else if (CURRENT_ACTION == SUBTRACTION)
                    valueOne = this.valueOne - valueTwo;
                else if (CURRENT_ACTION == MULTIPLICATION)
                    valueOne = this.valueOne * valueTwo;
                else if (CURRENT_ACTION == DIVISION)
                    valueOne = this.valueOne / valueTwo;
            }
        } else {
            if (txtResult.getText().toString().matches("")) {

            }
            else
                valueOne = Double.parseDouble(txtResult.getText().toString());

        }
    }
}


