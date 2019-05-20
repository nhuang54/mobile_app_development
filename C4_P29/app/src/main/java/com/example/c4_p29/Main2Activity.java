package com.example.c4_p29;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private Button btnTranslate;
    private Button btnBack;
    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnTranslate = (Button) findViewById(R.id.btnTranslate);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtDisplay = (TextView) findViewById(R.id.txtDisplay);

        final String language;
        Bundle bundle = getIntent().getExtras();
        language = bundle.getString("secretVal");

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equals("CH")){
                    txtDisplay.setText("你好，世界");
                }
                else if (language.equals("KR")){
                    txtDisplay.setText("안녕하세요 세계");
                }
                else if (language.equals("JP")){
                    txtDisplay.setText("こんにちは世界");
                }
                else if (language.equals("ES")){
                    txtDisplay.setText("Hola Mundo");
                }
                else if (language.equals("FR"))
                    txtDisplay.setText("Bonjour le monde");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
