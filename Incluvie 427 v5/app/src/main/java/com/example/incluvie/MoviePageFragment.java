package com.example.incluvie;


import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePageFragment extends Fragment {

    public TextView plot;

    public MoviePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_page, container, false);

        plot = (TextView) v.findViewById(R.id.plot);
        plot.setMovementMethod(new ScrollingMovementMethod());

        return v;
    }

    public void changePlot(String input){
        Log.e("input", input);
        plot.setText(input);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        MPI = (MoviePageInterface) context;
//
//
//    }

}
