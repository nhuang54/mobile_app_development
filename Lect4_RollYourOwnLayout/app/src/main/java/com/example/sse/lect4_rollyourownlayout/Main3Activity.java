package com.example.sse.lect4_rollyourownlayout;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

//Lecture Notes (3): Dynamically creating ViewGroups and Views depending on Device Orientation.

public class Main3Activity extends AppCompatActivity {

    private LinearLayout LLMain;
    private LinearLayout.LayoutParams LLP;  //Ref: https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html
    Button [] Buttons;  //array of buttons.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//Render layout differently depending on screen orientation.
//
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LLMain = new LinearLayout(this);  //Q: Wny not Main2Activity.this??? A: __________
            LLMain.setOrientation(LinearLayout.VERTICAL);

            LLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT  );  //Q: Does this specify how the LinearLayout will be displayed? A: ______

            Buttons = new Button[5];   //Q: Can you dynamically create an array of views in the designer? A: _______________
            for(int i=0;i<Buttons.length;i++) {
                Buttons[i] = new Button(this);
                Buttons[i].setText("Button" + String.valueOf(i));
                Buttons[i].setTag(i);  //tag our button in case we want to distinguish later, eg, in an onClick event.
                Buttons[i].setLayoutParams(LLP); //Let the button know how it will be laid out.
                LLMain.addView(Buttons[i]);
            }
            this.addContentView(LLMain, LLP);   //Q: Wait a minute, I thought LLP was for the views, not the view group.  What's different this time? A: _______
        } else {  //landscape
            LLMain = new LinearLayout(this);  //Q: Wny not Main2Activity.this??? A: __________
            LLMain.setOrientation(LinearLayout.HORIZONTAL);  //CHANGE THE ORIENTATION OF THE VIEW GROUP.

            LLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT  );  //Q: Does this specify how the LinearLayout will be displayed? A: ______

            Buttons = new Button[3]; //ONLY 3 BUTTONS IN LANDSCAPE
            for(int i=0;i<Buttons.length;i++) {
                Buttons[i] = new Button(this);
                Buttons[i].setText("Button" + String.valueOf(i));
                Buttons[i].setTag(i);  //tag our button in case we want to distinguish later, eg, in an onClick event.
                Buttons[i].setLayoutParams(LLP); //Let the button know how it will be laid out.
                LLMain.addView(Buttons[i]);
            }
            this.addContentView(LLMain, LLP);   //Q: Wait a minute, I thought LLP was for the views, not the view group.  What's different this time? A: _______
        }



    }
}
