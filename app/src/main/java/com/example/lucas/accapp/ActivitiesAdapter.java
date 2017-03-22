package com.example.lucas.accapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ActivitiesAdapter  extends ArrayAdapter<Activity>{

    public ActivitiesAdapter(Context context, ArrayList<Activity> activities) {
        super(context, 0, activities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Activity activity = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activities_list_item, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.tv_activity_id);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_activity_name);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_activity_time);

        tvId.setText(Integer.toString(activity.getId()));
        tvName.setText(activity.getName());
        tvTime.setText(Float.toString(activity.getTime()));

        return(convertView);
    }
}
