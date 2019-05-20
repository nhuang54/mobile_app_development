package com.example.problem2_25;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnCheck;
    private EditText txtPass1;
    private EditText txtPass2;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheck = (Button) findViewById(R.id.btnCheck);
        txtPass1 = (EditText) findViewById(R.id.txtPass1);
        txtPass2 = (EditText) findViewById(R.id.txtPass2);
        txtResult = (TextView) findViewById(R.id.txtResult);


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String pass1 = txtPass1.getText().toString();
                //String pass2 = txtPass2.getText().toString();

                if (txtPass1.getText().toString().equals(txtPass2.getText().toString())){
                    txtResult.setText("THANK YOU");
                } else {
                    txtResult.setText("PASSWORDS MUST MATCH");
                }
            }
        });
    }
}
