package com.example.railyatra.railyatra.trainArrivalActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainArrivalInfo extends AppCompatActivity {
    TextView stationCodeTv, totalTrainTv;
    ArrayList<TrainArrival> listArrive = new ArrayList<>();
    TrainArrivalAdapter adapter;
    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrival_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("TrainArrive");

        stationCodeTv = (TextView)findViewById(R.id.stationCodeTv);
        totalTrainTv = (TextView)findViewById(R.id.totalTrainTv);

        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new TrainArrivalAdapter(this, listArrive);
        listView1.setAdapter(adapter);

        Intent intent = getIntent();

        String stationCode = intent.getStringExtra("StationCode");
        String hourValue = intent.getStringExtra("HourValue");

        String url = "http://api.railwayapi.com/v2/arrivals/station/"+stationCode+"/hours/"+hourValue+"/apikey/3amjeb5e2c/";
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
            dialog = new ProgressDialog(TrainArrivalInfo.this);
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
                    String station = jsonResponse.getString("station");
                    String totalTrain = jsonResponse.getString("total");

                    stationCodeTv.setText(station);
                    totalTrainTv.setText(totalTrain);

                    JSONArray jsonArray = jsonResponse.getJSONArray("train");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r = jsonArray.getJSONObject(i);
                        String trainName = r.getString("name");
                        String trainNumber = r.getString("number");
                        String resultTrain = trainName+"-"+trainNumber;

                        String scharr = r.getString("scharr");
                        String schdep = r.getString("schdep");
                        String actarr = r.getString("actarr");
                        String actdep = r.getString("actdep");

                        String delayarr = r.getString("delayarr");
                        String delaydep = r.getString("delaydep");



                        listArrive.add(new TrainArrival(resultTrain, scharr, schdep, actarr, actdep, delayarr, delaydep));
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
