package com.example.weatherapp.details;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Adapters.DetailPagerAdapter;
import com.example.weatherapp.Adapters.PhotosAdapter;
import com.example.weatherapp.Datas.Photo;
import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Extras.ApiCall;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private RequestQueue mQueue;
    private WeatherData locData;
    private Photo photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle args = getIntent().getBundleExtra("WeatherBundle");
        String weatherString = args.getString("WeatherBundle");
        locData = Utils.getGsonParser().fromJson(weatherString, WeatherData.class);
        String tt = locData.getCity();
        if (locData.getState() != null) tt+=", "+locData.getState();
        if (locData.getCountryCode() != null) tt += ", "+locData.getCountryCode();
        getSupportActionBar().setTitle(tt);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getPhotos(locData.getCity());

        //Log.d("detail", );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.twitter_button:
                String addr = locData.getCity();
                if (locData.getState()!= null) addr += ", " + locData.getState();
                if (locData.getCountryCode()!= null) addr += ", " + locData.getCountryCode();
                String url = "https://twitter.com/intent/tweet?text=";
                url +="Check Out "+addr+"'s Weather! It is "+locData.getCurrentCon().getTemperature()+"°F!&hashtags=CSCI571WeatherSearch";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPhotos(String city){
        String url = ApiCall.getFullPhotoUrl(city);
        //Log.d("Photos", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d("DetailPhotos", response.toString());
                photos = Utils.getGsonParser().fromJson(response.toString(), Photo.class);
                mPager = (ViewPager)findViewById(R.id.detail_pager);
                mPager.setOffscreenPageLimit(3);
                mAdapter = new DetailPagerAdapter(getSupportFragmentManager(),locData, photos);
//        mAdapter =  DetailPagerAdapter.getInstance(getSupportFragmentManager(),locData);
                mPager.setAdapter(mAdapter);


                TabLayout tabLayout = (TabLayout) findViewById(R.id.detail_tab_layout);
                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager){
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.getIcon().setColorFilter(Color.parseColor("#A8A8A8"), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                });
                mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                ConstraintLayout progress = findViewById(R.id.progressLayout);
                progress.setVisibility(View.GONE);

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
}
