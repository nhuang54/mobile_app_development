package com.example.incluvie;

import android.app.Activity;
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
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    private TextView plot;
    private Button btnYtb;
    private Button btnPlt;
    private String url;


    public MoviePageInterface MPI;  //Future reference to an object that implements LeftRightFragmentListener

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moviepage);

//
//        if (android.os.Build.VERSION.SDK_INT > 9)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        final Bundle bundle = getIntent().getExtras();

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        plot = findViewById(R.id.plot);
        btnPlt = findViewById(R.id.btnPlt);
        btnYtb = findViewById(R.id.btnYtb);

        btnYtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrailerTask().execute(bundle.getString("movie"));
            }
        });



        MongoClientURI uri  = new MongoClientURI("mongodb://dan:dan@ds131258.mlab.com:31258/incluvie");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> coll = db.getCollection("Movies");

        url = bundle.getString("url");



        final Document myDoc = coll.find(eq("Title", bundle.getString("movie"))).first();

        if (url == null){
            // error here for display movies that are pulled from the api
            url = myDoc.getString("Poster");
        }

        //plot.setText(myDoc.getString("Plot"));
        title.setText(myDoc.getString("Title"));
        poster.setImageBitmap(loadBitmap(url));

        MoviePageFragment plotFragment = (MoviePageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        plotFragment.changePlot(myDoc.getString("Plot"));
//        btnPlt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MoviePageFragment plotFragment = (MoviePageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
//
//                plotFragment.changePlot(myDoc.getString("Plot"));
//            }
//        });


        Log.e("lol", bundle.getString("movie"));

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
            TmdbSearch y = x.getSearch();
            MovieResultsPage z = y.searchMovie(strings[0], 0, "en", true, 0);
            List<MovieDb> a = z.getResults();

            int d = a.get(0).getId();


            MovieDb movie = movies.getMovie(d,"en", TmdbMovies.MovieMethod.videos);
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


