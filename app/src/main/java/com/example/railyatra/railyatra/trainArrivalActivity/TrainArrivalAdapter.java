package com.example.railyatra.railyatra.trainArrivalActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import java.util.ArrayList;

public class TrainArrivalAdapter extends BaseAdapter {

    ArrayList<TrainArrival> listArrive;
    Context context;
    public TrainArrivalAdapter(Context context, ArrayList<TrainArrival> listArrive){
        this.context = context;
        this.listArrive = listArrive;
    }


    @Override
    public int getCount() {
        return listArrive.size();
    }

    @Override
    public Object getItem(int position) {
        return listArrive.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_train_arrive_info, null);

        TextView trainNameTv = (TextView)itemView.findViewById(R.id.trainNameTv);
        TextView scheduledArriveTv = (TextView)itemView.findViewById(R.id.scheduledArriveTimeTv);
        TextView scheduledDepatureTv = (TextView)itemView.findViewById(R.id.scheduledDepatureTimeTv);
        TextView actualArriveTv = (TextView)itemView.findViewById(R.id.actualArriveTimeTv);
        TextView actualDepatureTv = (TextView)itemView.findViewById(R.id.actualDepatureTimeTv);
        TextView delayArriveTv = (TextView)itemView.findViewById(R.id.delayArriveTimeTv);
        TextView delayDepatureTv = (TextView)itemView.findViewById(R.id.delayDepatureTimeTv);

        trainNameTv.setText(listArrive.get(position).getTrainName());
        scheduledArriveTv.setText(listArrive.get(position).getSch_arrive());
        scheduledDepatureTv.setText(listArrive.get(position).getSch_depature());
        actualArriveTv.setText(listArrive.get(position).getActual_arrive());
        actualDepatureTv.setText(listArrive.get(position).getActual_depature());
        delayArriveTv.setText(listArrive.get(position).getDelay_arrive());
        delayDepatureTv.setText(listArrive.get(position).getDelay_depature());

        return itemView;
    }
}
