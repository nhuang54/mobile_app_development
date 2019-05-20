package com.example.incluvie;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class CommentsFragment extends Fragment {

    private RatingFragment.OnFragmentInteractionListener mListener;
    private Button submit;
    private EditText comment;
    private String id;
    private ListView commentList;

    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String[] submitList2;
    String[] nameList2;
    CommentsAdapter cAdapter;

    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);
        Log.e("we are here", "here1");
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Movies").child(id);

        submit = v.findViewById(R.id.btnSubmitComment);
        comment = v.findViewById(R.id.txtCreateComment);
        commentList = v.findViewById(R.id.lvComments);




        populateComments();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(comment.getText())) {
                    addComment(comment.getText().toString());
                    comment.setText("");
                }
                else{
                    Toast.makeText(getActivity(), "Please enter a valid comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void populateComments(){

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> submitList = new ArrayList<String>();
                ArrayList<String> nameList = new ArrayList<String>();


                Iterator iterator = dataSnapshot.child("Comments").getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();
                    String name = dataUser.getKey().toString();
                    Log.e("name", name.toString());

                    Iterator iterator2 = dataSnapshot.child("Comments").child(name).getChildren().iterator();
                    while (iterator2.hasNext()) {

                        DataSnapshot dataUser2 = (DataSnapshot) iterator2.next();
                        String name2 = dataUser2.getKey();


                        nameList.add(name2 + ":");
                        submitList.add(dataUser.child(name2).getValue().toString());
                    }
                }

                    submitList2 = new String[submitList.size()];
                    nameList2 = new String[nameList.size()];

                    submitList.toArray(submitList2);
                    for(int i = 0; i< submitList2.length; i++){
                        Log.e("submitList", submitList2[i]);
                    }

                    nameList.toArray(nameList2);


                    cAdapter = new CommentsAdapter(nameList2, submitList2, getActivity());
                    commentList.setAdapter(cAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addComment(String input){

        final String comment = input;

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String millisInString  = dateFormat.format(new Date());

                mRef.child("Comments").child(millisInString).child(mAuth.getCurrentUser().getDisplayName()).setValue(comment);
                populateComments();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void getId(String input){
        id = input;
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
