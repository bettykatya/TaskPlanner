package com.katlab.taskplanner.Model;

import android.util.Log;

import com.katlab.taskplanner.Data.DataHelper;
import com.katlab.taskplanner.Model.Tag;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by katyakrechko@gmail.com
 */

public class Task {
    long task_id;
    private String task_name;
    private String task_description;
    private Date task_date;
    private Status task_status; // bullet, cross, etc.
    private ArrayList <Tag> tags;

    private final String DEFAULT_STRING = "";
    public static enum Status {   TASK_TO_DO, TASK_COMPLETED, TASK_MIGRATED,
                            TASK_NOT_ACTUAL, EVENT, NOTE, TO_EXPLORE };

    public Task(){

    }

    public Task(String name){
        task_name = name;
        task_description = DEFAULT_STRING;
        task_date = new Date();
        task_status = Status.TASK_TO_DO;
        tags = new ArrayList<Tag>();
    }

    public Task(String name, String description, Date day){
        task_name = name;
        task_description = description;
        task_date = day;
        task_status = Status.TASK_TO_DO;
        tags = new ArrayList<Tag>();
    }

    public Task(String task_id_str, String name, String description, Date day, Status status, ArrayList <Tag> tagList){
        task_id = Integer.parseInt(task_id_str);
        task_name = name;
        task_description = description;
        task_date = day;
        task_status = status;
        tags = tagList;
    }

    public void setId(long id){
        this.task_id = id;
        Log.i("info", "setId = " + Long.toString(id) + " task_id = " + task_id);
    }

    public long getTaskID(){
        return task_id;
    }
    public String getTaskName(){
        return task_name;
    }
    public String getTaskDescription(){
        return task_description;
    }
    public String getTaskDateString(){
        return DataHelper.DATE_FORMAT.format(task_date);
    }
    public Date getTaskDate(){
        return task_date;
    }
    public Status getTaskStatus(){
        return task_status;
    }
    public String getTaskTagsString(){
        String result = "";
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            int tag_id = tag.getTagID();
            result += "," + tag_id;
        }
        return result;
    }
    public ArrayList <Tag> getTaskTags(){
        return tags;
    }
}
