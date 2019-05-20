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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public ListAdapter mAdapter;
    DatabaseReference mDatabaseReference;

//    ArrayList<String> listArray = new ArrayList<String>();
//    DatabaseReference mDatabaseReference;



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


        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double diff;
                String title;
                String year;
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();

                    title = dataUser.child("Title").getValue().toString();
                    year = dataUser.child("Year").getValue().toString();
                    diff = d.distance(title, input.toLowerCase());

                    if (title.toLowerCase().contains(input.toLowerCase()) || diff < 3.0) {
                        title = title + " (" + year + ")";
                        titleArray.add(title);
                        scoreArray.add(diff);
                        movieTitleAdapter.notifyDataSetChanged();
                    }
                }
                String[] titleArray2 = new String[titleArray.size()];
                double[] scoreArray2 = new double[scoreArray.size()];
                // loop for finding the next highest score from the old list and store it into the new list
                for (int j = 0; j < titleArray.size(); j++) {
                    double currentMin = 10.0;
                    int index = 0;
                    for (int i = 0; i < titleArray.size(); i++) {
                        if (scoreArray.get(i) < currentMin) {
                            currentMin = scoreArray.get(i);
                            index = i;
                        }
                    }
                    scoreArray2[j] = scoreArray.get(index);
                    titleArray2[j] = titleArray.get(index);
                    scoreArray.set(index, 50.0);
                }

                listFinal = new String[titleArray.size()];
                listFinal = titleArray2;

                //Log.e("list", Arrays.toString(listFinal));

                if (listFinal.length == 0) {
                    Log.e("list", "its empty");
                    new NoResults().execute(input);
                } else {
                    ArrayAdapter movieTitleAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listFinal);

                    movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), movie_page.class);
                            i.putExtra("movie", listFinal[position]);
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

            TmdbApi api = new TmdbApi("da085d2e66866589082d29207e43770c");
            TmdbMovies movies = api.getMovies();
            TmdbSearch y = api.getSearch();
            MovieResultsPage searchResults = y.searchMovie(apiInput, 0, "en", false, 0);

            List<MovieDb> movieList = searchResults.getResults();
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
                // get all required info from API
                movieId = Integer.toString(movieList.get(i).getId());
                movieTitle = movieList.get(i).getTitle();
                moviePlot = movieList.get(i).getOverview();
                movieYear = movieList.get(i).getReleaseDate().substring(0, 4);
                moviePoster = movieList.get(i).getPosterPath();
                // Store in Firebase DB
                mDatabaseReference.child(movieId).child("Title").setValue(movieTitle);
                mDatabaseReference.child(movieId).child("Plot").setValue(moviePlot);
                mDatabaseReference.child(movieId).child("Year").setValue(movieYear);
                mDatabaseReference.child(movieId).child("Poster").setValue(moviePoster);

                movieTitle = movieTitle + " (" + movieYear + ")";

                titles.add(movieTitle);
            }

            return titles;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            int size = strings.size();
            String idList[] = new String[size];
            listFinal = new String[size];

            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot dataUser = (DataSnapshot) iterator.next();

                        //title = dataUser.child("Title").getValue().toString();
                        //year = dataUser.child("Year").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            for (int i = 0; i < size; i++) {
                idList[i] = strings.get(i);

            }

            ArrayAdapter movieTitleAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listFinal);

            movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(), movie_page.class);
                    i.putExtra("movie", listFinal[position]);
                    startActivity(i);
                }
            });
            movieList.setAdapter(movieTitleAdapter);
            super.onPostExecute(strings);
        }
    }
}
