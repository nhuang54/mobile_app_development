package com.example.incluvie;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.bson.Document;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    public String plotString;
    private Button btnYtb;
    private Button btnPlt;
    private Button btnRate;
    private Button btnCmt;
    private String url;
    DatabaseReference mDatabaseReference;
    Bundle bundle;
    MoviePageFragment plotFrag;
    CommentsFragment commentFrag;
    RatingFragment rateFrag;
    LinearLayout fragmentContainer;
    FragmentManager fm;



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


        btnYtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrailerTask().execute(bundle.getString("movie"));
            }
        });



//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        plotFrag = new MoviePageFragment();
        commentFrag = new CommentsFragment();
        rateFrag = new RatingFragment();

        fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        //url = bundle.getString("url");
        ft.add(R.id.fragmentContainer, plotFrag);//now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("plotFrag");  //why do we do this?
        ft.commit ();
        getPoster();

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

    public void showRating(){

//        android.app.Fragment frag = new RatingFragment();
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();

        if (rateFrag == null)
            rateFrag = new RatingFragment();

        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.replace(R.id.fragmentContainer, rateFrag);
        ft.addToBackStack ("rateFrag");
        //ft.detach(f1);   //what would happen if f1, f2, or f3 were null?  how would we check and fix this?
        //ft.detach(f2);
        //ft.attach(f3);
        ft.commit();
    }
    public void showPlot(){

//        FragmentManager fm = getFragmentManager();
//        plotFrag.changePlot(plotString);
//        fm.beginTransaction().replace(R.id.fragmentContainer, plotFrag).commit();
        //Log.e("plotFrag", Boolean.toString(plotFrag.equals(null)));
        changePlot(plotString);

        if (plotFrag == null) {
            Log.e("plotFrag is null", "NULL");
            plotFrag = new MoviePageFragment();
            FragmentTransaction ft = fm.beginTransaction();  //Create a reference to a fragment transaction.
            ft.hide(rateFrag);
            ft.hide(commentFrag);
            ft.show(plotFrag);   //why does this not *always* crash?
            ft.commit();

        }
        else{
            Log.e("plotFrag is NOT NULL", "NONNULL");
            FragmentTransaction ft = fm.beginTransaction ();
            ft.replace(R.id.fragmentContainer, plotFrag);
            ft.addToBackStack ("plotFrag");  //why do we do this?
            Log.e("PlotString", plotString);
            getPoster();
            plotFrag.changePlot(plotString);
            ft.commit();

        }
    }
    public void showComment(){
//
//        android.app.Fragment frag = new CommentsFragment();
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();

        if (commentFrag == null)
            commentFrag = new CommentsFragment();

        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.replace(R.id.fragmentContainer, commentFrag);
        ft.addToBackStack ("commentFrag");  //why do we do this?
        ft.commit();

    }


    private void getPoster(){
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key;
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    key = dataUser.getKey();
                    Log.e("key", key + "==" + bundle.getString("movie"));
                    if(key.equals(bundle.getString("movie"))){
                        // mDatabaseReference.child(key).child("Poster").getKey();

                        if(dataUser.child("Poster").getValue() != null) {
                            url = dataUser.child("Poster").getValue().toString();
                            poster.setImageBitmap(loadBitmap(url));

                        }
                        else{url = null;}

                        title.setText(dataUser.child("Title").getValue().toString());

                  //      plot.setText(dataUser.child("Plot").toString());

                        plotString = dataUser.child("Plot").getValue().toString();
                        Log.e("tag512", plotString);

                        plotFrag.changePlot(plotString);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changePlot(String plot){
        plotFrag.changePlot(plot);
    }

    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        int IO_BUFFER_SIZE = 4 * 1024;
        try {
            URI uri = new URI(url);
            url = uri.toASCIIString();
            in = new BufferedInputStream(new URL(url).openStream(),
                    IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            int bytesRead;
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
                    options);
        } catch (IOException e) {
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
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


