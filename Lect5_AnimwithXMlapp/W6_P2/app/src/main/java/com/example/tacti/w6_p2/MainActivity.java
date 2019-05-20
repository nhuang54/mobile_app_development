package com.example.tacti.w6_p2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtOne;
    private EditText edtOne;

    private CheckBox checkRed;
    private CheckBox checkBlue;
    private CheckBox checkGreen;

    private View view;

    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveSharedPreferenceInfo();

        view = this.getWindow().getDecorView();

        txtOne = (TextView) findViewById(R.id.txtOne);
        edtOne = (EditText) findViewById(R.id.edtOne);

        checkRed = (CheckBox) findViewById(R.id.checkRed);
        checkBlue = (CheckBox) findViewById(R.id.checkBlue);
        checkGreen = (CheckBox) findViewById(R.id.checkGreen);

        checkRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true){
                    txtOne.setText("RED");
                    edtOne.setText("RED");
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    color = "RED";
                }
            }
        });

        checkBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {
                    txtOne.setText("BLUE");
                    edtOne.setText("BLUE");
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    color = "BLUE";
                }
            }
        });

        checkGreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {
                    txtOne.setText("GREEN");
                    edtOne.setText("GREEN");
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                    color = "GREEN";
                }
            }
        });
    }

    void saveSharedPreferenceInfo() {
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = simpleAppInfo.edit();

        editor.putString("txtOne", txtOne.getText().toString());
        editor.putString("edtOne", edtOne.getText().toString());

        editor.putBoolean("checkRed", checkRed.isChecked());
        editor.putBoolean("checkBlue", checkBlue.isChecked());
        editor.putBoolean("checkGreen", checkGreen.isChecked());

        editor.apply();


    }

    void retrieveSharedPreferenceInfo() {
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

        String s1 = simpleAppInfo.getString("txtOne", "<missing>");
        String s2 = simpleAppInfo.getString("edtOne", "<missing>");

        Boolean b1 = simpleAppInfo.getBoolean("checkRed", false);
        Boolean b2 = simpleAppInfo.getBoolean("checkBlue", false);
        Boolean b3 = simpleAppInfo.getBoolean("checkGreen", false);

        String s3 = simpleAppInfo.getString("color", "<missing>");

        if (s3.equals("RED")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        if (s3.equals("BLUE")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }

        if (s3.equals("GREEN")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        txtOne.setText(s1);
        edtOne.setText(s2);

        checkRed.setChecked(b1);
        checkBlue.setChecked(b2);
        checkGreen.setChecked(b3);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //saveSharedPreferenceInfo();
        Log.i("a", "saveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putString("txtOne", txtOne.getText().toString());
        outState.putString("edtOne", edtOne.getText().toString());

        outState.putBoolean("checkRed", checkRed.isChecked());
        outState.putBoolean("checkBlue", checkBlue.isChecked());
        outState.putBoolean("checkGreen", checkGreen.isChecked());

        outState.putString("color", color);

        saveSharedPreferenceInfo();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //retrieveSharedPreferenceInfo();

        Log.i("a", "restoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);

        String s1 = savedInstanceState.getString("txtOne", "<missing>");
        String s2 = savedInstanceState.getString("edtOne", "<missing>");

        Boolean b1 = savedInstanceState.getBoolean("checkRed", false);
        Boolean b2 = savedInstanceState.getBoolean("checkBlue", false);
        Boolean b3 = savedInstanceState.getBoolean("checkGreen", false);

        String s3 = savedInstanceState.getString("color", "<missing>");

        if (s3.equals("RED")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        if (s3.equals("BLUE")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }

        if (s3.equals("GREEN")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        txtOne.setText(s1);
        edtOne.setText(s2);

        checkRed.setChecked(b1);
        checkBlue.setChecked(b2);
        checkGreen.setChecked(b3);
    }
}
