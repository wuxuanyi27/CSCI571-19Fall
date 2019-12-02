package com.example.weatherapp.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.weatherapp.Datas.Photo;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Favorites;
import com.example.weatherapp.details.Photos;
import com.example.weatherapp.details.Today;
import com.example.weatherapp.details.Weekly;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DetailPagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_PAGES = 3;
    private static DetailPagerAdapter mInstance;
    private static WeatherData locData;
    private static Photo locPhoto;
    public DetailPagerAdapter(FragmentManager fm, WeatherData data, Photo photo) {
        super(fm);
        locData = data;
        locPhoto = photo;
    }

    public static DetailPagerAdapter getInstance(FragmentManager fm, WeatherData data, Photo photo){
        if (mInstance == null)
        {
            mInstance = new DetailPagerAdapter(fm, data, photo);
            locData = data;
            locPhoto = photo;
        }
        return mInstance;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) return newTodayInstance();
        else if(i == 1) return newWeeklyInstance();
        else return newPhotosInstance();
    }

    static Fragment newTodayInstance(){
        Today f = new Today();
        Bundle args = new Bundle();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String WeatherString = gson.toJson(locData);
        String photoString = gson.toJson(locPhoto);
        //Log.d("weatherString", WeatherString);
        args.putString("PhotoBundle", photoString);
        args.putString("WeatherBundle", WeatherString);
        f.setArguments(args);
        return f;
    }

    static Fragment newWeeklyInstance(){
        Weekly f = new Weekly();
        Bundle args = new Bundle();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String WeatherString = gson.toJson(locData);
        //Log.d("weatherString", WeatherString);
        String photoString = gson.toJson(locPhoto);
        //Log.d("weatherString", WeatherString);
        args.putString("PhotoBundle", photoString);
        args.putString("WeatherBundle", WeatherString);
        f.setArguments(args);
        return f;
    }

    static Fragment newPhotosInstance(){
        Photos f = new Photos();
        Bundle args = new Bundle();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String WeatherString = gson.toJson(locData);
        //Log.d("weatherString", WeatherString);
        String photoString = gson.toJson(locPhoto);
        //Log.d("weatherString", WeatherString);
        args.putString("PhotoBundle", photoString);
        args.putString("WeatherBundle", WeatherString);
        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
