package com.example.railyatra.railyatra.liveTrainStatusActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import java.util.ArrayList;

public class LiveTrainStatusAdapter extends BaseAdapter {

    ArrayList<LiveTrain> listLive;
    Context context;
    public LiveTrainStatusAdapter(Context context, ArrayList<LiveTrain> listLive){
        this.context = context;
        this.listLive = listLive;
    }

    @Override
    public int getCount() {
        return listLive.size();
    }

    @Override
    public Object getItem(int position) {
        return listLive.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_live_train_info, null);

        TextView StationNameTv = (TextView)itemView.findViewById(R.id.stationNameTv);
        TextView scheduledArriveTimeTv = (TextView)itemView.findViewById(R.id.scheduledArriveTimeTv);
        TextView actualArriveTimeTv = (TextView)itemView.findViewById(R.id.actualArriveTimeTv);
        TextView scheduledDepatureTv = (TextView)itemView.findViewById(R.id.scheduledDepatureTimeTv);
        TextView actualDepatureTv = (TextView)itemView.findViewById(R.id.actualDepatureTimeTv);
        TextView lateminTv = (TextView)itemView.findViewById(R.id.lateminTv);

        StationNameTv.setText(listLive.get(position).getTrainStationInfo());
        scheduledArriveTimeTv.setText(listLive.get(position).getSch_arrive());
        actualArriveTimeTv.setText(listLive.get(position).getAct_arrive());
        scheduledDepatureTv.setText(listLive.get(position).getSch_depature());
        actualDepatureTv.setText(listLive.get(position).getAct_depature());
        lateminTv.setText(listLive.get(position).getLatemin());

        return itemView;
    }
}
