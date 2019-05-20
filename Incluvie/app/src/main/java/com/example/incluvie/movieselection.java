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
import android.view.View;
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movieselection);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //inflate header layout
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recycler3);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView3.setLayoutManager(mLayoutManager3);

        // specify an adapter (see also next example)
        MongoClientURI uri  = new MongoClientURI("mongodb://dan:dan@ds131258.mlab.com:31258/incluvie");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> coll = db.getCollection("Movies");

        String[] movieTitle = {"Coraline","Captain America: Civil War", "Clue", "Cast Away"};
        String[] myDataset = new String[movieTitle.length];
//        String[] myDataset = {"https://ia.media-imdb.com/images/M/MV5BMzQxNjM5NzkxNV5BMl5BanBnXkFtZTcwMzg5NDMwMg@@._V1_SX300.jpg",
//                "https://ia.media-imdb.com/images/M/MV5BMzQxNjM5NzkxNV5BMl5BanBnXkFtZTcwMzg5NDMwMg@@._V1_SX300.jpg",
//                "https://ia.media-imdb.com/images/M/MV5BMzQxNjM5NzkxNV5BMl5BanBnXkFtZTcwMzg5NDMwMg@@._V1_SX300.jpg",
//                "https://ia.media-imdb.com/images/M/MV5BMzQxNjM5NzkxNV5BMl5BanBnXkFtZTcwMzg5NDMwMg@@._V1_SX300.jpg"};

        for(int i=0;i<myDataset.length; i++){
            Document myDoc = coll.find(eq("Title", movieTitle[i])).first();
            myDataset[i] = myDoc.getString("Poster").toString();
        }


        client.close();


        mAdapter = new MyAdapter(myDataset, movieTitle);
        mRecyclerView.setAdapter(mAdapter);
        recyclerView2.setAdapter(mAdapter);
        recyclerView3.setAdapter(mAdapter);


    }
}

