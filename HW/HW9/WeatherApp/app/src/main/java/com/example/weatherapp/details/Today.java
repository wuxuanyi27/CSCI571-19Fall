package com.example.weatherapp.details;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Today extends Fragment {
    private WeatherData locData;
    private Context mContext;

    public Today() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("1", "onCreateView: ");
        // Inflate the layout for this fragment
        final Bundle args = getArguments();
        Log.d("weekly args", args.getString("WeatherBundle"));
        if (args!= null) {
            String weatherString = args.getString("WeatherBundle");
            Log.d("Today", args.toString());
            locData = Utils.getGsonParser().fromJson(weatherString, WeatherData.class);
        }
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_today, container, false);
        mContext = getActivity();
        TextView ws = rootView.findViewById(R.id.WS_value);
        ws.setText(String.format("%.2f",locData.getCurrentCon().getWindSpeed()) + " mph");
//        Log.d("windSpeed", ws.toString());
        TextView prs = rootView.findViewById(R.id.Prs_value);
        prs.setText(String.format("%.2f",locData.getCurrentCon().getPressure()) + " mb");
        TextView prcp =rootView.findViewById(R.id.Prcp_value);
        /*if (locData.getCurrentCon().getPrecipitation() == 0){
            prcp.setText("NA");
        }*/
        prcp.setText(String.format("%.2f",locData.getCurrentCon().getPrecipitation()) + " mmph");

        TextView t = rootView.findViewById(R.id.T_value);
        t.setText(locData.getCurrentCon().getTemperature() + "℉");

        TextView h = rootView.findViewById(R.id.H_value);
        h.setText(Math.round(locData.getCurrentCon().getHumidity() * 100) + "%");

        TextView v = rootView.findViewById(R.id.V_value);
        v.setText(String.format("%.2f",locData.getCurrentCon().getVisibility()) + " km");

        TextView o = rootView.findViewById(R.id.O_value);
        o.setText(String.format("%.2f",locData.getCurrentCon().getOzone()) + " DU");

        TextView cc = rootView.findViewById(R.id.CC_value);
        cc.setText(Math.round(locData.getCurrentCon().getCloudCover()) + "%");

        ImageView smmry_img = rootView.findViewById(R.id.Smmry_img);
        smmry_img.setImageResource(locData.getCurrentCon().getSmmry_icon());

        TextView smmry_text = rootView.findViewById(R.id.Smmry_text);
        String s = "";
        switch (locData.getCurrent().getIcon()){
            case "clear-day": s = "clear day"; break;
            case "clear-night": s = "clear night"; break;
            case "partly-cloudy-night": s = "cloudy night"; break;
            case "partly-cloudy-day": s = "cloudy day"; break;
            default: s = locData.getCurrent().getIcon();
        }
        smmry_text.setText(s);
        return rootView;
    }
}
