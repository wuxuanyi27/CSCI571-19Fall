package com.example.weatherapp.Datas;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.example.weatherapp.R;

import java.util.List;

public class DlyItemConverted {
    List<wthrItem> data;
    private int smmry_icon;
    private String smmry_text;

    public void setData(List<wthrItem> data) {
        this.data = data;
    }

    public void setSmmry_icon(String smmry_icon) {
        switch(smmry_icon){
            case "clear-day": this.smmry_icon = R.drawable.weather_clearday; break;
            case "clear-night": this.smmry_icon = R.drawable.weather_clearnight;break;
            case "rain": this.smmry_icon = R.drawable.weather_rain; break;
            case "sleet": this.smmry_icon = R.drawable.weather_sleet; break;
            case "snow": this.smmry_icon = R.drawable.weather_snow; break;
            case "wind": this.smmry_icon = R.drawable.weather_wind; break;
            case "fog": this.smmry_icon = R.drawable.weather_fog; break;
            case "cloudy": this.smmry_icon = R.drawable.weather_cloudy; break;
            case "partly-cloudy-night": this.smmry_icon = R.drawable.weather_partly_cloudy_night;break;
            case "partly-cloudy-day": this.smmry_icon = R.drawable.weather_partly_cloudy_day;break;
            default:this.smmry_icon = R.drawable.weather_clearday;
        }
    }

    public void setSmmry_text(String smmry_text) {
        this.smmry_text = smmry_text;
    }

    public List<wthrItem> getData() {
        return data;
    }

    public int getSmmry_icon() {
        return smmry_icon;
    }

    public String getSmmry_text() {
        return smmry_text;
    }
}
