package com.example.c4_p29;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String language = "";

    private Button btnCH;
    private Button btnKR;
    private Button btnJP;
    private Button btnES;
    private Button btnFR;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCH = (Button) findViewById(R.id.btnCH);
        btnKR = (Button) findViewById(R.id.btnKR);
        btnJP = (Button) findViewById(R.id.btnJP);
        btnES = (Button) findViewById(R.id.btnES);
        btnFR = (Button) findViewById(R.id.btnFR);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "CH";
            }
        });

        btnKR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "KR";
            }
        });

        btnJP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "JP";
            }
        });

        btnES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "ES";
            }
        });

        btnFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "FR";
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equals("")) {
                    String s = "Please pick a language before continuing";
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                    i.putExtra("secretVal", language);
                    startActivity(i);
                }
            }
        });
    }
}
