package com.example.weatherapp.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Adapters.PhotosAdapter;
import com.example.weatherapp.Extras.ApiCall;
import com.example.weatherapp.Datas.Photo;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Photos extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Photo photos;
    private static String[] myPhotos;


    public Photos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle args = getArguments();
        if (args!= null) {
            String photoString = args.getString("PhotoBundle");
            Log.d("PhotoFrg", photoString);
            photos = Utils.getGsonParser().fromJson(photoString, Photo.class);
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_photos, container, false);

        recyclerView = rootView.findViewById(R.id.photos_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ConvertToString(photos);
        return rootView;
    }


    public void ConvertToString(Photo photos){
        List<String> retList = new ArrayList<>();
        //Log.d("Photos", photos.getItems().size()+"");
        for(int i = 0; i < photos.getItems().size(); i++){
            retList.add(photos.getItems().get(i).getLink());
        }
        myPhotos = new String[retList.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < retList.size(); j++) {

            // Assign each value to String array
            myPhotos[j] = retList.get(j);
            //Log.d("Photos", myPhotos[j]);
        }
        Log.d("Photos", myPhotos.length+"");
        mAdapter = new PhotosAdapter(myPhotos, getActivity());
        recyclerView.setAdapter(mAdapter);
    }
}
