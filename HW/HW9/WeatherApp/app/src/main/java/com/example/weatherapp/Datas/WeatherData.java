package com.example.weatherapp.Datas;

import java.util.List;

public class WeatherData {
    private double lat;
    private double lon;
    private String city;
    private String state;
    private String country;
    private String countryCode;
    private static WeatherData mInstance;

    public String getCountryCode() {
        return countryCode;
    }

    private CrrntItem current;
    private CrrntItemConverted currentCon;
    private DlyItemConverted dailyCon;
    private DlyItem daily;

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public static WeatherData getInstance(){
        if (mInstance == null) mInstance = new WeatherData();
        return mInstance;
    }

    public CrrntItemConverted getCurrentCon() {
        return currentCon;
    }

    public void setCurrentCon(CrrntItemConverted currentCon) {
        this.currentCon = currentCon;
    }

    public void setDailyCon(DlyItemConverted dailyCon) {
        this.dailyCon = dailyCon;
    }

    public DlyItemConverted getDailyCon() {
        return dailyCon;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public void setDaily(DlyItem daily) {
        this.daily = daily;
    }

    public DlyItem getDaily() {
        return daily;
    }

    public void setCurrent(CrrntItem current) {
        this.current = current;
    }

    public CrrntItem getCurrent() {
        return current;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   /* public void setDly_smry(String dly_smry) {
        this.dly_smry = dly_smry;
    }

    public void setWkly_wthr(List<wthrItem> wkly_wthr) {
        this.wkly_wthr = wkly_wthr;
    }*/

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    /*public String getDly_smry() {
        return dly_smry;
    }

    public List<wthrItem> getWkly_wthr() {
        return wkly_wthr;
    }*/
}
