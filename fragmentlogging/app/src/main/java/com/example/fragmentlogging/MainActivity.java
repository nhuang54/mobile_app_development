package com.example.fragmentlogging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String MyFlag = "parkingmiracle";  //this will be our trail of breadcrumbs for logging events.
    private static int eventCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onPause");
        super.onPause();
    }


    @Override
    protected void onStart() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onStart");
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Activity onRestoreInstanceState");
        super.onResume();
    }


    //Handy Helpers...
    public String intToStr(Integer i)
    {
        return i.toString();
    }

    public int strToInt(String S)
    {
        return Integer.parseInt(S);
    }


}
