package com.example.railyatra.railyatra.trainFareEnquiryActivity;

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

public class TrainFareEnquiryInfo extends AppCompatActivity {

    TextView trainNameTv, fromTrainTv, toTrainTv, quotaTv;
    ListView listView1;
    ArrayList<TrainFare> listUSERS = new ArrayList<>();
    TrainFareEnquiryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_fare_enquiry_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("FareEnquiry");

        trainNameTv = (TextView)findViewById(R.id.trainNameTv);
        fromTrainTv = (TextView)findViewById(R.id.fromTrainTv);
        toTrainTv = (TextView)findViewById(R.id.toTrainTv);
        quotaTv = (TextView)findViewById(R.id.quotaTv);

        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new TrainFareEnquiryAdapter(this, listUSERS);
        listView1.setAdapter(adapter);
        Intent intent = getIntent();

        String trainNumber = intent.getStringExtra("TrainNumber");
        String fromCode = intent.getStringExtra("FromCode");
        String toCode = intent.getStringExtra("ToCode");
        String age = intent.getStringExtra("Age");
        String quota = intent.getStringExtra("Quota");
        String doj = intent.getStringExtra("Doj");

        String url = "http://api.railwayapi.com/v2/fare/train/"+trainNumber+"/source/"+fromCode+"/dest/"+toCode+"/age/"+age+"/quota/"+quota+"/date/"+doj+"/apikey/3amjeb5e2c/";
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
            dialog = new ProgressDialog(TrainFareEnquiryInfo.this);
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

                    JSONObject jsonFrom = jsonResponse.getJSONObject("from");
                    String fromCode = jsonFrom.getString("code");
                    String fromName = jsonFrom.getString("name");
                    String resultFrom = fromName+"-"+fromCode;

                    JSONObject jsonTo = jsonResponse.getJSONObject("to");
                    String toCode = jsonTo.getString("code");
                    String toName = jsonTo.getString("name");
                    String resultTo = toName+"-"+toCode;

                    JSONObject jsonQuota = jsonResponse.getJSONObject("quota");
                    String quotaCode = jsonQuota.getString("code");
                    String quotaName = jsonQuota.getString("name");
                    String resultQuota = quotaName+"-"+quotaCode;

                    trainNameTv.setText(resultTrain);
                    fromTrainTv.setText(resultFrom);
                    toTrainTv.setText(resultTo);
                    quotaTv.setText(resultQuota);


                    JSONArray jsonArray = jsonResponse.getJSONArray("fare");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r = jsonArray.getJSONObject(i);
                        String code = r.getString("code");
                        String name = r.getString("name");
                        String fare = r.getString("fare");

                        listUSERS.add(new TrainFare(code, name, fare));
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
