package com.example.railyatra.railyatra.mainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.railyatra.railyatra.R;
import com.example.railyatra.railyatra.aboutUsActivity.AboutUsActivity;
import com.example.railyatra.railyatra.liveTrainStatusActivity.LiveTrainStatusActivity;
import com.example.railyatra.railyatra.pnrStatusActivity.PnrStatusActivity;
import com.example.railyatra.railyatra.seatAvailabilityActivity.SeatAvailabilityActivity;
import com.example.railyatra.railyatra.trainArrivalActivity.TrainArrivalActivity;
import com.example.railyatra.railyatra.trainBetweenStationActivity.TrainBetweenStationActivity;
import com.example.railyatra.railyatra.trainFareEnquiryActivity.TrainFareEnquiryActivity;
import com.example.railyatra.railyatra.trainRunningDayActivity.TrainRunningDayActivity;

public class MainActivity extends AppCompatActivity {


    public static String[] gridViewStrings = {
            "Search Train",
            "PNR Status",
            "Live Train Status",
            "Seat Availability",
            "Train Running Day",
            "Fare Enquiry",
            "Train Arrivals",
            "About Us"
    };
    public static int[] gridViewImages = {
            R.drawable.trainbetweenstation,
            R.drawable.pnrstatus,
            R.drawable.livestatus,
            R.drawable.seat,
            R.drawable.running,
            R.drawable.fare,
            R.drawable.arrive,
            R.drawable.aboutus
    };
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation of colors

//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
//        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(2500);
//        animationDrawable.setExitFadeDuration(5000);
//        animationDrawable.start();

        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(new GridViewAdapter(this, gridViewStrings, gridViewImages));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), TrainBetweenStationActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), PnrStatusActivity.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), LiveTrainStatusActivity.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), SeatAvailabilityActivity.class);
                        startActivity(intent3);
                        break;

                    case 4:
                        Intent intent6 = new Intent(getApplicationContext(), TrainRunningDayActivity.class);
                        startActivity(intent6);
                        break;

                    case 5:
                        Intent intent7 = new Intent(getApplicationContext(), TrainFareEnquiryActivity.class);
                        startActivity(intent7);
                        break;

                    case 6:
                        Intent intent8 = new Intent(getApplicationContext(), TrainArrivalActivity.class);
                        startActivity(intent8);
                        break;

                    case 7:
                        Intent intent11 = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(intent11);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_fb:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.custom_helpline, null))
                        .setTitle("HelpLine")
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                break;

//                Intent intent11 = new Intent(getApplicationContext(), AboutUsActivity.class);
//                startActivity(intent11);
//                break;

        }

        return true;
    }
}
