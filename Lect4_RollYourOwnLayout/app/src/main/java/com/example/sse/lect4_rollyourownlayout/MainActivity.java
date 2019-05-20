package com.example.sse.lect4_rollyourownlayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

//Lecture Notes (1): Dynamically adding items to an existing layout.

public class MainActivity extends AppCompatActivity {

    private RelativeLayout RLMain;
    private RelativeLayout.LayoutParams RLP;  //Ref: https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html
    private Button btnOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //This looks important, what does it do?

//1: let's create our button...
//        btnOne = (Button) findViewById(R.id.btnOne);   //This usually works, why can't it find R.id.btnone?
        btnOne = new Button(MainActivity.this);  //Let's create it ourselves...
        btnOne.setText("Button One");
        btnOne.setBackgroundColor(Color.CYAN);

//2: Let's find our button a view group.
        RLMain = (RelativeLayout) findViewById(R.id.RLMain);  //This one exists, and was already "inflated."  (Second statement in onCreate)
        RLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);  //IMPORTANT: Layout Params are for the views, not the view group.
        btnOne.setLayoutParams(RLP);
        btnOne.setGravity(Gravity.CENTER_VERTICAL);


//3. Let's put our button in it's new view group.
        RLMain.addView(btnOne);  //Let's

//Misc
btnOne.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Intent i = new Intent(this, Main2Activity.class );  //Q: Why is 'this' parm insufficient here?  A: ___________
        Intent i = new Intent(MainActivity.this, Main2Activity.class );
//        Intent i = new Intent(getApplicationContext(), Main2Activity.class );
        startActivity(i);

        Toast.makeText(MainActivity.this, "Hi guys...", Toast.LENGTH_LONG).show();
    }
});

    }
}
