package com.example.weatherapp.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.Favorites;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    static int NUM_PAGES = 1;
    private static MainPagerAdapter mInstance;
    private static WeatherData locData;
    private static ArrayList<WeatherData> favList;

   public MainPagerAdapter(FragmentManager fm , WeatherData data, ArrayList<WeatherData> list){
        super(fm);
        locData = data;
        favList = list;
        NUM_PAGES = list.size() + 1;
    }

    public static MainPagerAdapter getInstance( FragmentManager fm, WeatherData data, ArrayList<WeatherData> list){
        if (mInstance == null)
            mInstance = new MainPagerAdapter(fm, data, list);
        locData = data;
        favList = list;
        NUM_PAGES = list.size() + 1;
        return mInstance;
    }

    @Override
    public Fragment getItem(int i) {
        return newInstance(i);
    }

    static Fragment newInstance(int num){
        Log.d("countNumber", num+"");
       if (num == 0) {
           Favorites f = new Favorites();
           Bundle args = new Bundle();
           String WeatherString = Utils.getGsonParser().toJson(locData);
           //Log.d("weatherString", WeatherString);
           args.putString("WeatherBundle", WeatherString);
           f.setArguments(args);
           return f;
       }
       else {
           Favorites f = new Favorites();
           Bundle args = new Bundle();
           String WeatherString = Utils.getGsonParser().toJson(favList.get(num - 1));
           //Log.d("weatherString", WeatherString);
           args.putString("WeatherBundle", WeatherString);
           f.setArguments(args);
           return f;
       }
    }

    public int removeCity(ViewGroup mContext, WeatherData dd){
       int position = 0;
       for(WeatherData d:favList){
           //Log.d("CITYS", dd.getCity()+" "+d.getCity());
           if (dd.getCity().equals(d.getCity())) {
               favList.remove(position);
               break;
           }
           position++;
       }
       //Log.d("SIZE", ""+favList.size());
       //Log.d("REMOVE", position+1 +"");
       Object object = instantiateItem(mContext, position + 1);
       destroyItem(mContext, position + 1, object);
       NUM_PAGES = favList.size() + 1;
       //NUM_PAGES -- ;
       return position;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return MainPagerAdapter.POSITION_NONE;
    }
}
