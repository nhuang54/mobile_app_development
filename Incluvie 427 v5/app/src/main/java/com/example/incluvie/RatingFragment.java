package com.example.incluvie;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class RatingFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    public SeekBar rate;
    public Button btnLiberal;
    public Button btnWoke;
    public Button btnFeminist;
    public Button btnDiverse;
    public Button btnMisogynist;
    public Button btnRacist;
    public Button btnInclusive;
    public Button btnEducational;
    public Button btnSubmit;

    boolean liberal = false;
    boolean woke = false;
    boolean feminist = false;
    boolean diverse = false;
    boolean misogynist = false;
    boolean racist = false;
    boolean inclusive = false;
    boolean educational = false;
    DatabaseReference mRef;
    DatabaseReference mRef2;
    FirebaseAuth mAuth;
    public String id;

    String user;
    String email;

    int seekValue;

    int[] labels = new int[8];

    String[] labelNames= {"Liberal", "Woke", "Feminist", "Diverse", "Misogynist", "Racist", "Inclusive", "Educational"};


    public RatingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();


        mRef = FirebaseDatabase.getInstance().getReference().child("Movies").child(id).child("Ratings");

        mRef2 = FirebaseDatabase.getInstance().getReference().child("Users");

        mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    if(dataUser.child("email").getValue().toString().equals(email)){
                        user = dataUser.getKey().toString();
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        btnLiberal = v.findViewById(R.id.btnLiberal);
        btnWoke =  v.findViewById(R.id.btnWoke);
        btnFeminist =  v.findViewById(R.id.btnFeminist);
        btnDiverse = v.findViewById(R.id.btnDiverse);
        btnMisogynist = v.findViewById(R.id.btnMisogynist);
        btnRacist =  v.findViewById(R.id.btnRacist);
        btnInclusive =  v.findViewById(R.id.btnInclusive);
        btnEducational =  v.findViewById(R.id.btnEducational);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        rate = v.findViewById(R.id.seekBar);
        Log.e("hello", "we are here");

        btnLiberal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(v);
            }
        });
        btnWoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnFeminist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnDiverse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnRacist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnMisogynist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnInclusive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnEducational.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeButton(v);
                    }
                });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changeButton(v);
                            }
                        });



        rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekValue = progress - 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        for (int i = 0; i < 8; i++){
            labels[i] = 0;
        }

        return v;
    }
    public void getId(String input) {

        id = input;

    }

    private void changeButton(View view){

        Log.e("scale", Boolean.toString(mRef.child("Scale").child(mAuth.getCurrentUser().getUid()) == null));


        if (view.getId() == R.id.btnLiberal){
            if(labels[0] == 0){labels[0] = 1;}
            else if(labels[0] ==1){labels[0] = 0;}
        }
        else if(view.getId() == R.id.btnWoke){
            if(labels[1] == 0){labels[1] = 1;}
            else if(labels[1] ==1){labels[1] = 0;}
        }
        else if(view.getId() == R.id.btnFeminist){
            if(labels[2] == 0){labels[2] = 1;}
            else if(labels[2] ==1){labels[2] = 0;}
        }
        else if(view.getId() == R.id.btnDiverse){
            if(labels[3] == 0){labels[3] = 1;}
            else if(labels[3] ==1){labels[3] = 0;}
        }
        else if(view.getId() == R.id.btnMisogynist){
            if(labels[4] == 0){labels[4] = 1;}
            else if(labels[4] ==1){labels[4] = 0;}
        }
        else if(view.getId() == R.id.btnRacist){
            if(labels[5] == 0){labels[5] = 1;}
            else if(labels[5] ==1){labels[5] = 0;}
        }
        else if(view.getId() == R.id.btnInclusive){
            if(labels[6] == 0){labels[6] = 1;}
            else if(labels[6] ==1){labels[6] = 0;}
        }
        else if(view.getId() == R.id.btnEducational){
            if(labels[7] == 0){labels[7] = 1;}
            else if(labels[7] ==1){labels[7] = 0;}
        }
        else if(view.getId() == R.id.btnSubmit){
            Log.e("hello", "we are here");

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("scale2", Boolean.toString(mRef.child("Scale").child(mAuth.getCurrentUser().getUid()).equals(null)));
                    if(mRef.child("Scale").child(mAuth.getCurrentUser().getUid()) == null)
                    {

                        for (int i = 0; i < 8; i++) {
                            if (labels[i] == 1) {
                                Log.e("label", labelNames[i]);
                                if (dataSnapshot.child("Labels").child(labelNames[i]).getValue() == null) {
                                    mRef.child("Labels").child(labelNames[i]).setValue(1);
                                } else {
                                    long original = (long) dataSnapshot.child("Labels").child(labelNames[i]).getValue();
                                    int intOriginal = (int) original;
                                    intOriginal++;
                                    mRef.child("Labels").child(labelNames[i]).setValue(intOriginal);
                                }
                            }
                        }
                    }

                    mRef.child("Scale").child(mAuth.getCurrentUser().getUid()).setValue(seekValue);
                    mRef2.child(user).child("Ratings").child(id).setValue(seekValue);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
