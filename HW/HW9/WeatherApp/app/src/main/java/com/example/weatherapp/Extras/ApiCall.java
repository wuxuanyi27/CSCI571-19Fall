package com.example.weatherapp.Extras;


import android.util.Log;
import android.util.StateSet;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiCall {
    static String ipUrl="http://ip-api.com/json";
    static String weatherUrl="http://weather-app.us-west-1.elasticbeanstalk.com/get_weather?";
    static String photosUrl = "http://weather-app.us-west-1.elasticbeanstalk.com/get_photos?";
    static String autoCompleteUrl = "http://weather-app.us-west-1.elasticbeanstalk.com/get_autocomplete?";
    static String geoUrl = "http://weather-app.us-west-1.elasticbeanstalk.com/get_location?";

    public static String getAutoCompleteUrl() {
        return autoCompleteUrl;
    }

    public static String getWeatherUrl() {
        return weatherUrl;
    }

    public static String getIpUrl() {

        return ipUrl;
    }

    public static String getPhotosUrl() {
        return photosUrl;
    }

    public static String getGeoUrl() {
        return geoUrl;
    }

    public static String getFullPhotoUrl(String city) {
        String ret = photosUrl + "city=" + city;
        return ret;
    }
}
