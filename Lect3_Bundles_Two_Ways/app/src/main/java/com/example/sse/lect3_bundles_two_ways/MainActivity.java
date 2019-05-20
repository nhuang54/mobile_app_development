package com.example.sse.lect3_bundles_two_ways;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private CheckBox cbHamburger;
    private CheckBox cbFrenchFries;
    private CheckBox cbOnionRings;
    private TextView tvTotal;
    private Button btnNext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cbHamburger = (CheckBox) findViewById(R.id.cbHamburger);
        cbFrenchFries = (CheckBox) findViewById(R.id.cbFrenchFries);
        cbOnionRings = (CheckBox) findViewById(R.id.cbOnionRings);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnNext = (Button) findViewById(R.id.btnNext);

//        if (savedInstanceState != null) {
//            String savedTotal;
//            savedTotal = savedInstanceState.getString("total");
//            tvTotal.setText(savedTotal);
//            // tvTotal.setText(savedInstanceState.getString("total"));  //or in one karate-chop...
//
//            cbHamburger.setChecked(savedInstanceState.getBoolean("hamburger"));
//            cbOnionRings.setChecked(savedInstanceState.getBoolean("onionrings"));
//            cbFrenchFries.setChecked(savedInstanceState.getBoolean("frenchfries"));
//        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class );
                i.putExtra("secretVal", "42");  //OPTIONAL for message passing between activities, basically just a hashmap, this bundle will be passed with intent.
                startActivity(i);
            }
        });
    }

    public void onCBClick(View v)      //Early binding used to share an event handler!
    {
//    v.findViewById(v.getId());  //we can actually identify the precise checkbox if we needed to.

        ((CheckBox) v).setText("Hello");
        Integer totalPrice = 0;
        if (cbHamburger.isChecked())
            totalPrice = totalPrice + 1;

        if (cbFrenchFries.isChecked())
            totalPrice = totalPrice + 1;

        if (cbOnionRings.isChecked())
            totalPrice = totalPrice + 1;

        String tvMessage = "Total: " + totalPrice.toString();

        tvTotal.setText(tvMessage);

//return totalPrice;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("hamburger", cbHamburger.isChecked());
        outState.putBoolean("frenchfries", cbFrenchFries.isChecked());
        outState.putBoolean("onionrings", cbOnionRings.isChecked());
        outState.putString("total", tvTotal.getText().toString());

        super.onSaveInstanceState(outState);  //QUESTION: why do we do this last?

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {   //QUESTIONS: Will this always be called, if not, why not?
        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState != null) {
            String savedTotal;
            savedTotal = savedInstanceState.getString("total");
            tvTotal.setText(savedTotal);
            // tvTotal.setText(savedInstanceState.getString("total"));  //or in one karate-chop...

            cbHamburger.setChecked(savedInstanceState.getBoolean("hamburger"));
            cbOnionRings.setChecked(savedInstanceState.getBoolean("onionrings"));
            cbFrenchFries.setChecked(savedInstanceState.getBoolean("frenchfries"));
//        }



    }

}
