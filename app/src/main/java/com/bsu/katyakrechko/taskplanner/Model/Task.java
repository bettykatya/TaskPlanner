package com.bsu.katyakrechko.taskplanner.Model;

import android.support.annotation.NonNull;

import com.bsu.katyakrechko.taskplanner.Data.DataHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by katyakrechko@gmail.com
 */

public class Task {
    int task_id;
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
        task_id = 1;
        task_name = name;
        task_description = DEFAULT_STRING;
        task_date = new Date();
        task_status = Status.TASK_TO_DO;
        tags = new ArrayList<Tag>();
    }

    public Task(String name, String description, Date day){
        task_id = 1;
        task_name = name;
        task_description = description;
        task_date = day;
        task_status = Status.TASK_TO_DO;
        tags = new ArrayList<Tag>();
    }

    public Task(String name, String description, Date day, Status status, ArrayList <Tag> tagList){
        task_id = 1;
        task_name = name;
        task_description = description;
        task_date = day;
        task_status = status;
        tags = tagList;
    }



    public int getTaskID(){
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
