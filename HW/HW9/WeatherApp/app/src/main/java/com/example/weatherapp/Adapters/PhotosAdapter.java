package com.example.weatherapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
    private String[] mDataset;
    public ImageView imageView;
    public Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;

        public MyViewHolder(ImageView v) {
            super(v);
            image = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotosAdapter(String[] myDataset, Context c) {
        mDataset = myDataset;
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_photo, parent, false);


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Glide.with(mContext).load(mDataset[position]).into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
        //return 8;
    }
}
