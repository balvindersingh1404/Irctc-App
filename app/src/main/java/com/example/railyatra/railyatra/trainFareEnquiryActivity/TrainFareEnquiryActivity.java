package com.example.railyatra.railyatra.trainFareEnquiryActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.railyatra.railyatra.R;
import com.example.railyatra.railyatra.util.AutoCompleteStationAdapter;
import com.example.railyatra.railyatra.util.AutoCompleteTrainAdapter;

public class TrainFareEnquiryActivity extends AppCompatActivity {
    Button findTrainFareBtn;
    EditText dojEditText, ageEditText;
    String date;
    String quota;
    Spinner spinner;
    ArrayAdapter<CharSequence> staticAdapter;
    AutoCompleteTextView stationSearch1;
    AutoCompleteTextView stationSearch2;
    AutoCompleteTextView trainNameOrNumber;
    AutoCompleteStationAdapter adapter1;
    AutoCompleteStationAdapter adapter2;
    AutoCompleteTrainAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_fare_enquiry);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("FareEnquiry");

        spinner = (Spinner) findViewById(R.id.spinnerQuata);
        ageEditText = (EditText) findViewById(R.id.ageEdit);
        dojEditText = (EditText) findViewById(R.id.dojEdit);
        findTrainFareBtn = (Button) findViewById(R.id.findTrainFareBtn);
        stationSearch1 = (AutoCompleteTextView) findViewById(R.id.search1);
        adapter1 = new AutoCompleteStationAdapter(this, android.R.layout.simple_dropdown_item_1line);
        stationSearch1.setAdapter(adapter1);

        staticAdapter = ArrayAdapter.createFromResource(this, R.array.quota_array, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quota = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //when autocomplete is clicked
        stationSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stationName1 = adapter1.getItem(position).getName();
                stationSearch1.setText(stationName1);

            }
        });

        stationSearch2 = (AutoCompleteTextView) findViewById(R.id.search2);
        adapter2 = new AutoCompleteStationAdapter(this, android.R.layout.simple_dropdown_item_1line);
        stationSearch2.setAdapter(adapter2);

        //when autocomplete is clicked
        stationSearch2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stationName2 = adapter2.getItem(position).getName();
                stationSearch2.setText(stationName2);

            }
        });

        trainNameOrNumber = (AutoCompleteTextView) findViewById(R.id.trainNameOrNumberAutocomplete);
        adapter3 = new AutoCompleteTrainAdapter(this, android.R.layout.simple_dropdown_item_1line);
        trainNameOrNumber.setAdapter(adapter3);

        //when autocomplete is clicked
        trainNameOrNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String trainName = adapter3.getItem(position).getName();
                trainNameOrNumber.setText(trainName);

            }
        });

// open data picker on dob field click
        dojEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                DatePickerDialog dialog = new DatePickerDialog(TrainFareEnquiryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dojEditText.setText(date);
                    }
                }, 2017, 8, 5);
                dialog.show();
            }
        });

        findTrainFareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = stationSearch1.getText().toString();
                String name2 = stationSearch2.getText().toString();
                String name3 = trainNameOrNumber.getText().toString();

                String fromCode, toCode, trainNumber;

                if (name1.contains("(") || name1.contains(")")) {
                    fromCode = name1.substring(name1.indexOf("(") + 1, name1.indexOf(")"));
                } else {
                    fromCode = name1;
                }


                if (name2.contains("(") || name2.contains(")")) {
                    toCode = name2.substring(name2.indexOf("(") + 1, name2.indexOf(")"));
                } else {
                    toCode = name2;
                }


                if (name3.contains("(") || name3.contains(")")) {
                    trainNumber = name3.substring(name3.indexOf("(") + 1, name3.indexOf(")"));
                } else {
                    trainNumber = name3;
                }

                String age = ageEditText.getText().toString();
                String doj = dojEditText.getText().toString();
                String quotaInput = quota.substring(quota.indexOf("(") + 1, quota.indexOf(")"));

                Intent intent = new Intent(TrainFareEnquiryActivity.this, TrainFareEnquiryInfo.class);

                intent.putExtra("TrainNumber", trainNumber);
                intent.putExtra("FromCode", fromCode);
                intent.putExtra("ToCode", toCode);
                intent.putExtra("Age", age);
                intent.putExtra("Quota", quotaInput);
                intent.putExtra("Doj", doj);

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
            stationSearch2.setText("");
            trainNameOrNumber.setText("");
            dojEditText.setText("");
            ageEditText.setText("");
        }
    }
}
