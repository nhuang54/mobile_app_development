package com.example.incluvie;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import info.debatty.java.stringsimilarity.*;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


public class SearchActivity extends Main2Activity{

    private ImageView poster;
    private TextView title;
    private TextView plot;
    private ListView movieList;
    private String input;
    public String[] listFinal;
    public String[] idListFinal;
    public ListAdapter mAdapter;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Movies");

        movieList = (ListView) findViewById(R.id.movieTitles);
        Bundle bundle = getIntent().getExtras();
        input = bundle.getString("Title");

        listFinal = new String[] {"", "", "", "", ""};

        final ArrayAdapter movieTitleAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listFinal);

        movieList.setAdapter(movieTitleAdapter);

        final Damerau d = new Damerau();

        final ArrayList<String> titleArray = new ArrayList<String>();
        final ArrayList<Double> scoreArray = new ArrayList<Double>();
        final ArrayList<String> idArray= new ArrayList<String>();



        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                double diff;
                String title;
                String year;
                String id;

                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();

                    title = dataUser.child("Title").getValue().toString();
                    year = dataUser.child("Year").getValue().toString();
                    id = dataUser.getKey().toString();
                    diff = d.distance(title.toLowerCase(), input.toLowerCase());

                    if (title.toLowerCase().contains(input.toLowerCase()) || diff < 3.0) {
                        title = title + " (" + year + ") ";

                        titleArray.add(title);
                        scoreArray.add(diff);
                        idArray.add(id);

                        movieTitleAdapter.notifyDataSetChanged();
                    }
                }
                String[] idArray2 = new String[idArray.size()];
                String[] titleArray2 = new String[titleArray.size()];
                double[] scoreArray2 = new double[scoreArray.size()];
                // loop for finding the next lowest score from the old list and store it into the new list
                for (int j = 0; j < titleArray.size(); j++) {
                    double currentMin = 1000.0;
                    int index = 0;
                    for (int i = 0; i < titleArray.size(); i++) {
                        if (scoreArray.get(i) < currentMin) {
                            currentMin = scoreArray.get(i);
                            index = i;
                        }
                    }
                    scoreArray2[j] = scoreArray.get(index);
                    titleArray2[j] = titleArray.get(index);
                    idArray2[j] = idArray.get(index);
                    scoreArray.set(index, 50.0);
                }

                listFinal = new String[titleArray.size()];
                listFinal = titleArray2;

                idListFinal = new String[titleArray.size()];
                idListFinal = idArray2;

                if (listFinal.length == 0){ // check to see if there are results from database
                    //Log.e("list", "its empty");
                    // invoke this method to pull from API
                    new NoResults().execute(input);
                }
                else{
                    ArrayAdapter movieTitleAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listFinal);

                    movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), movie_page.class);
                            i.putExtra("movie", idListFinal[position]);
                            startActivity(i);
                        }
                    });
                    movieList.setAdapter(movieTitleAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    class NoResults extends AsyncTask<String, Void, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            String apiInput = strings[0];
            ArrayList<String> titles = new ArrayList<String>();
            ArrayList<String> idList = new ArrayList<String>();


            TmdbApi api = new TmdbApi("da085d2e66866589082d29207e43770c");
            TmdbMovies movies = api.getMovies();
            TmdbSearch y = api.getSearch();
            MovieResultsPage searchResults = y.searchMovie(apiInput, 0, "en", false, 0);

            List<MovieDb> movieResults = searchResults.getResults();
            int j = searchResults.getTotalResults();

            if (j > 20){
                j = 20;
            }
            String movieTitle;
            String moviePlot;
            String moviePoster;
            String movieYear;
            String movieId;

            for (int i = 0; i < j; i++) {
                // get data from the API
                movieTitle = movieResults.get(i).getTitle();
                moviePlot = movieResults.get(i).getOverview();
                moviePoster = movieResults.get(i).getPosterPath();
                movieYear = movieResults.get(i).getReleaseDate();
                if (!movieYear.equals("")){
                    movieYear = movieYear.substring(0,4);
                }
                movieId = Integer.toString(movieResults.get(i).getId());

                mDatabaseReference.child(movieId).child("Title").setValue(movieTitle);
                mDatabaseReference.child(movieId).child("Year").setValue(movieYear);
                if (moviePlot != null){
                    mDatabaseReference.child(movieId).child("Plot").setValue(moviePlot);
                }
                else{mDatabaseReference.child(movieId).child("Plot").setValue(null);}

                if (moviePoster != null) {
                    mDatabaseReference.child(movieId).child("Poster").setValue("https://image.tmdb.org/t/p/w300" + moviePoster);
                }
                else{mDatabaseReference.child(movieId).child("Poster").setValue(null);}

                titles.add(movieId);
            }
            return titles;
        }

        @Override
        protected void onPostExecute(final List<String> strings) {
            final int size = strings.size();
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final String[] idList = new String[size];
                    listFinal = new String[size];
                    String title;
                    String year;

                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot dataUser = (DataSnapshot) iterator.next();
                        for (int i = 0; i < size; i++) {
                            //Log.e("DB_ID", dataUser.getKey());
                            //Log.e("LIST_ID", strings.get(i));

                            if (strings.get(i).equals(dataUser.getKey())){
                                idList[i] = strings.get(i);
                                title = dataUser.child("Title").getValue().toString();
                                year = dataUser.child("Year").getValue().toString();
                                listFinal[i] = title + " (" + year + ")";
                            }
                        }
                    }
                    ArrayAdapter movieTitleAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listFinal);

                    movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), movie_page.class);
                            i.putExtra("movie", idList[position]);
                            startActivity(i);
                        }
                    });
                    movieList.setAdapter(movieTitleAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            super.onPostExecute(strings);
        }
    }
}
