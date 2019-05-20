package com.example.sse.lect3_bundles_two_ways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {


    private TextView tvMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        String secretMsg;  //TRUE OR FALSE: This is the key to the Bundle/Hashmap?
        Bundle bundle = getIntent().getExtras();
        secretMsg = bundle.getString("secretVal");  //TRUE OR FALSE: The parameter in quotes, is the key to the Bundle/Hashmap?
        secretMsg = "Secret Message From Activity 1: " + secretMsg;

        tvMsg.setText(secretMsg);  //QUESTION: Is this the Key or Value?
        Toast.makeText(Main2Activity.this, "The first activity sent me a Secret Value = " + secretMsg , Toast.LENGTH_LONG).show();


    }
}
