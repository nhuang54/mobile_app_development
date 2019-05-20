package com.example.problem221;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private TextView txtNum1;
    private TextView txtNum2;
    private TextView txtResult;

    Random r = new Random();
    private int num1 = r.nextInt(10) + 1;
    private int num2 = r.nextInt(10) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtNum1 = (TextView) findViewById(R.id.txtNum1);
        txtNum2 = (TextView) findViewById(R.id.txtNum2);
        txtResult = (TextView) findViewById(R.id.txtResult);

        txtNum1.setText(Integer.toString(num1));
        txtNum2.setText(Integer.toString(num2));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(Integer.toString(num1 + num2));
            }
        });
    }
}
