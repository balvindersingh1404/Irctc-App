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

public class AutoCompleteTrainAdapter extends ArrayAdapter<Train> implements Filterable {
    private ArrayList<Train> mTrain;

    private String TRAIN_URL1 ="http://api.railwayapi.com/suggest_train/trains/";
    private String TRAIN_URL2= "/apikey/3amjeb5e2c/";

    public AutoCompleteTrainAdapter(Context context, int resource) {
        super(context, resource);
        mTrain = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTrain.size();
    }

    @Override
    public Train getItem(int position) {
        return mTrain.get(position);
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
                        mTrain = new DownloadTrain().execute(term).get();
                    } catch (Exception e) {
                        Log.d("HUS", "EXCEPTION " + e);
                    }
                    filterResults.values = mTrain;
                    filterResults.count = mTrain.size();
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
        View view = inflater.inflate(R.layout.auto_complete_train, parent, false);

        //get Country
        Train train = mTrain.get(position);

        TextView trainName = (TextView) view.findViewById(R.id.trainName);

        trainName.setText(train.getName());

        return view;
    }

    //download mCountry list
    private class DownloadTrain extends AsyncTask<String, Void, ArrayList<Train>> {

        @Override
        protected ArrayList<Train> doInBackground(String... params) {
            try {

                String NEW_URL = TRAIN_URL1 + URLEncoder.encode(params[0],"UTF-8") + TRAIN_URL2;
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

                ArrayList<Train> trianList = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonString);
                JSONArray jsonArray = jsonResponse.getJSONArray("trains");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    //store the train name
                    Train train = new Train();
                    String trainName = jo.getString("name");
                    String trainNumber = jo.getString("number");
                    String result = trainName+"("+trainNumber+")";
                    train.setName(result);
                    trianList.add(train);
                }

                //return the trainList
                return trianList;

            } catch (Exception e) {
                Log.d("HUS", "EXCEPTION " + e);
                return null;
            }
        }
    }
}
