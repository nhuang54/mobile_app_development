package com.example.sse.interfragmentcommunication;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */

//This will get inflated up top.
public class ControlFragment extends Fragment {


    private EditText edtSendMessage;
    private EditText edtSendMessage2;
    private Button btnSendMessage;

    public ControlFragment() {  //todo, why?
        // Required empty public constructor
    }

//*** MESSAGE PASSING MECHANISM ***//
//Need to create an interface to ensure message passing works between fragments.
//This interface, as with all interfaces serves as a contract.  Implementer of this interface, must implement all of its methods.
//Important Fact: Since the MainActivity will implement this, we are guaranteed to find a sendMessage
//routine there!
    public interface ControlFragmentListener {            //this is just an interface definition.
        public void sendMessage(String msg, String msg2); //it could live in its own file.  placed here for convenience.
    }

    ControlFragmentListener CFL;  //Future reference to an object that implements ControlFragmentListener, Can be anything, as long as it implements all interface methods.
                                  //Question: Who holds the reference?  Answer: ____________
//*** MESSAGE PASSING MECHANISM ***//


    //onAttach gets called when fragment attaches to Main Activity.  This is the right time to instantiate
    //our ControlFragmentListener, why?  Because we know the Main Activity was successfully created and hooked.
    @Override
    public void onAttach(Context context) {   //The onAttach method, binds the fragment to the owner.  Fragments are hosted by Activities, therefore, context refers to: ____________?
        super.onAttach(context);
        CFL = (ControlFragmentListener) context;  //context is a handle to the main activity, let's bind it to our interface.
    }


//NOTE:
//This old onAttach, still works, but is deprecated,
//better to use the newer one above, which passes a context object, which can also be typecast into an Activity Object.
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        CFL = (ControlFragmentListener) activity;
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_control, container, false);  //this needs to be separated from return statement,
                                                                                  //so we can refer to the views objects before passing view to Activity.

        edtSendMessage = (EditText) view.findViewById(R.id.edtSendMessage);
        edtSendMessage2 = (EditText) view.findViewById(R.id.edtSendMessage2);
        btnSendMessage = (Button) view.findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnSendMessage.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //FIX ME!!  //Hide the soft keyboard when we click the button.  Ref: https://stackoverflow.com/questions/18977187/how-to-hide-soft-keyboard-when-activity-starts
                CFL.sendMessage(edtSendMessage.getText().toString(), edtSendMessage2.getText().toString());  //CFL is a handle to our MainActivity, we are sending it our message text.
            }
        });

        return view;
    }

}
