package com.example.railyatra.railyatra.seatAvailabilityActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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

public class SeatAvailabilityInfo extends AppCompatActivity {

    TextView trainNameTv, fromStationTv, toStationTv, classTv, quotaTv;
    ListView listView1;

    ArrayList<String> listSeat = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SeatAvailability");
        listView1 = (ListView) findViewById(R.id.listView1);


        trainNameTv = (TextView) findViewById(R.id.trainNameTv);
        fromStationTv = (TextView) findViewById(R.id.fromStationTv);
        toStationTv = (TextView) findViewById(R.id.toStationTv);
        classTv = (TextView) findViewById(R.id.classTv);
        quotaTv = (TextView) findViewById(R.id.quotaTv);

        Intent intent = getIntent();
        String trainNumber = intent.getStringExtra("TrainNumber");
        String fromCode = intent.getStringExtra("FromCode");
        String toCode = intent.getStringExtra("ToCode");
        String quota = intent.getStringExtra("Quota");
        String classes = intent.getStringExtra("Class");
        String doj = intent.getStringExtra("Doj");

        String url = "http://api.railwayapi.com/v2/check-seat/train/" + trainNumber + "/source/" + fromCode + "/dest/" + toCode + "/date/" + doj + "/class/" + classes + "/quota/" + quota + "/apikey/3amjeb5e2c/";
        Log.i("URL :",url);

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


    class LoadUsersTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SeatAvailabilityInfo.this);
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
                if (httpCon.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
                        else result = result + line;
                    }
                    bufferedReader.close();
                    httpCon.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return result;
        }//end of doInbackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && s.length() > 0) {
                try {

                    JSONObject jsonResponse = new JSONObject(s);

                    String trainName = jsonResponse.getString("train_name");
                    String trainNumber = jsonResponse.getString("train_number");
                    String resultTrain = trainName+"-"+trainNumber;

                    JSONObject jsonFrom = jsonResponse.getJSONObject("from");
                    String fromName = jsonFrom.getString("name");
                    String fromCode = jsonFrom.getString("code");
                    String resultFrom = fromName+"("+fromCode+")";

                    JSONObject jsonTo = jsonResponse.getJSONObject("to");
                    String toName = jsonTo.getString("name");
                    String toCode = jsonTo.getString("code");
                    String resultTo = toName+"("+toCode+")";

                    JSONObject jsonClass = jsonResponse.getJSONObject("class");
                    String className = jsonClass.getString("name");
                    String classCode = jsonClass.getString("code");
                    String resultClass = className+"("+classCode+")";

                    JSONObject jsonQuota = jsonResponse.getJSONObject("quota");
                    String quotaName = jsonQuota.getString("quota_name");
                    String quotaCode = jsonQuota.getString("quota_code");
                    String resultQuota = quotaName+"("+quotaCode+")";

                    trainNameTv.setText(resultTrain);
                    fromStationTv.setText(resultFrom);
                    toStationTv.setText(resultTo);
                    classTv.setText(resultClass);
                    quotaTv.setText(resultQuota);

                    JSONArray jsonAvailability = jsonResponse.getJSONArray("availability");
                    for (int i = 0; i < jsonAvailability.length(); i++) {
                        JSONObject r = jsonAvailability.getJSONObject(i);
                        String date = r.getString("date");
                        String status = r.getString("status");
                        String availabilityDetails = date+"            "+status;

                        listSeat.add(availabilityDetails);
                    }
                    adapter = new ArrayAdapter<String>(SeatAvailabilityInfo.this, android.R.layout.simple_list_item_1, listSeat);
                    listView1.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
