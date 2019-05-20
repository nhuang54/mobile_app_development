package com.example.c2_p23;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPress;
    private TextView txtLight;

    int light = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPress = (Button) findViewById(R.id.btnPress);
        txtLight = (TextView) findViewById(R.id.txtLight);

        final String red = "#FF0000";
        final String green = "#00FF00";
        final String yellow = "#FFFF00";

        txtLight.setBackgroundColor(Color.parseColor(red));

        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (light == 0){
                    txtLight.setBackgroundColor(Color.parseColor(green));
                    light = 1;
                }
                else if (light == 1) {
                    txtLight.setBackgroundColor(Color.parseColor(yellow));
                    light = 2;
                }
                else if (light == 2) {
                    txtLight.setBackgroundColor(Color.parseColor(red));
                    light = 0;
                }
            }
        });
    }
}
