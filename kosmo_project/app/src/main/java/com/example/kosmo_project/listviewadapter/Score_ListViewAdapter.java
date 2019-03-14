package com.example.kosmo_project.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kosmo_project.R;
import com.example.kosmo_project.vo.StdScore;

import java.util.List;

public class Score_ListViewAdapter extends BaseAdapter {
    private List<StdScore> list = null;

    public Score_ListViewAdapter(List<StdScore> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_scorelistview, parent, false);

        TextView score_grade = (TextView)convertView.findViewById(R.id.score_grade);
        TextView score_semester = (TextView)convertView.findViewById(R.id.score_semester);
        TextView score_avgscore = (TextView)convertView.findViewById(R.id.score_avgscore);
        TextView score_passscore = (TextView)convertView.findViewById(R.id.score_passscore);
        TextView score_year = (TextView)convertView.findViewById(R.id.score_year);
        TextView score_leccount = (TextView)convertView.findViewById(R.id.score_leccount);

        StdScore item = (StdScore) list.get(position);

        int avgscore = Integer.parseInt(item.getPassScore());

        score_grade.setText(item.getGrade()+"학년");
        score_semester.setText(item.getSemester()+"학기");
        score_avgscore.setText("평균 학점 : "+item.getAvgscore());
        score_passscore.setText("이수 학점 : "+String.format("%3d", avgscore));
        score_year.setText(item.getYear()+"년도");
        score_leccount.setText("강의 수 : "+item.getLectureCount());

        return convertView;
    }
}
