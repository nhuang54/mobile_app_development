package com.example.incluvie;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class frontPageAdapter extends BaseAdapter {

    private
    String poster[][];             //Keeping it simple.  Using Parallel arrays is the introductory way to store the List data.
    String movieTitle[][];  //the "better" way is to encapsulate the list items into an object, then create an arraylist of objects.
    MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;


    String[] titleList;
    TextView txtTitle;
    Context context;   //Creating a reference to our context object, so we only have to get it once.  Context enables access to application specific resources.
    // Eg, spawning & receiving intents, locating the various managers.

    //STEP 2: Override the Constructor, be sure to:
    // grab the context, the callback gets it as a parm.
    // load the strings and images into object references.
    public frontPageAdapter(String[] inputTitle, String[][] input1, String[][] input2, Context input3) {
        poster = input1;
        movieTitle = input2;
        context = input3;
        titleList = inputTitle;
    }

    //STEP 3: Override and implement getCount(..), ListView uses this to determine how many rows to render.
    @Override
    public int getCount() {
//        return episodes.size(); //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        return poster.length;   //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        //Q: How else could we have done this (better)? ________________
    }

    //STEP 4: Override getItem/getItemId, we aren't using these, but we must override anyway.
    @Override
    public Object getItem(int position) {
//        return episodes.get(position);  //In Case you want to use an ArrayList
        return null;        //really should be returning entire set of row data, but it's up to us, and we aren't using this call.
    }

    @Override
    public long getItemId(int position) {
        return position;  //Another call we aren't using, but have to do something since we had to implement (base is abstract).
    }

    //THIS IS WHERE THE ACTION HAPPENS.  getView(..) is how each row gets rendered.
//STEP 5: Easy as A-B-C
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
        row = inflater.inflate(R.layout.frontpagelist, parent, false);

        mRecyclerView = row.findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        for(int i =0; i < poster[position].length;i++){
            //Log.e("poster", poster[position][i].toString());
            //Log.e("moviteTitle", movieTitle[position][i].toString());
        }
        mAdapter = new MyAdapter(poster[position], movieTitle[position]);

        mRecyclerView.setAdapter(mAdapter);

        txtTitle = row.findViewById(R.id.title);
        txtTitle.setText(titleList[position]);



        return row;  //once the row is fully constructed, return it.  Hey whatif we had buttons, can we target onClick Events within the rows, yep!
//return convertView;

    }




}
