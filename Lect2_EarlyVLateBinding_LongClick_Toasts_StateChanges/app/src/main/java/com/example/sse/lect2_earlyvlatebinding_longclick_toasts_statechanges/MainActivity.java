package com.example.sse.lect2_earlyvlatebinding_longclick_toasts_statechanges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtMsg;
    private Button btnClickMe;
    private TextView txtMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMsg = (EditText) findViewById(R.id.edtMsg);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        btnClickMe = (Button) findViewById(R.id.btnClickMe);

//        btnClickMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                edtMsg.setText("Hello Android");
//                txtMsg.setText("Hello Android");
//                Toast.makeText(getBaseContext(),"Hello Android",Toast.LENGTH_LONG).show();
//            }
//        });

btnClickMe.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        edtMsg.setText("Hello LongClick Listener");
        txtMsg.setText("Hello LongClick Listener");
        Toast.makeText(getBaseContext(),"Hello LongClick Listener",Toast.LENGTH_LONG).show();
        return true;
    }
});
    }

    public void btnClickMeOnClickEvent(View v){
        edtMsg.setText("Hello Early Binding");
        txtMsg.setText("Hello Early Binding");
        Toast.makeText(getBaseContext(),"Hello Early Binding",Toast.LENGTH_LONG).show();
    }

    public void Jerry(View v) {
        Toast.makeText(getBaseContext(),"Hello LATE Binding",Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        Log.i("Patriots","onStart Called.")
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}



