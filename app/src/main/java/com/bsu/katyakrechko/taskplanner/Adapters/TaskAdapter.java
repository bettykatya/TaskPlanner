package com.bsu.katyakrechko.taskplanner.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsu.katyakrechko.taskplanner.Activities.MainActivity;
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

        int image_id;
        switch(task.getTaskStatus()){
            case TASK_TO_DO:{
                image_id = R.mipmap.to_do;
                break;
            }
            case TASK_COMPLETED:{
                image_id = R.mipmap.completed;
                break;
            }
            case TASK_MIGRATED:{
                image_id = R.mipmap.migrated;
                break;
            }
            case TASK_NOT_ACTUAL:{
                image_id = R.mipmap.not_actual;
                break;
            }
            case NOTE:{
                image_id = R.mipmap.note;
                break;
            }
            default:{
                image_id = R.mipmap.to_do;
            }
        }

        ((TextView) view.findViewById(R.id.task_name_label)).setText(task.getTaskName());
        ((TextView) view.findViewById(R.id.task_decription_label)).setText(task.getTaskDescription());
        ((TextView) view.findViewById(R.id.task_date_label)).setText(task.getTaskDateString());
        ((ImageView) view.findViewById(R.id.task_status_image)).setImageResource(image_id);

        return view;
    }

    Task getTask(int position) {
        return ((Task) getItem(position));
    }
}
