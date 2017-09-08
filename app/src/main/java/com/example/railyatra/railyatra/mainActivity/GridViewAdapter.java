package com.example.railyatra.railyatra.mainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.railyatra.railyatra.R;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final int[] Imageid;

    public GridViewAdapter(Context mContext, String[] string, int[] imageid) {
        this.mContext = mContext;
        this.string = string;
        Imageid = imageid;
    }


    @Override
    public int getCount() {
        return string.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {

        View grid;
        TextView textView;
        ImageView imageView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.custom_grid_view, null);
            textView = (TextView) grid.findViewById(R.id.text1);
            imageView = (ImageView) grid.findViewById(R.id.image1);
            textView.setText(string[p]);
            imageView.setImageResource(Imageid[p]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}
