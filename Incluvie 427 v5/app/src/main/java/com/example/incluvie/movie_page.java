package com.example.incluvie;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.bson.Document;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by nathanwu on 4/12/18.
 */

public class movie_page extends Main2Activity {

    private ImageView poster;
    private TextView title;
    private String plotString;
    private String titleString;
    private Button btnYtb;
    private Button btnPlt;
    private Button btnRate;
    private Button btnCmt;
    private String url;
    private TextView label1;
    private TextView label2;
    private TextView label3;
    long total;
    int count;
    DatabaseReference mDatabaseReference;
    Bundle bundle;
    MoviePageFragment plotFrag;
    CommentsFragment commentFrag;
    RatingFragment rateFrag;
    LinearLayout fragmentContainer;
    FragmentManager fm;
    RatingBar rating;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviepage);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Movies");

        bundle = getIntent().getExtras();

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        btnPlt = findViewById(R.id.btnPlt);
        btnYtb = findViewById(R.id.btnYtb);
        btnRate = findViewById(R.id.btnRate);
        btnCmt = findViewById(R.id.btnCmt);
        label1 = findViewById(R.id.lbl1);
        label2 = findViewById(R.id.lbl2);
        label3 = findViewById(R.id.lbl3);
        rating = findViewById(R.id.rating);

        btnYtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrailerTask().execute(bundle.getString("movie"));
            }
        });

        plotFrag = new MoviePageFragment();
        commentFrag = new CommentsFragment();
        rateFrag = new RatingFragment();

        fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragmentContainer, plotFrag);//now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("plotFrag");  //why do we do this?
        ft.commit();
        fm.executePendingTransactions();

        btnPlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlot();
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating();
            }
        });

        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComment();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences mData = getSharedPreferences(bundle.getString("movie"), Context.MODE_PRIVATE);

        if (mData.contains("Title") && mData.contains("Plot") && mData.contains("Poster")){
            titleString = mData.getString("Title", "<missing>");
            plotString = mData.getString("Plot", "<missing>");
            url = mData.getString("Poster", "<missing>");

            Picasso.get().load(url).into(poster);
            title.setText(titleString);
            plotFrag.changePlot(plotString);
        }
        else {
            url = bundle.getString("url");
            // check to see if theres a poster before getting from database
            if (url != null) {
                Picasso.get().load(url).into(poster);
                getPoster(false);
            } else
                getPoster(true);
        }

        setRating();
        setLabels();
    }

    public void onBackPressed(){
        this.finish();
    }

    public void setRating(){
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String key;
                    total = 0;
                    count = 0;
                    Iterator iterator = dataSnapshot.child(bundle.getString("movie")).child("Ratings").child("Scale").getChildren().iterator();
                    while(iterator.hasNext()){
                        DataSnapshot dataUser = (DataSnapshot) iterator.next();
                        key = dataUser.getKey().toString();
                        total += (long) dataUser.getValue();
                        count++;
                        }
                        if(count>0) {
                            int average = Math.round((total / count) + 5);
                            rating.setRating(average);
                        }
                    }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
    }

    public void setLabels(){
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key;
                ArrayList<String> nameList1 = new ArrayList<String>();
                ArrayList<String> totalList1 = new ArrayList<String>();

                Iterator iterator = dataSnapshot.child(bundle.getString("movie")).child("Ratings").child("Labels").getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    key = dataUser.getKey().toString();
                    nameList1.add(key);
                    totalList1.add(dataUser.getValue().toString());
                }
                ArrayList<String> nameList2 = new ArrayList<String>(Collections.nCopies(8, "0"));

                for (int j = 0; j < nameList1.size(); j++) {
                    int currentMin = 0;
                    int index = 0;
                    for (int i = 0; i < nameList1.size(); i++) {
                        int score = Integer.parseInt(totalList1.get(i));
                        if (score > currentMin) {
                            currentMin = score;
                            index = i;
                        }
                    }
                    nameList2.set(j,nameList1.get(index));
                    totalList1.set(index, "0");
                }
                if(nameList2.get(0) == "0"){
                    nameList2.set(0,"Not Rated Yet");
                }
                if(nameList2.get(1) == "0"){
                    nameList2.set(1,"Not Rated Yet");
                }
                if(nameList2.get(2) == "0"){
                    nameList2.set(2, "Not Rated Yet");
                }
                label1.setText(nameList2.get(0));
                label2.setText(nameList2.get(1));
                label3.setText(nameList2.get(2));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void showRating(){

        rateFrag.getId(bundle.getString("movie"));

        FragmentTransaction ft = fm.beginTransaction ();
        if (rateFrag.isAdded()) { // if the fragment is already in container
            ft.show(rateFrag);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragmentContainer, rateFrag, "rateFrag");
        }
        // Hide fragment B
        if (plotFrag.isAdded()) {
            ft.hide(plotFrag);
        }
        // Hide fragment C
        if (commentFrag.isAdded()) {
            ft.hide(commentFrag);
        }
        // Commit changes
        ft.commit();
    }

    public void showPlot(){
        changePlot(plotString);
        FragmentTransaction ft = fm.beginTransaction ();
        if (plotFrag.isAdded()) { // if the fragment is already in container
            ft.show(plotFrag);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragmentContainer, plotFrag, "plotFrag");
        }
        // Hide fragment B
        if (rateFrag.isAdded()) {
            ft.hide(rateFrag);
        }
        // Hide fragment C
        if (commentFrag.isAdded()) {
            ft.hide(commentFrag);
        }
        // Commit changes
        ft.commit();
    }

    public void showComment(){

        commentFrag.getId(bundle.getString("movie"));

        FragmentTransaction ft = fm.beginTransaction ();
        if (commentFrag.isAdded()) { // if the fragment is already in container
            ft.show(commentFrag);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragmentContainer, commentFrag, "commentFrag");
        }
        // Hide fragment B
        if (rateFrag.isAdded()) {
            ft.hide(rateFrag);
        }
        // Hide fragment C
        if (plotFrag.isAdded()) {
            ft.hide(plotFrag);
        }
        // Commit changes
        ft.commit();
    }

    private void getPoster(final boolean getAll){
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key;
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    key = dataUser.getKey();
                    if(key.equals(bundle.getString("movie"))){
                        if (getAll) {
                            if (dataUser.child("Poster").getValue() != null) {
                                url = dataUser.child("Poster").getValue().toString();
                                Picasso.get().load(url).into(poster);
                            } else
                                url = null;

                            SharedPreferences mData = getSharedPreferences(key, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mData.edit();

                            titleString = dataUser.child("Title").getValue().toString();
                            plotString = dataUser.child("Plot").getValue().toString();

                            editor.putString("Title", titleString);
                            editor.putString("Plot", plotString);
                            editor.putString("Poster", url);

                            editor.apply();

                            title.setText(titleString);
                            plotFrag.changePlot(plotString);
                            break;
                        }
                        else {
                            SharedPreferences mData = getSharedPreferences(key, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mData.edit();

                            titleString = dataUser.child("Title").getValue().toString();
                            plotString = dataUser.child("Plot").getValue().toString();

                            editor.putString("Title", titleString);
                            editor.putString("Plot", plotString);
                            editor.putString("Poster", url);

                            editor.apply();

                            title.setText(titleString);
                            plotFrag.changePlot(plotString);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void changePlot(String plot){
        plotFrag.changePlot(plot);
    }

    class TrailerTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            TmdbApi x = new TmdbApi("da085d2e66866589082d29207e43770c");
            TmdbMovies movies = x.getMovies();
            MovieDb movie = movies.getMovie(Integer.parseInt(strings[0]),"en", TmdbMovies.MovieMethod.videos);
            List<Video> list = movie.getVideos();
            String e = list.get(0).getKey().toString();
            return e;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String t_url = "http://www.youtube.com/watch?v=" + s;
            Log.e("youtube", t_url);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(t_url));
            startActivity(i);
        }


    }

}


