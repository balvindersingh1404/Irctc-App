package com.example.railyatra.railyatra.pnrStatusActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.railyatra.railyatra.R;


public class PnrStatusActivity extends AppCompatActivity {

    EditText pnrEditText;
    Button PnrBtn;
    String pnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("PNRStatus");

        pnrEditText = (EditText) findViewById(R.id.pnrEditText);
        PnrBtn = (Button) findViewById(R.id.pnrBtn);

        PnrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pnr = pnrEditText.getText().toString();

                Intent intent = new Intent(PnrStatusActivity.this, PnrStatusInfo.class);
                intent.putExtra("Pnr", pnr);
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
            pnrEditText.setText("");
        }
    }
}
