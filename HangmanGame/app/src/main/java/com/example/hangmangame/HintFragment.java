package com.example.hangmangame;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HintFragment extends Fragment {

    public TextView txtHint; // initializing the textView

    public HintFragment() {
        // Required empty public constructor
    }
    // setting up the interface
    public interface HintFragmentListener {
        public void setHint(String hint);
    }

    LetterFragment.LetterFragmentListener HiFL;

    public void onAttach(Context context){
        super.onAttach(context);
        HiFL = (LetterFragment.LetterFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hint, container, false);

        txtHint = (TextView)view.findViewById(R.id.txtHint); // bind id with txtHint variable
        return view;
    }

}
