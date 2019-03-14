package com.example.kosmo_project.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kosmo_project.ChatActivity;
import com.example.kosmo_project.R;
import com.example.kosmo_project.httpcommunication.MyRetrofit;
import com.example.kosmo_project.vo.Lecture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class Lecture_ListViewAdapter extends BaseAdapter {
    private List<Lecture> lectures = null;

    public Transformation transformation = null;

    public Lecture_ListViewAdapter(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public int getCount() {
        return lectures.size();
    }

    @Override
    public Object getItem(int position) {
        return lectures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_timetablelistview, parent, false);

        transformation = new MaskTransformation(convertView.getContext(), R.drawable.rounded_image);

        TextView lecture_classtime = (TextView)convertView.findViewById(R.id.lecture_classtime);
        ImageView lecture_empimage = (ImageView)convertView.findViewById(R.id.lecture_empimage);
        TextView lecture_name = (TextView)convertView.findViewById(R.id.lecture_name);;
        TextView lecture_classroom = (TextView)convertView.findViewById(R.id.lecture_classroom);

        Lecture item = lectures.get(position);

        lecture_classtime.setText(item.getClassTime()+"교시");
        lecture_name.setText(item.getLectureName());
        lecture_classroom.setText(item.getClassRoom());

        setUserImage(item.getEmpImage(), lecture_empimage);
        return convertView;
    }

    public void setUserImage(String path, ImageView imageView) {
        Picasso.get().load(MyRetrofit.BASE_URL + "project/resources" + path)
                .transform(transformation)
                .into(imageView);
    }
}
