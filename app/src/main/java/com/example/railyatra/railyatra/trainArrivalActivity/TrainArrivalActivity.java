package com.example.railyatra.railyatra.trainArrivalActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.railyatra.railyatra.R;
import com.example.railyatra.railyatra.util.AutoCompleteStationAdapter;


public class TrainArrivalActivity extends AppCompatActivity {
    Button findTrainArriveBtn;
    Spinner spinnerHour;
    ArrayAdapter<CharSequence> hourAdapter;
    String hour;
    AutoCompleteTextView stationSearch1;
    AutoCompleteStationAdapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrival);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("TrainArrivals");

        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        findTrainArriveBtn = (Button) findViewById(R.id.findTrainArriveBtn);

        hourAdapter = ArrayAdapter.createFromResource(this, R.array.hour_array, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(hourAdapter);
        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hour = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stationSearch1 = (AutoCompleteTextView) findViewById(R.id.search1);
        adapter1 = new AutoCompleteStationAdapter(this, android.R.layout.simple_dropdown_item_1line);
        stationSearch1.setAdapter(adapter1);

        //when autocomplete is clicked
        stationSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stationName1 = adapter1.getItem(position).getName();
                stationSearch1.setText(stationName1);

            }
        });

        findTrainArriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = stationSearch1.getText().toString();
                String stationCode;
                if(name1.contains("(")|| name1.contains(")")){
                     stationCode = name1.substring(name1.indexOf("(") + 1, name1.indexOf(")"));

                }
                else{
                    stationCode=name1;
                }
                String hourStr = hour.substring(hour.indexOf("(") + 1, hour.indexOf(")"));

                Intent intent = new Intent(TrainArrivalActivity.this, TrainArrivalInfo.class);

                intent.putExtra("StationCode", stationCode);
                intent.putExtra("HourValue", hourStr);

                startActivityForResult(intent, 101);

            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            stationSearch1.setText("");
        }

    }
}
