package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnSayHello;
    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSayHello = (Button) findViewById(R.id.btnSayHello);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        btnSayHello.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View view) { txtMsg.setText("Hello World.");}
        });
    }
}
