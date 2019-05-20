package com.example.fragmentlogging;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private static final String MyFlag = "parkingmiracle";  //this will be our trail of breadcrumbs for logging events.
    private static int eventCount = 0;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDetach() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onDestroyView");
        super.onDestroyView();
    }

    public void onDestroy() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onStop");
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onStart");
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onAttach");
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventCount++;
        Log.e(MyFlag, intToStr(eventCount) + ": Fragment onCreateView");
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public String intToStr(Integer i)
    {
        return i.toString();
    }

    public int strToInt(String S)
    {
        return Integer.parseInt(S);
    }

}
