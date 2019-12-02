package com.example.weatherapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.Datas.wthrItem;
import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class WrthrItemAdapter extends BaseAdapter {

    private LinkedList<wthrItem>  mData;
    private Context mContext;

    public WrthrItemAdapter(LinkedList<wthrItem> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.week_list_row, parent, false);
        TextView txt_date = (TextView) convertView.findViewById(R.id.date);
        ImageView img_smmry = (ImageView) convertView.findViewById(R.id.summary);
        TextView txt_tmpLow = (TextView) convertView.findViewById(R.id.tempLow);
        TextView txt_tmpHigh = (TextView) convertView.findViewById(R.id.tempHigh);
        img_smmry.setBackgroundResource(mData.get(position).getIcon_con());

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
        String date = DATE_FORMAT.format(new Date(mData.get(position).getTime()*1000));// This line
        txt_date.setText(date.toString());
//        txt_tmpLow.setText(new Integer(mData.get(position).getTempLow()).toString());
        txt_tmpLow.setText(String.valueOf(Math.round(mData.get(position).getTemperatureLow())));
        txt_tmpHigh.setText(String.valueOf(Math.round(mData.get(position).getTemperatureHigh())));
//        txt_tmpHigh.setText(mData.get(position).getTempHigh());
        return convertView;
    }
}
