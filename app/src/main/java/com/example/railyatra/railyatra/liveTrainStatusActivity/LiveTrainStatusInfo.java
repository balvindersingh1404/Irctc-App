package com.example.railyatra.railyatra.liveTrainStatusActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class LiveTrainStatusInfo extends AppCompatActivity {

    TextView trainNumberTv, trainPositionTv;

    ListView listView1;
    ArrayList<LiveTrain> listLive = new ArrayList<>();
    LiveTrainStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("LiveTrainStatus");
        trainNumberTv = (TextView)findViewById(R.id.trainNumberTv);
        trainPositionTv = (TextView)findViewById(R.id.positionTv);

        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new LiveTrainStatusAdapter(this, listLive);
        listView1.setAdapter(adapter);

        Intent intent = getIntent();

        String date = intent.getStringExtra("Date");
        String trainNumber = intent.getStringExtra("TrainNumber");

        String url = "http://api.railwayapi.com/v2/live/train/"+trainNumber+"/date/"+date+"/apikey/3amjeb5e2c/";
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

    class LoadUsersTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LiveTrainStatusInfo.this);
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
                    String trainNumber = jsonResponse.getString("train_number");
                    String trainPosition = jsonResponse.getString("position");
                    trainNumberTv.setText(trainNumber);
                    trainPositionTv.setText(trainPosition);

                    JSONArray jsonArray = jsonResponse.getJSONArray("route");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r = jsonArray.getJSONObject(i);

                        JSONObject jsonStation = r.getJSONObject("station_");
                        String stationName = jsonStation.getString("name");
                        String stationCode = jsonStation.getString("code");
                        String resultStation = stationName+"-"+stationCode;

                        String scharrDate = r.getString("scharr_date");
                        String scharr = r.getString("scharr");
                        String schArrive = scharrDate+" "+scharr;

                        String actarrDate = r.getString("actarr_date");
                        String actarr = r.getString("actarr");
                        String actArrive = actarrDate+" "+actarr;

                       // String scharrDate = r.getString("scharr_date");
                        String schdep = r.getString("schdep");
                        String schDepature = scharrDate+" "+schdep;

                       // String actarrDate = r.getString("actarr_date");
                        String actdep = r.getString("actdep");
                        String actDepature = actarrDate+" "+actdep;

                        String latemin = r.getString("latemin");

                        listLive.add(new LiveTrain(schArrive, resultStation, actArrive, schDepature, actDepature, latemin));
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
