package com.example.weatherapp.Datas;

import com.example.weatherapp.R;

public class CrrntItemConverted {
    private int temperature;
    private float humidity;
    private float windSpeed;
    private float visibility;
    private float pressure;
    private float ozone;
    private float cloudCover;
    private float precipitation;
    private int smmry_icon;
    private String smmry_text;

    public float getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(float cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setTemperature(float temperature) {
        this.temperature = Math.round(temperature);
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setOzone(float ozone) {
        this.ozone = ozone;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
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
            default: this.smmry_icon = R.drawable.weather_clearday;
        }
    }

    public void setSmmry_text(String smmry_text) {
        this.smmry_text = smmry_text;
    }

    public int getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getVisibility() {
        return visibility;
    }

    public float getPressure() {
        return pressure;
    }

    public float getOzone() {
        return ozone;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public int getSmmry_icon() {
        return smmry_icon;
    }

    public String getSmmry_text() {
        return smmry_text;
    }
}
