package com.example.weatherapp.Datas;

public class CrrntItem {
    private float temperature;
    private float humidity;
    private float windSpeed;
    private float visibility;
    private float pressure;
    private float ozone;
    private float cloudCover;

    public void setCloudCover(float cloudCover) {
        this.cloudCover = cloudCover;
    }

    public float getCloudCover() {
        return cloudCover;
    }

    private float precipitation;
    private String icon;
    private String summary;

    public float getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(float windSpeed) { this.windSpeed = windSpeed; }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setOzone(float ozone) {
        this.ozone = ozone;
    }

    public void setPrecipitation(float precipitation) { this.precipitation = precipitation; }
    public float getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getVisibility() {
        return visibility;
    }

    public float getPressure() { return pressure; }

    public float getOzone() {
        return ozone;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
