package com.example.railyatra.railyatra.trainBetweenStationActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.railyatra.railyatra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainBetweenStationInfo extends AppCompatActivity {
    ListView listView1;
    ArrayList<TrainBetweenStation> listUSERS = new ArrayList<>();
    TrainBetweenStationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_station_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("TrainBetweenStation");

        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new TrainBetweenStationAdapter(this, listUSERS);
        listView1.setAdapter(adapter);

        Intent intent = getIntent();

        String result1 = intent.getStringExtra("FromCode");
        String result2 = intent.getStringExtra("ToCode");
        String dateandmonth = intent.getStringExtra("DOJ");


        String url = "http://api.railwayapi.com/v2/between/source/"+result1+"/dest/"+result2+"/date/"+dateandmonth+"/apikey/3amjeb5e2c/";
        Log.i("URL: ",url);
        LoadUsersTask task = new LoadUsersTask();
        task.execute(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                this.finish();
                break;
        }
        return true;
    }

    class LoadUsersTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TrainBetweenStationInfo.this);
            dialog.setMessage("Loading....");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String str = params[0];
            String result = "";

            //open connect and get data
            try {
                URL url = new URL(str);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setRequestMethod("GET");
                httpCon.connect();
                if (httpCon.getResponseCode()==200)
                {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                    while (true)
                    {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
                        else result=result+line;
                    }
                    bufferedReader.close();
                    httpCon.disconnect();
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return result;
        }//end of doInbackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null && s.length()>0)
            {
                try {

                    JSONObject jsonResponse = new JSONObject(s);
                    JSONArray jsonArray = jsonResponse.getJSONArray("trains");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r = jsonArray.getJSONObject(i);
                        String name = r.getString("name");
                        String number = r.getString("number");
                        String trainName = name+"-"+number;
                        String depatureTime = r.getString("src_departure_time");
                        String arrivalTime = r.getString("dest_arrival_time");
                        String travelTime = r.getString("travel_time");
                        JSONObject fromInfo = r.getJSONObject("from");
                        JSONObject toInfo = r.getJSONObject("to");
                        String fromCode = fromInfo.getString("code");
                        String toCode = toInfo.getString("code");
                        listUSERS.add(new TrainBetweenStation(trainName, fromCode, toCode, depatureTime, arrivalTime, travelTime));
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
