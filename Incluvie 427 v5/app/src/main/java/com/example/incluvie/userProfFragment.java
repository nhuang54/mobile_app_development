package com.example.incluvie;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class userProfFragment extends Fragment {


    EditText editName;
    EditText editEmail;
    EditText editBday;
    EditText editEthnicity;
    EditText editGender;

    FirebaseAuth mAuth;
    DatabaseReference mRef;

    private String email;
    private String name;
    private String gender;
    private String birthday;
    private String ethnicity;
    private String user;

    public userProfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_prof, container, false);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        user = mAuth.getCurrentUser().getEmail();

        SharedPreferences mProfile = getActivity().getSharedPreferences(user, Context.MODE_PRIVATE);

        editName = v.findViewById(R.id.editName);
        editEmail = v.findViewById(R.id.editEmail);
        editBday = v.findViewById(R.id.editBday);
        editEthnicity = v.findViewById(R.id.editEthnicity);
        editGender = v.findViewById(R.id.editGender);

        // check mProfile
        if (mProfile.contains("email")){
            email = mProfile.getString("email", "<missing>");
            name = mProfile.getString("name", "<missing>");
            birthday = mProfile.getString("birthday", "<missing>");
            ethnicity = mProfile.getString("ethnicity", "<missing>");
            gender = mProfile.getString("gender", "<missing>");

            editEmail.setText(email);
            editName.setText(name);
            editBday.setText(birthday);
            editEthnicity.setText(ethnicity);
            editGender.setText(gender);
        }
        else {
            updateFragFromDB();
        }
        return v;

    }
    private void updateFragFromDB(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    if(dataUser.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())){

                        email = dataUser.child("email").getValue().toString();
                        name = dataUser.child("name").getValue().toString();
                        birthday = dataUser.child("birthday").getValue().toString();
                        ethnicity = dataUser.child("ethnicity").getValue().toString();
                        gender = dataUser.child("gender").getValue().toString();

                        SharedPreferences mProfile = getActivity().getSharedPreferences(user, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mProfile.edit();

                        editor.putString("email", email);
                        editor.putString("name", name);
                        editor.putString("birthday", birthday);
                        editor.putString("ethnicity", ethnicity);
                        editor.putString("gender", gender);

                        editor.apply();

                        editEmail.setText(email);
                        editName.setText(name);
                        editBday.setText(birthday);
                        editEthnicity.setText(ethnicity);
                        editGender.setText(gender);


                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
