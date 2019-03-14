package com.example.kosmo_project.listviewadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.kosmo_project.R;

import java.util.ArrayList;
import java.util.List;

public class Recv_ListViewAdapter extends BaseAdapter {
    private List<ListViewItem> items = null;

    public Recv_ListViewAdapter(List<ListViewItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /*if(convertView!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_weatherlistview, parent, false);
        }*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_weatherlistview, parent, false);

        TextView weather_time = (TextView)convertView.findViewById(R.id.weather_time);
        ImageView weather_image = (ImageView)convertView.findViewById(R.id.weather_image);
        TextView weather_contents = (TextView)convertView.findViewById(R.id.weather_contents);
        TextView weather_regulator = (TextView)convertView.findViewById(R.id.weather_regulator);

        ListViewItem item = (ListViewItem)getItem(position);

        weather_time.setText(item.getTime());
        weather_image.setImageResource(item.getWeatherType().getImageId());
        weather_contents.setText(item.getWeatherType().getKoValue());
        weather_regulator.setText(item.getRegulator());

        return convertView;
    }
}
