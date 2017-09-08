package com.example.railyatra.railyatra.trainRunningDayActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class TrainRunningDayInfo extends AppCompatActivity {

    TextView trainNameTv;
    ListView listView1;
    ArrayList<String> listRunning = new ArrayList<>();
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_train_running_day_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("RunningTrain");
        listView1 = (ListView) findViewById(R.id.listView1);
        trainNameTv = (TextView)findViewById(R.id.trainNameTv);

        Intent intent = getIntent();
        String trainNumber = intent.getStringExtra("TrainNumber");
        String url = "http://api.railwayapi.com/v2/name-number/train/"+trainNumber+"/apikey/3amjeb5e2c/";
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
            dialog = new ProgressDialog(TrainRunningDayInfo.this);
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

                    JSONObject jsonTrain = jsonResponse.getJSONObject("train");
                    String trainName = jsonTrain.getString("name");
                    String trainNumber = jsonTrain.getString("number");
                    String resultTrain = trainName+"-"+trainNumber;
                    trainNameTv.setText(resultTrain);

                    JSONArray dayArray = jsonTrain.getJSONArray("days");
                    for(int i = 0; i < dayArray.length(); i++) {
                        JSONObject r = dayArray.getJSONObject(i);

                        String dayCode = r.getString("code");
                        String runStatus = r.getString("runs");

                        String resultDay = dayCode+"                       "+runStatus;

                        listRunning.add(resultDay);
                    }
                    adapter = new ArrayAdapter<String>(TrainRunningDayInfo.this, android.R.layout.simple_list_item_1, listRunning);
                    listView1.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
