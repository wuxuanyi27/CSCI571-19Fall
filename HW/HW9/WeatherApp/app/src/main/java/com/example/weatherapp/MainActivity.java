package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Adapters.AutoCompleteAdapter;
import com.example.weatherapp.Adapters.MainPagerAdapter;
import com.example.weatherapp.Datas.Auto;
import com.example.weatherapp.Datas.CrrntItem;
import com.example.weatherapp.Datas.CrrntItemConverted;
import com.example.weatherapp.Datas.DlyItem;
import com.example.weatherapp.Datas.DlyItemConverted;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Datas.wthrItem;
import com.example.weatherapp.Extras.ApiCall;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.details.DetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private AutoCompleteAdapter aAdapter;
    private RequestQueue mQueue;
    private Auto acData;
    private ArrayList<WeatherData> favorList = new ArrayList<>();
    private WeatherData locData = WeatherData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate: ");
        mQueue = Volley.newRequestQueue(this);
        SharedPreferences preferences = this.getSharedPreferences("city",0);

        setTheme(R.style.AppTheme);
        /*try{
            Thread.sleep(1000);
        }
        catch (Exception e){
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progress);
        spinner.setVisibility(View.VISIBLE);
        getLocation();
        //Log.d("getJson", mAdapter.toString());
    }

    @Override
    protected void onRestart() {
        Log.d("MainActivity", "onRestart: ");
        getStorage();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        favorList.clear();
        Log.d("MainActivity", "onStop: ");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView =(SearchView)
                MenuItemCompat.getActionView(searchItem);
        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.parseColor("#1E1E1E"));
        searchAutoComplete.setDropDownBackgroundResource(R.color.colorLight);

        searchAutoComplete.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String queryString =(String)parent.getItemAtPosition(position);
                        searchAutoComplete.setText(queryString);
                    }
                }
        );
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.length() >= 1)
                            getAutoComplete(newText, searchAutoComplete);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (query.length() > 0) {
                            Intent i = new Intent(MainActivity.this, SearchableActivity.class);
                            i.putExtra("CityBundle", query);
                            startActivity(i);
                        }
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    private void getLocation(){
        String url = ApiCall.getIpUrl();
        //Log.d("url", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               //Log.d("json", "onResponse: "+response.toString());
                try {
                    locData.setCity(response.getString("city"));
                    locData.setState(response.getString("region"));
                    locData.setCountry(response.getString("country"));
                    locData.setLat(response.getDouble("lat"));
                    locData.setLon(response.getDouble("lon"));
                    locData.setCountryCode(response.getString("countryCode"));
                    if (locData.getCountryCode().equals("US"))
                        locData.setCountryCode("USA");
                    getWeather();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json", "onErrorResponse: "+error.toString());
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void getWeather() {
        String url = ApiCall.getWeatherUrl() + "lat=" + locData.getLat() + "&lng=" + locData.getLon();
        //Log.d("MyTag", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    setDetails(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("json", "onErrorResponse: "+error.toString());
                    error.printStackTrace();
                }
        });
        mQueue.add(request);
    }

    public void getAutoComplete(String input, final SearchView.SearchAutoComplete s){
        String url = ApiCall.getAutoCompleteUrl()+"input="+input;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setAutos(response, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void setDetails(JSONObject response) {
        try {
            locData.setCurrent(Utils.getGsonParser().fromJson(response.get("currently").toString(), CrrntItem.class));
            locData.setDaily(Utils.getGsonParser().fromJson(response.get("daily").toString(), DlyItem.class));
            convertData();
        }catch (JSONException e){
            Log.d("MainActivity", "current and daily data exception");
        }
    }

    private void setAutos(JSONObject response, SearchView.SearchAutoComplete s){
        acData = Utils.getGsonParser().fromJson(response.toString(), Auto.class);
        aAdapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        List<String> dataArr = new ArrayList<>();
        for(int i = 0; i < acData.getPredictions().size(); i++){
            dataArr.add(acData.getPredictions().get(i).getDescription());
        }
        //Log.d("MainActivity", dataArr.size()+"");
        aAdapter.setData(dataArr);
        s.setAdapter(aAdapter);
    }

    private void convertData(){

        CrrntItemConverted itemCrrnt = new CrrntItemConverted();
        itemCrrnt.setHumidity(locData.getCurrent().getHumidity());
        itemCrrnt.setOzone(locData.getCurrent().getOzone());
        itemCrrnt.setPrecipitation(locData.getCurrent().getPrecipitation());
        itemCrrnt.setTemperature(locData.getCurrent().getTemperature());
        itemCrrnt.setPressure(locData.getCurrent().getPressure());
        itemCrrnt.setCloudCover(locData.getCurrent().getCloudCover());
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

        //Bundle args = new Bundle();
        //String weatherString = locData.toString();
        //args.putString( "WeatherBundle" , weatherString);
        getStorage();
        /*mPager = (ViewPager)findViewById(R.id.pager);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), locData, favorList);

//        mAdapter = MainPagerAdapter.getInstance(getSupportFragmentManager(), locData);
        mPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager, true);*/
    }

    public void getStorage(){
        SharedPreferences preferences = this.getSharedPreferences("city",0);
        //Log.d("MainActivity", preferences.toString());
        Map<String, ?> key = preferences.getAll();
        Log.d("MainActivity", key.entrySet().size()+"");
        for(Map.Entry<String, ?>entry : key.entrySet()){
            String s = entry.getValue().toString();

            WeatherData favData = Utils.getGsonParser().fromJson(s, WeatherData.class);
            Log.d("MainActivity", favData.getCity()+ " "+ s);
            favorList.add(favData);
        }
        ConstraintLayout progress = findViewById(R.id.progressLayout);
        progress.setVisibility(View.GONE);
        //Log.d("MainActivity", "getStorage: ");
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(0);
        //mAdapter = MainPagerAdapter.getInstance(getSupportFragmentManager(),locData,favorList);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), locData, favorList);

        mPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager, true);

    }



}
