package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Adapters.MainPagerAdapter;
import com.example.weatherapp.Datas.CrrntItem;
import com.example.weatherapp.Datas.CrrntItemConverted;
import com.example.weatherapp.Datas.DlyItem;
import com.example.weatherapp.Datas.DlyItemConverted;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Datas.wthrItem;
import com.example.weatherapp.Extras.ApiCall;
import com.example.weatherapp.Extras.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchableActivity extends AppCompatActivity {
    WeatherData locData = new WeatherData();
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String city = intent.getStringExtra("CityBundle");

//        Log.d("Searchable", city);
        getWeatherFromCity(city);
    }

    public void getWeatherFromCity(String city){
        String url = ApiCall.getGeoUrl()+ "city=" + city;
        locData.setCity(city);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d("json", "onResponse: "+response.toString());
                try {
                    JSONArray results = response.getJSONArray("results");
                    JSONObject result = results.getJSONObject(0);
                    JSONObject geometry = result.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    locData.setLat(location.getDouble("lat"));
                    locData.setLon(location.getDouble("lng"));
//                    Log.d("searchable", locData.getLat()+" "+ locData.getLon() +" "+ locData.getCountryCode() + " " +locData.getCountry());
                    getWeather(locData.getLat(), locData.getLon());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("json", "onErrorResponse: "+error.toString());
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void getWeather(double lat, double lon){
        String url=ApiCall.getWeatherUrl()+ "lat=" + lat + "&lng=" + lon;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setDetails(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("json", "onErrorResponse: "+error.toString());
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void setDetails(JSONObject response){
        try {
            locData.setCurrent(Utils.getGsonParser().fromJson(response.get("currently").toString(), CrrntItem.class));
            locData.setDaily(Utils.getGsonParser().fromJson(response.get("daily").toString(), DlyItem.class));
            convertData();
        }catch (JSONException e){
            Log.d("SearchableActivity", "current and daily data exception");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void convertData(){

        CrrntItemConverted itemCrrnt = new CrrntItemConverted();
        itemCrrnt.setHumidity(locData.getCurrent().getHumidity());
        itemCrrnt.setOzone(locData.getCurrent().getOzone());
        itemCrrnt.setPrecipitation(locData.getCurrent().getPrecipitation());
        itemCrrnt.setTemperature(locData.getCurrent().getTemperature());
        itemCrrnt.setPressure(locData.getCurrent().getPressure());
        itemCrrnt.setVisibility(locData.getCurrent().getVisibility());
        itemCrrnt.setWindSpeed(locData.getCurrent().getWindSpeed());
        itemCrrnt.setSmmry_icon(locData.getCurrent().getIcon());
        itemCrrnt.setSmmry_text(locData.getCurrent().getSummary());
        locData.setCurrentCon(itemCrrnt);

        DlyItemConverted itemDly = new DlyItemConverted();
        List<wthrItem> list = new ArrayList<>();
        //Log.d("myTag", locData.getDaily().getData().getClass()+"");
        for(wthrItem i: locData.getDaily().getData())
        {
            i.setIcon_con(i.getIcon());
            list.add(i);
        }
        itemDly.setData(list);
        itemDly.setSmmry_icon(locData.getDaily().getIcon());
        itemDly.setSmmry_text(locData.getDaily().getSummary());
        locData.setDailyCon(itemDly);

        //Log.d("currentCon", locData.getDaily().getData().get(0).getTemperatureHigh()+""+locData.getDaily().getData().get(0).getTemperatureLow());

        Bundle args = new Bundle();
        String WeatherString = Utils.getGsonParser().toJson(locData);
        //Log.d("weatherString", WeatherString);
        args.putString("WeatherBundle", WeatherString);
        Favorites ff = new Favorites();
        ff.setArguments(args);

        String tt = locData.getCity();
        if (locData.getState() != null) tt+=","+locData.getState();
        if (locData.getCountryCode() != null) tt += ","+locData.getCountryCode();
        getSupportActionBar().setTitle(tt);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.search_fra, ff);
        ft.commit();
    }
}
