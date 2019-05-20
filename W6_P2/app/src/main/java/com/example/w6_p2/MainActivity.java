package com.example.w6_p2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // initializing views and checkboxes
    private TextView txtOne;
    private EditText edtOne;

    private CheckBox checkRed;
    private CheckBox checkBlue;
    private CheckBox checkGreen;

    private View view;
    private String color; // variable for current color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = this.getWindow().getDecorView(); // get the view for changing background color

        // binding checkboxes and texts
        txtOne = (TextView) findViewById(R.id.txtOne);
        edtOne = (EditText) findViewById(R.id.edtOne);

        checkRed = (CheckBox) findViewById(R.id.checkRed);
        checkBlue = (CheckBox) findViewById(R.id.checkBlue);
        checkGreen = (CheckBox) findViewById(R.id.checkGreen);
        // set up onChecked listener for each checkbox, set text to corresponding color
        // change the background color and store color in variable color.
        checkRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {
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
        retrieveSharedPreferenceInfo(); // restore defaults before closing app
    }
    // function for storing shared preferences
    void saveSharedPreferenceInfo() {
        // learned from lecture
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = simpleAppInfo.edit();
        // store everything we need
        editor.putString("txtOne", txtOne.getText().toString());
        editor.putString("edtOne", edtOne.getText().toString());
        editor.putString("color", color);

        editor.putBoolean("checkRed", checkRed.isChecked());
        editor.putBoolean("checkBlue", checkBlue.isChecked());
        editor.putBoolean("checkGreen", checkGreen.isChecked());

        editor.apply(); // commit the changes
    }
    // function for retrieving shared preferences
    void retrieveSharedPreferenceInfo() {
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

        // get everything we need
        String s1 = simpleAppInfo.getString("txtOne", "<missing>");
        String s2 = simpleAppInfo.getString("edtOne", "<missing>");

        Boolean b1 = simpleAppInfo.getBoolean("checkRed", false);
        Boolean b2 = simpleAppInfo.getBoolean("checkBlue", false);
        Boolean b3 = simpleAppInfo.getBoolean("checkGreen", false);

        String s3 = simpleAppInfo.getString("color", "<missing>");

        // restore to how it was before app destroyed, if there's a shared preference
        if (b1 != false){
            checkRed.setChecked(true);
        }
        if (b2 != false){
            checkBlue.setChecked(true);
        }
        if (b3 != false){
            checkGreen.setChecked(true);
        }

        if (s3.equals("RED")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        else if (s3.equals("BLUE")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        else if (s3.equals("GREEN")) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        txtOne.setText(s1);
        edtOne.setText(s2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("a", "saveInstanceState");
        super.onSaveInstanceState(outState);
        // store necessary info into a bundle
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
        Log.i("a", "restoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        // retrieve everything from bundle during onRestoreInstanceState
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

