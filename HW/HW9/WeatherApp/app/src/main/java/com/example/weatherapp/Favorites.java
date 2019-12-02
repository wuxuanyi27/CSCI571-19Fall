package com.example.weatherapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Adapters.MainPagerAdapter;
import com.example.weatherapp.Adapters.WrthrItemAdapter;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Datas.wthrItem;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.details.DetailActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment {

    private ListView week_list;
    private List<wthrItem> mList = null;
    private Context mContext;
    private WrthrItemAdapter mAdapter;
    private CardView overall_info;
    private WeatherData locData;


    public Favorites() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle args = getArguments();
        if (args!= null) {
            String weatherString = args.getString("WeatherBundle");
            Log.d("bundle", args.toString());
            locData = Utils.getGsonParser().fromJson(weatherString, WeatherData.class);
        }

        //Log.d("bundle", locData.getCountry());
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_favorites, container, false);
        mContext = getActivity();
        final FloatingActionButton fab =rootView.findViewById(R.id.add_delete);
        if (locData.getCountryCode() != null) {
            fab.setVisibility(View.GONE);
        }
        // Inflate the layout for this fragment
        week_list = rootView.findViewById(R.id.week_list);
        overall_info = rootView.findViewById(R.id.overall_info);

        ImageView tem_img = rootView.findViewById(R.id.tem_icon);
        tem_img.setImageResource(locData.getCurrentCon().getSmmry_icon());
        TextView location = rootView.findViewById(R.id.location);
        String tt = locData.getCity();
        if (locData.getState() != null) tt+=", "+locData.getState();
        if (locData.getCountryCode() != null) tt += ", "+locData.getCountryCode();
        location.setText(tt);
        TextView temperature = rootView.findViewById(R.id.temperature);
        temperature.setText(locData.getCurrentCon().getTemperature()+"℉");
        TextView summary = rootView.findViewById(R.id.tem_summary);
        summary.setText(locData.getCurrentCon().getSmmry_text());
        TextView humidity = rootView.findViewById(R.id.hum_num);
        humidity.setText(Math.round(locData.getCurrentCon().getHumidity() * 100) + "%");
        TextView windSpeed = rootView.findViewById(R.id.wind_num);
        windSpeed.setText(String.format("%.2f",locData.getCurrentCon().getWindSpeed()) + " mph");
        TextView visibility = rootView.findViewById(R.id.vis_num);
        visibility.setText(String.format("%.2f",locData.getCurrentCon().getVisibility()) + " km");
        TextView pressure = rootView.findViewById(R.id.pre_num);
        pressure.setText(String.format("%.2f",locData.getCurrentCon().getPressure()) + " mb");

        //tem_img.setImageResource(locData.getCurrentCon().getSmmry_icon());

        SharedPreferences preferences = getActivity().getSharedPreferences("city", 0);
        Map<String, ?> key = preferences.getAll();
        if (key.keySet().contains(locData.getCity())) {
            fab.setImageResource(R.drawable.map_marker_minus);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("city", 0);
                Map<String, ?> key = preferences.getAll();
                if (key.keySet().contains(locData.getCity())) {
                    doToastDel(rootView);
                    fab.setImageResource(R.drawable.map_marker_plus);
                }
                else {
                    doToastAdd();
                    fab.setImageResource(R.drawable.map_marker_minus);
                }
            }
        });
        overall_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent (mContext, DetailActivity.class);
                i.putExtra("WeatherBundle", args);
//                i.putExtra("Weather_data", locData.toString());
                startActivity(i);
            }
        });
        mList = new LinkedList<wthrItem>();
        for(wthrItem i: locData.getDailyCon().getData()){
            mList.add(i);
        }
        mAdapter = new WrthrItemAdapter((LinkedList<wthrItem>) mList, mContext);
        week_list.setAdapter(mAdapter);
        return rootView;
    }
    public void addToFavorite(){
        SharedPreferences preferences = getActivity().getSharedPreferences("city", 0);
        SharedPreferences.Editor editor = preferences.edit();
        final Bundle args = getArguments();
        String weatherString = args.getString("WeatherBundle");
        //Log.d("bundle", args.toString());
        editor.putString(locData.getCity(), weatherString);
        editor.apply();
        countSize(preferences);
    }

    public void DelFromFavorite(ViewGroup rootView){
        SharedPreferences preferences = getActivity().getSharedPreferences("city", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(locData.getCity());
        editor.apply();
        countSize(preferences);

        ViewPager mPager = (ViewPager)getActivity().findViewById(R.id.pager);
        if (mPager != null) {
            mPager.setOffscreenPageLimit(0);
            //mAdapter = MainPagerAdapter.getInstance(getSupportFragmentManager(),locData,favorList);
            MainPagerAdapter mAdapter = (MainPagerAdapter) mPager.getAdapter();
            int i = mAdapter.removeCity(rootView, locData);
            Log.d("favorite", " " + i);
            mAdapter.notifyDataSetChanged();
        }
        //mAdapter.destroyItem();
    }

    public int countSize(SharedPreferences preferences){
        Map<String, ?> keys = preferences.getAll();
        Log.d("FavoritesCount", keys.entrySet().size()+"");
        return keys.entrySet().size();
    }

    public void doToastAdd(){
        Toast.makeText(getActivity(), locData.getCity()+" was added to favorites", Toast.LENGTH_SHORT).show();
        addToFavorite();
    }

    public void doToastDel(ViewGroup rootView){
        Toast.makeText(getActivity(), locData.getCity()+" was removed from favorites", Toast.LENGTH_SHORT).show();
        DelFromFavorite(rootView);
    }

}
