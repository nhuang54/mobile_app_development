package com.example.incluvie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CommentsAdapter extends BaseAdapter {

    private
    String DisplayNames[];             //Keeping it simple.  Using Parallel arrays is the introductory way to store the List data.
    String Comments[];  //the "better" way is to encapsulate the list items into an object, then create an arraylist of objects.
    TextView txtName;
    TextView txtComment;
    Context context;   //Creating a reference to our context object, so we only have to get it once.  Context enables access to application specific resources.
    // Eg, spawning & receiving intents, locating the various managers.

    //STEP 2: Override the Constructor, be sure to:
    // grab the context, the callback gets it as a parm.
    // load the strings and images into object references.
    public CommentsAdapter(String[] input1, String[] input2, Context input3) {
        DisplayNames = input1;
        Comments = input2;
        context = input3;
    }

    //STEP 3: Override and implement getCount(..), ListView uses this to determine how many rows to render.
    @Override
    public int getCount() {
//        return episodes.size(); //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        return DisplayNames.length;   //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
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
    public View getView(int position, View convertView, ViewGroup parent) {  //convertView is Row (it may be null), parent is the layout that has the row Views.

//STEP 5a: Inflate the listview row based on the xml.
        View row;  //this will refer to the row to be inflated or displayed if it's already been displayed. (listview_row.xml)
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        row = inflater.inflate(R.layout.listview_row, parent, false);  //

// Let's optimize a bit by checking to see if we need to inflate, or if it's already been inflated...
//        if (convertView == null){  //indicates this is the first time we are creating this row.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
            row = inflater.inflate(R.layout.commentslistview, parent, false);
//        }
//        else
////        {
//            row = convertView;
//        }

//STEP 5b: Now that we have a valid row instance, we need to get references to the views within that row and fill with the appropriate text and images.
        txtName = (TextView) row.findViewById(R.id.txtName);
        txtComment = (TextView) row.findViewById(R.id.txtComment);

        txtName.setText(DisplayNames[position]);
        txtComment.setText(Comments[position]);

//STEP 5c: That's it, the row has been inflated and filled with data, return it.
        return row;  //once the row is fully constructed, return it.  Hey whatif we had buttons, can we target onClick Events within the rows, yep!
//return convertView;

    }




}
