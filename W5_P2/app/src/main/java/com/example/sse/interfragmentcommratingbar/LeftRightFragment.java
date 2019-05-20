package com.example.sse.interfragmentcommratingbar;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.lang.reflect.Field;
import java.util.ArrayList;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftRightFragment extends Fragment {

    //Boiler Plate Stuff.
    private Button btnLeft;
    private Button btnRight;


    public LeftRightFragment() {
        // Required empty public constructor
    }

    //*** MESSAGE PASSING MECHANISM ***//
//Need to create an interface to ensure message passing works between fragments.
//This interface, as with all interfaces serves as a contract.  Implementer of this interface, must implement all of its methods.
//Important Fact: Since the MainActivity will implement this, we are guaranteed to find a sendMessage
//routine there!
    public interface LeftRightFragmentListener {            //this is just an interface definition.
        public void sendMessage(String msg); //it could live in its own file.  placed here for convenience.
    }

    LeftRightFragmentListener LRFL;  //Future reference to an object that implements ControlFragmentListener, Can be anything, as long as it implements all interface methods.

    //onAttach gets called when fragment attaches to Main Activity.  This is the right time to instantiate
    //our ControlFragmentListener, why?  Because we know the Main Activity was successfully created and hooked.
    @Override
    public void onAttach(Context context) {   //The onAttach method, binds the fragment to the owner.  Fragments are hosted by Activities, therefore, context refers to: ____________?
        super.onAttach(context);
        LRFL = (LeftRightFragmentListener) context;  //context is a handle to the main activity, let's bind it to our interface.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_drawable, container, false);  //comment this out, it would return the default view, without our setup/amendments.
        View v = inflater.inflate(R.layout.fragment_leftright, container, false);   //MUST HAPPEN FIRST, otherwise components don't exist.

        btnRight = (Button) v.findViewById(R.id.btnRight);
        btnLeft = (Button) v.findViewById(R.id.btnLeft);

//setting up navigation call backs.  (Left and Right Buttons)
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LRFL.sendMessage("leftBtn");
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LRFL.sendMessage("rightBtn");
            }
        });

        return v;   //returns the view, with our must happen last, Why? A: ____________
    }

}