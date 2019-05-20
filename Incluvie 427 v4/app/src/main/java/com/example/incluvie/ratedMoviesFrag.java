package com.example.incluvie;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class ratedMoviesFrag extends Fragment {

    private String id;
    private ListView ratedList;

    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String[] ratingList2;
    String[] movieList2;
    CommentsAdapter cAdapter;
    String user;

    public ratedMoviesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.ratedmoviesfrag, container, false);
        Log.e("we are here", "here1");
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ratedList = v.findViewById(R.id.ratedList);

        updateFrag();
        return v;
    }

    public void updateFrag(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> ratingList = new ArrayList<String>();
                ArrayList<String> movieList = new ArrayList<String>();

                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    if(dataUser.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())){
                        Iterator iterator2 = dataUser.child("Ratings").getChildren().iterator();
                        while(iterator2.hasNext()){
                            DataSnapshot dataUser2 = (DataSnapshot) iterator2.next();

                            String name2 = dataUser2.getKey();


                            movieList.add(name2 + ":");
                            ratingList.add(dataUser2.getValue().toString());
                        }

                    }
                }
                ratingList2 = new String[ratingList.size()];
                movieList2 = new String[movieList.size()];

                ratingList.toArray(ratingList2);
                Log.e("size1", Integer.toString(ratingList2.length));


                movieList.toArray(movieList2);


                cAdapter = new CommentsAdapter(movieList2, ratingList2, getActivity());
                ratedList.setAdapter(cAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getId(String input){
        id = input;
    }

}
