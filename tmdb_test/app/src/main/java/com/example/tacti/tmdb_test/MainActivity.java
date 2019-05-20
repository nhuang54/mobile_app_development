package com.example.tacti.tmdb_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class MainActivity extends Main2Activity {

    private TextView txtOne;
    private TextView txtTwo;
    private Button btnOne;
    private Button btnSwitch;
    private ImageView imgOne;
    static final String API_KEY = "da085d2e66866589082d29207e43770c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOne = (TextView) findViewById(R.id.txtOne);
        txtTwo = (TextView) findViewById(R.id.txtTwo);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        imgOne = (ImageView) findViewById(R.id.imgOne);
        

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
                new MovieTitleTask().execute();
                new MovieDescriptTask().execute();
            }
        });

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(i);
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {
            txtOne.setText("");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                  TmdbMovies movies = new TmdbApi("da085d2e66866589082d29207e43770c").getMovies();
                  MovieDb movie = movies.getMovie(284054, "en");
                  String x = movie.getPosterPath();
                  return x;
            }
            catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            //txtOne.setText(response);
            String url = "http://image.tmdb.org/t/p/w342" + response;
            new RetrieveImageTask().execute(url);

        }
    }

    class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (IOException e) {
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgOne.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }


    class MovieTitleTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {
            txtOne.setText("");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                TmdbMovies movies = new TmdbApi("da085d2e66866589082d29207e43770c").getMovies();
                MovieDb movie = movies.getMovie(284054, "en");
                String x = movie.getOriginalTitle();
                return x;
            }
            catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            txtOne.setText(response);
        }
    }


    class MovieDescriptTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {
            txtTwo.setText("");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                TmdbMovies movies = new TmdbApi("da085d2e66866589082d29207e43770c").getMovies();
                MovieDb movie = movies.getMovie(284054, "en");
                String x = movie.getOverview();
                return x;
            }
            catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            txtTwo.setText(response);

        }
    }

}
