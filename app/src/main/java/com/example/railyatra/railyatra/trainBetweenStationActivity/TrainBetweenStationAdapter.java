package com.example.railyatra.railyatra.trainBetweenStationActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import java.util.ArrayList;

public class TrainBetweenStationAdapter extends BaseAdapter {
    ArrayList<TrainBetweenStation> listUsers;
    Context context;
    public TrainBetweenStationAdapter(Context context, ArrayList<TrainBetweenStation> listUsers){
        this.context = context;
        this.listUsers = listUsers;
    }
    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_train_between_station_info, null);
        TextView trainNameTv = (TextView)itemView.findViewById(R.id.trainNameTv);
        TextView depatureTv = (TextView)itemView.findViewById(R.id.depatureTimeTv);
        TextView fromCodeTv = (TextView)itemView.findViewById(R.id.fromCodeTv);
        TextView arrivalTv = (TextView)itemView.findViewById(R.id.arrivalTimeTv);
        TextView toCodeTv = (TextView)itemView.findViewById(R.id.toCodeTv);
        TextView travelTimeTv = (TextView)itemView.findViewById(R.id.travelTimeTv);

        trainNameTv.setText(listUsers.get(position).getTrainName());
        depatureTv.setText(listUsers.get(position).getDepatureTime());
        fromCodeTv.setText(listUsers.get(position).getFromCode());
        arrivalTv.setText(listUsers.get(position).getArrivalTime());
        toCodeTv.setText(listUsers.get(position).getToCode());
        travelTimeTv.setText(listUsers.get(position).getTravelTime());

        return itemView;
    }
}
