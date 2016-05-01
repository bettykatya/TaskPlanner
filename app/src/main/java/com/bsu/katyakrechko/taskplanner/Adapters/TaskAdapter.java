package com.bsu.katyakrechko.taskplanner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bsu.katyakrechko.taskplanner.Model.Task;
import com.bsu.katyakrechko.taskplanner.R;

import java.util.ArrayList;

/**
 * Created by katyakrechko@gmail.com
 */
public class TaskAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList <Task> objects;

    public TaskAdapter(Context context, ArrayList <Task> tasks) {
        ctx = context;
        objects = tasks;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_tasks_item, parent, false);
        }

        Task task = getTask(position);
        ((TextView) view.findViewById(R.id.task_name_label)).setText(task.getTaskName());
        ((TextView) view.findViewById(R.id.task_decription_label)).setText(task.getTaskDescription());
        ((TextView) view.findViewById(R.id.task_date_label)).setText(task.getTaskDateString());

        return view;
    }

    Task getTask(int position) {
        return ((Task) getItem(position));
    }
}
