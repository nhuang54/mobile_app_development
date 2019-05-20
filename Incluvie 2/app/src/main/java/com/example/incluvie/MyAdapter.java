package com.example.incluvie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private String[] titleDataset;


    public String movie;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton mImageButton;
        public ViewHolder(ImageButton v) {
            super(v);
            mImageButton = v;
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent i = new Intent(view.getContext(), movie_page.class);
                i.putExtra("movie", titleDataset[getAdapterPosition()]);
                view.getContext().startActivity(i);
                //bundle.putString("id",titleDataset[getAdapterPosition()]);
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset, String[] movie_title) {
        mDataset = myDataset;
        titleDataset = movie_title;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ImageButton v = (ImageButton) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_button, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mImageButton.setImageBitmap(loadBitmap(mDataset[position]));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        int IO_BUFFER_SIZE = 4 * 1024;
        try {
            URI uri = new URI(url);
            url = uri.toASCIIString();
            in = new BufferedInputStream(new URL(url).openStream(),
                    IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            int bytesRead;
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
                    options);
        } catch (IOException e) {
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}