package com.example.railyatra.railyatra.pnrStatusActivity;

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

public class PnrStatusInfo extends AppCompatActivity {

    TextView trainNameTv, prnNoTv, dojTv, boardingTv, reservationTv, passengerTv ;
    ListView listView1;

    ArrayList<String> listPNR = new ArrayList<>();
    ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_prn_status_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("PNRStatus");
        listView1 = (ListView) findViewById(R.id.listView1);

        trainNameTv = (TextView)findViewById(R.id.trainNameTv);
        prnNoTv = (TextView)findViewById(R.id.prnTv);
        dojTv = (TextView)findViewById(R.id.dojTv);
        boardingTv = (TextView)findViewById(R.id.boardingTv);
        reservationTv = (TextView)findViewById(R.id.reservationTv);
        passengerTv = (TextView)findViewById(R.id.passengerTv);

        Intent intent = getIntent();
        String pnr = intent.getStringExtra("Pnr");

        String url = "http://api.railwayapi.com/v2/pnr-status/pnr/"+pnr+"/apikey/3amjeb5e2c/";
        Log.i("URL : ",url);
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
            dialog = new ProgressDialog(PnrStatusInfo.this);
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
                    String trainName = jsonResponse.getString("train_name");
                    String trainNumber = jsonResponse.getString("train_num");
                    String resultTrain = trainName+""+trainNumber;
                    String pnrNumber = jsonResponse.getString("pnr");
                    String dateOfJourney = jsonResponse.getString("doj");
                    String passengerNumber = jsonResponse.getString("total_passengers");

                    JSONObject jsonBoarding = jsonResponse.getJSONObject("boarding_point");
                    String boardingName = jsonBoarding.getString("name");
                    String boardingCode = jsonBoarding.getString("code");
                    String boardingResult = boardingName+"("+boardingCode+")";

                    JSONObject jsonReservation = jsonResponse.getJSONObject("reservation_upto");
                    String reservationName = jsonReservation.getString("name");
                    String reservationCode = jsonReservation.getString("code");
                    String reservationResult = reservationName+"("+reservationCode+")";

                    trainNameTv.setText(resultTrain);
                    prnNoTv.setText(pnrNumber);
                    dojTv.setText(dateOfJourney);
                    passengerTv.setText(passengerNumber);
                    boardingTv.setText(boardingResult);
                    reservationTv.setText(reservationResult);

                    JSONArray passengerArray = jsonResponse.getJSONArray("passengers");
                    for(int i = 0; i < passengerArray.length(); i++) {
                        JSONObject r = passengerArray.getJSONObject(i);

                        String bookingStatus = r.getString("booking_status");
                        String currentStatus = r.getString("current_status");
                        String coachPosition = r.getString("coach_position");

                        String passengerDetails = bookingStatus+"             "+currentStatus+"                    "+coachPosition;

                        listPNR.add(passengerDetails);
                    }
                    adapter = new ArrayAdapter<String>(PnrStatusInfo.this, android.R.layout.simple_list_item_1, listPNR);
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
