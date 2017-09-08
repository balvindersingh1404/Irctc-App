package com.example.railyatra.railyatra.trainFareEnquiryActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

import java.util.ArrayList;

public class TrainFareEnquiryAdapter extends BaseAdapter {
    ArrayList<TrainFare> listUsers;
    Context context;
    public TrainFareEnquiryAdapter(Context context, ArrayList<TrainFare> listUsers){
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
        View itemView = inflater.inflate(R.layout.custom_train_fare_enquiry_info, null);

        TextView quotaNameTv = (TextView)itemView.findViewById(R.id.quotaNameTv);
        TextView quotaCodeTv = (TextView)itemView.findViewById(R.id.quotaCodeTv);
        TextView fareTv = (TextView)itemView.findViewById(R.id.fareTv);

        quotaNameTv.setText(listUsers.get(position).getName());
        quotaCodeTv.setText(listUsers.get(position).getCode());
        fareTv.setText(listUsers.get(position).getFare());

        return itemView;
    }
}
