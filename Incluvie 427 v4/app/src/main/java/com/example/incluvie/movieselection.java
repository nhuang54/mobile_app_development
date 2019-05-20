package com.example.incluvie;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.app.Activity;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class movieselection extends Main2Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private RecyclerView.LayoutManager mLayoutManager3;

    private NavigationView navigationView;
    ListView listView1;
    frontPageAdapter fAdapter;

    DatabaseReference mDatabaseReference;

    private int SIZE = 8;
    private int semaphore;

    private String[] movieId1 = new String[SIZE];
    private String[] movieId2 = new String[SIZE];
    private String[] movieId3 = new String[SIZE];
    private String[] movieId4 = new String[SIZE];

    private String[] moviePoster1 = new String[SIZE];
    private String[] moviePoster2 = new String[SIZE];
    private String[] moviePoster3 = new String[SIZE];
    private String[] moviePoster4 = new String[SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView1 = findViewById(R.id.listView1);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Top Movies");

        DatabaseReference mDBChild1 = mDatabaseReference.child("Top Animated");
        DatabaseReference mDBChild2 = mDatabaseReference.child("Best Movies");
        DatabaseReference mDBChild3 = mDatabaseReference.child("Best Inclusive");
        DatabaseReference mDBChild4 = mDatabaseReference.child("Worst Diversity");

        mDBChild1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    movieId1[i] = dataUser.getKey().toString();
                    moviePoster1[i] = dataUser.child("Poster").getValue().toString();
                    i++;
                }
                semaphore++;
                if (semaphore == 4)
                    displayTopMovies();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mDBChild2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    movieId2[i] = dataUser.getKey().toString();
                    moviePoster2[i] = dataUser.child("Poster").getValue().toString();
                    i++;
                }
                semaphore++;
                if (semaphore == 4)
                    displayTopMovies();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mDBChild3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    movieId3[i] = dataUser.getKey().toString();
                    moviePoster3[i] = dataUser.child("Poster").getValue().toString();
                    i++;
                }
                semaphore++;
                if (semaphore == 4)
                    displayTopMovies();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mDBChild4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    movieId4[i] = dataUser.getKey().toString();
                    moviePoster4[i] = dataUser.child("Poster").getValue().toString();
                    i++;
                }
                semaphore++;
                if (semaphore == 4)
                    displayTopMovies();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void displayTopMovies(){
        String[][] displayTitle = {movieId1, movieId2, movieId3, movieId4};
        String[][] displayPoster = {moviePoster1, moviePoster2, moviePoster3, moviePoster4};
        String[] movieCategories = {"Best Animated Movies for Diversity", "Best Movies Overall", "Most Inclusive Movies", "Worst Movies for Diversity"};

        fAdapter = new frontPageAdapter(movieCategories, displayPoster, displayTitle,getBaseContext());
        listView1.setAdapter(fAdapter);
    }

}

