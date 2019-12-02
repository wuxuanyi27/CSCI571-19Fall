package com.example.weatherapp.Datas;

import java.util.List;

public class DlyItem {
    private List<wthrItem> data;
    private String summary;
    private String icon;

    public void setData(List<wthrItem> data) {
        this.data = data;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<wthrItem> getData() {
        return data;
    }

    public String getIcon() {
        return icon;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }


}
