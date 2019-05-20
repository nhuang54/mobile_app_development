package com.example.oauth_login;

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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class Main2Activity extends AppCompatActivity {

    private TextView title;
    private TextView plot;
    private ImageView poster;
    private Button btnApi;
    private Button btnData;
    private Button btnLocat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = (TextView) findViewById(R.id.title);
        plot = (TextView) findViewById(R.id.plot);
        poster = (ImageView) findViewById(R.id.poster);
        btnApi = (Button) findViewById(R.id.btnApi);
        btnData = (Button) findViewById(R.id.btnData);
        btnLocat = (Button) findViewById(R.id.btnLocat);

        btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MovieTitleTask().execute();
                new MoviePlotTask().execute();
                new MoviePosterTask().execute();
            }
        });
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MongoClientURI uri  = new MongoClientURI("mongodb://dan:dan@ds131258.mlab.com:31258/incluvie");
                    MongoClient client = new MongoClient(uri);
                    MongoDatabase db = client.getDatabase(uri.getDatabase());
                    MongoCollection<Document> coll = db.getCollection("Movies");

                    Document myDoc = coll.find(Filters.eq("Title", "Captain America: Civil War")).first();
                    Log.e("lol",myDoc.toJson().toString());

                    String title1 = myDoc.get("Title").toString();
                    Log.e("lol", title1);
                    title.setText(title1);
                    plot.setText(myDoc.get("Plot").toString());
                    poster.setImageBitmap(loadBitmap(myDoc.get("Poster").toString()));

                    client.close();


                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    class MovieTitleTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {
            title.setText("");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                TmdbMovies movies = new TmdbApi("da085d2e66866589082d29207e43770c").getMovies();
                MovieDb movie = movies.getMovie(284054, "en");
                String x = movie.getOriginalTitle();
                return x;
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "no title";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if(response == null){
                response = "THERE WAS AN ERROR";
            }
            title.setText(response);
        }
    }


    class MoviePlotTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected void onPreExecute() {
            plot.setText("");
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
            plot.setText(response);

        }
    }

    class MoviePosterTask extends AsyncTask<Void, Void, String> {
        private Exception exception;


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
            new MoviePoster().execute(url);

        }
    }

    class MoviePoster extends AsyncTask<String, Void, Bitmap> {
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
            poster.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }

}
