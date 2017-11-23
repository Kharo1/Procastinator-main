package com.example.android.procastinator;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/21/2017.
 */

public class UserAdapter extends ArrayAdapter<User>{

    private final Context context;
    private final ArrayList<User> data;
    private final int layoutResourceId;
    public UserAdapter(Context context, int layoutResourceId, ArrayList<User> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.textView1 = (TextView)row.findViewById(R.id.task_title);
            holder.textView2 = (TextView)row.findViewById(R.id.due_date);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        User user = data.get(position);

        holder.textView1.setText(user.getTaskName());
        holder.textView2.setText(user.getDueDate() + "-  " + user.getCourseName());


        return row;
    }

    static class ViewHolder{
        TextView textView1;
        TextView textView2;
    }
}
