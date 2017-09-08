package com.example.railyatra.railyatra.liveTrainStatusActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.railyatra.railyatra.R;
import com.example.railyatra.railyatra.util.AutoCompleteTrainAdapter;


public class LiveTrainStatusActivity extends AppCompatActivity {

    AutoCompleteTextView trainNameOrNumber;
    Button findLiveTrainBtn;
    AutoCompleteTrainAdapter adapter1;
    EditText dojEditText;
    String dateandmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("LiveTrainStatus");
        findLiveTrainBtn = (Button) findViewById(R.id.findLiveTrainBtn);

        trainNameOrNumber = (AutoCompleteTextView) findViewById(R.id.trainAutoComplete);
        adapter1 = new AutoCompleteTrainAdapter(this, android.R.layout.simple_dropdown_item_1line);
        trainNameOrNumber.setAdapter(adapter1);
        dojEditText = (EditText) findViewById(R.id.dojEdit);
        //when autocomplete is clicked
        trainNameOrNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String trainName = adapter1.getItem(position).getName();
                trainNameOrNumber.setText(trainName);
            }
        });

        // open data picker on dob field click
        dojEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                DatePickerDialog dialog = new DatePickerDialog(LiveTrainStatusActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String str = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dateandmonth = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dojEditText.setText(str);

                    }
                }, 2017, 8, 5);
                dialog.show();
            }
        });

        findLiveTrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainName = trainNameOrNumber.getText().toString();
                String trainNumber;
                if (trainName.contains("(") || trainName.contains(")")) {
                    trainNumber = trainName.substring(trainName.indexOf("(") + 1, trainName.indexOf(")"));
                } else {
                    trainNumber = trainNameOrNumber.getText().toString();
                }

                Intent intent = new Intent(LiveTrainStatusActivity.this, LiveTrainStatusInfo.class);
                intent.putExtra("Date", dateandmonth);
                intent.putExtra("TrainNumber", trainNumber);
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
            trainNameOrNumber.setText("");
            dojEditText.setText("");
        }
    }
}
