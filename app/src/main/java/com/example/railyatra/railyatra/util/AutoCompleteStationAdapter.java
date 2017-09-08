package com.example.railyatra.railyatra.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AutoCompleteStationAdapter extends ArrayAdapter<Station> implements Filterable {
    private ArrayList<Station> mStation;

    private String STATION_URL1 ="http://api.railwayapi.com/suggest_station/name/";
    private String STATION_URL2= "/apikey/3amjeb5e2c/";

    public AutoCompleteStationAdapter(Context context, int resource) {
        super(context, resource);
        mStation = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mStation.size();
    }

    @Override
    public Station getItem(int position) {
        return mStation.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        //get data from the web
                        String term = constraint.toString();
                        mStation = new DownloadStation().execute(term).get();
                    } catch (Exception e) {
                        Log.d("HUS", "EXCEPTION " + e);
                    }
                    filterResults.values = mStation;
                    filterResults.count = mStation.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.auto_complete_station, parent, false);

        //get Country
        Station station = mStation.get(position);

        TextView stationName = (TextView) view.findViewById(R.id.stationName);

        stationName.setText(station.getName());

        return view;
    }

    //download mCountry list
    private class DownloadStation extends AsyncTask<String, Void, ArrayList<Station>> {

        @Override
        protected ArrayList<Station> doInBackground(String... params) {
            try {

                String NEW_URL = STATION_URL1 + URLEncoder.encode(params[0],"UTF-8") + STATION_URL2;
                Log.d("HUS", "JSON RESPONSE URL " + NEW_URL);

                URL url = new URL(NEW_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                //parse JSON and store it in the list
                String jsonString = sb.toString();

                ArrayList<Station> stationList = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonString);
                JSONArray jsonArray = jsonResponse.getJSONArray("station");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    //store the country name
                    Station station = new Station();
                    String fullname = jo.getString("fullname");
                    String code = jo.getString("code");
                    String result = fullname+"("+code+")";
                    station.setName(result);
                    stationList.add(station);
                }

                //return the countryList
                return stationList;

            } catch (Exception e) {
                Log.d("HUS", "EXCEPTION " + e);
                return null;
            }
        }
    }
}
