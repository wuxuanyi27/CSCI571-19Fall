package com.example.weatherapp.Datas;

import com.example.weatherapp.R;

import java.text.DateFormat;
import java.util.Date;

public class wthrItem {
    private long time;
    private String icon;
    private int icon_con;
    private float temperatureHigh;
    private float temperatureLow;

    public wthrItem() {

    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIcon_con() {
        return icon_con;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setIcon_con(String icon) {
        switch(icon){
            case "clear-day": this.icon_con = R.drawable.weather_clearday; break;
            case "clear-night": this.icon_con = R.drawable.weather_clearnight;break;
            case "rain": this.icon_con = R.drawable.weather_rain; break;
            case "sleet": this.icon_con = R.drawable.weather_sleet; break;
            case "snow": this.icon_con = R.drawable.weather_snow; break;
            case "wind": this.icon_con = R.drawable.weather_wind; break;
            case "fog": this.icon_con = R.drawable.weather_fog; break;
            case "cloudy": this.icon_con = R.drawable.weather_cloudy; break;
            case "partly-cloudy-night": this.icon_con = R.drawable.weather_partly_cloudy_night;break;
            case "partly-cloudy-day": this.icon_con = R.drawable.weather_partly_cloudy_day;break;
            default:this.icon_con = R.drawable.weather_clearday;
        }
    }

    public void setTemperatureHigh(float temperatureHigh) {
        this.temperatureHigh = Math.round(temperatureHigh);
    }

    public void setTemperatureLow(float temperatureLow) {
        this.temperatureLow = Math.round(temperatureLow);
    }

    public wthrItem(long time, String icon, float temperatureHigh, float temperatureLow) {
        this.time = time;
        this.icon = icon;
        this.temperatureHigh = Math.round(temperatureHigh);
        this.temperatureLow =  Math.round(temperatureLow);
    }

    public long getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public float getTemperatureHigh() {
        return temperatureHigh;
    }

    public float getTemperatureLow() {
        return temperatureLow;
    }
}
