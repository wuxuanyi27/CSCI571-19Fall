package com.example.weatherapp.details;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.Datas.WeatherData;
import com.example.weatherapp.Extras.Utils;
import com.example.weatherapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Weekly extends Fragment {
    private static WeatherData locData;
    private static Context mContext;
    ArrayList<Entry> temHigh;
    ArrayList<Entry> temLow;


    public Weekly() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle args = getArguments();
        if (args!= null) {
            String weatherString = args.getString("WeatherBundle");
            locData = Utils.getGsonParser().fromJson(weatherString, WeatherData.class);
        }
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_weekly, container, false);
        mContext = getActivity();
        TextView sum_text = rootView.findViewById(R.id.sum_text);
        ImageView sum_img = rootView.findViewById(R.id.sum_img);
        sum_img.setImageResource(locData.getDailyCon().getSmmry_icon());
        sum_text.setText(locData.getDailyCon().getSmmry_text());

        LineChart lineChart = rootView.findViewById(R.id.lineChart);

        drawChart(lineChart);
        return rootView;
    }

    public void drawChart(LineChart lineChart){
        temHigh = new ArrayList<>();
        temLow = new ArrayList<>();
        for(int i = 0; i< 8; i++){
            Entry c = new Entry(i,locData.getDailyCon().getData().get(i).getTemperatureLow());
            temLow.add(c);
        }
        for(int i = 0; i< 8; i++){
            Entry c = new Entry(i,locData.getDailyCon().getData().get(i).getTemperatureHigh());
            temHigh.add(c);
        }
        LineDataSet setTemLow = new LineDataSet(temLow,"Minimum Temperature");
        LineDataSet setTemHigh = new LineDataSet(temHigh,"Maximum Temperature");
        setTemLow.setColor(Color.parseColor("#BC83FC"));
        setTemHigh.setColor(Color.parseColor("#F9AC1A"));
        ArrayList<ILineDataSet> allLinesList = new ArrayList<>();
        allLinesList.add(setTemLow);
        allLinesList.add(setTemHigh);

        LineData data = new LineData(allLinesList);
        lineChart.setData(data);
        XAxis x = lineChart.getXAxis();
        x.setTextColor(Color.parseColor("#ffffff"));
        lineChart.getAxisLeft().setTextColor(Color.parseColor("#ffffff"));
        lineChart.getAxisRight().setTextColor(Color.parseColor("#ffffff"));;
        Legend legend =lineChart.getLegend();
        legend.setTextSize(14f);
        legend.setFormSize(14f);
        x.setDrawGridLines(false);
        legend.setTextColor(Color.parseColor("#ffffff"));
        lineChart.invalidate();
    }

}
