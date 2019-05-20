package com.example.sse.interfragmentcommratingbar;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

//public class MainActivity extends AppCompatActivity {
    public class MainActivity extends Activity implements LeftRightFragment.LeftRightFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Honoring our promise to implement sendMessage from "implements ControlFragment.ControlFragmentListener" above.
    @Override
    public void sendMessage(String msg) {
        DrawableFragment receivingFragment = (DrawableFragment)getFragmentManager().findFragmentById(R.id.drawableFrag);
        receivingFragment.stringToBtn(msg);
    }



}