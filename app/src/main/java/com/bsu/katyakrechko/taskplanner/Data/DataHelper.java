package com.bsu.katyakrechko.taskplanner.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bsu.katyakrechko.taskplanner.Model.Tag;
import com.bsu.katyakrechko.taskplanner.Model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by katyakrechko@gmail.com
 */

//TODO вынести считывание таска из строки в отдельный метод, передавать туда курсор

public class DataHelper {
    static DBHelper dbHelper;
    static SQLiteDatabase db;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    //names for tables and columns in database
    public static final String TABLE_TAGS_NAME = "tags";
    public static final String COLUMN_NAME_TAG_ID = "tagID";
    public static final String COLUMN_NAME_TAG_NAME = "tagName";
    public static final String COLUMN_NAME_TAG_TYPE = "tagType";

    public static final String TABLE_TASKS_NAME = "tasks";
    public static final String COLUMN_NAME_TASK_ID = "taskID";
    public static final String COLUMN_NAME_TASK_NAME = "taskName";
    public static final String COLUMN_NAME_TASK_DESCRIPTION = "taskDescription";
    public static final String COLUMN_NAME_TASK_DATE = "taskDate";
    public static final String COLUMN_NAME_TASK_STATUS = "taskStatus";
    public static final String COLUMN_NAME_TAGS = "taskTags";
    public static final String COLUMN_NAME_PROJECT_TAGS = "tagsProject";

    //variables for queries
    private static String table = TABLE_TASKS_NAME;
    private static String [] columns = {
            COLUMN_NAME_TASK_ID,COLUMN_NAME_TASK_NAME,COLUMN_NAME_TASK_DESCRIPTION,
            COLUMN_NAME_TASK_DATE,COLUMN_NAME_TASK_STATUS,COLUMN_NAME_TAGS};
    private static String selection = null;
    private static String[] selectionArgs = null;
    private static String groupBy = null;
    private static String having = null;
    private static String orderBy = null;

    public static void openDB(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase(); //change to call this async!!!
    }
    public static void closeDB(){
        dbHelper.close();
    }

    public static void addTask(Task task){
        int task_id = task.getTaskID();
        String task_name = task.getTaskName();
        String task_description = task.getTaskDescription();
        String task_date = task.getTaskDateString();
        Task.Status task_status = task.getTaskStatus(); // bullet, cross, etc.
        ArrayList <Tag> tags = task.getTaskTags();
        String tagsString = getStringFromTags(tags);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TASK_ID, task_id);
        values.put(COLUMN_NAME_TASK_NAME, task_name);
        values.put(COLUMN_NAME_TASK_DESCRIPTION, task_description);
        values.put(COLUMN_NAME_TASK_DATE, task_date);
        values.put(COLUMN_NAME_TASK_STATUS, task_status.name());
        values.put(COLUMN_NAME_TAGS, tagsString);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(TABLE_TASKS_NAME, null, values);
    }

    public static ArrayList <Task> getAllTasks(){
        ArrayList <Task> allTasks = new ArrayList<Task>();
        Cursor c = db.query( table, columns, selection, selectionArgs, groupBy, having, orderBy);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String task_name = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_NAME));
                        String task_description = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DESCRIPTION));
                        String task_date_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DATE));
                        Date task_date = DATE_FORMAT.parse(task_date_str);
                        /*String task_status = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_STATUS));
                        ArrayList<Tag> tags = new ArrayList<>();
                        */
                        Task task = new Task(task_name, task_description, task_date/*, task_status,tags*/);
                        allTasks.add(task);
                    } catch (Exception e){Log.e("error", "error-getAllTasks");}
                } while (c.moveToNext());
            }
            c.close();
        }
        return allTasks;
    }
    public static ArrayList <Task> getDayTasks(Date day){
        ArrayList <Task> tasks = new ArrayList<Task>();
        String selection1 = " " + COLUMN_NAME_TASK_DATE + " = '" + DATE_FORMAT.format(day) + "'";
        Cursor c = db.query( table, columns, selection1, selectionArgs, groupBy, having, orderBy);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String task_name = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_NAME));
                        String task_description = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DESCRIPTION));
                        String task_date_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DATE));
                        String task_status_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_STATUS));
                        Task.Status task_status = Task.Status.valueOf(task_status_str);
                        ArrayList<Tag> tags = new ArrayList<>();
                        Date task_date = DATE_FORMAT.parse(task_date_str);
                        Task task = new Task(task_name, task_description, task_date, task_status,tags);
                        tasks.add(task);
                    } catch (Exception e){Log.e("error", "getDayTasks");}
                } while (c.moveToNext());
            }
            c.close();
        }

        return tasks;
    }
    public static ArrayList <Task> getWeekTasks(Date weekDay){
        ArrayList <Task> tasks = new ArrayList<Task>();

        Calendar cal_start_week = GregorianCalendar.getInstance();
        cal_start_week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Calendar cal_end_week = GregorianCalendar.getInstance();
        cal_end_week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal_end_week.add(Calendar.DATE, 6);

        Cursor c = db.query( table, columns, selection, selectionArgs, groupBy, having, orderBy);
        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String task_name = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_NAME));
                        String task_description = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DESCRIPTION));
                        String task_date_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DATE));
                        String task_status_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_STATUS));
                        Task.Status task_status = Task.Status.valueOf(task_status_str);
                        ArrayList<Tag> tags = new ArrayList<>();
                        Date task_date = DATE_FORMAT.parse(task_date_str);

                        Calendar task_cal = GregorianCalendar.getInstance();
                        task_cal.setTime(task_date);

                        if(task_cal.after(cal_start_week) && task_cal.before(cal_end_week)){
                            Task task = new Task(task_name, task_description, task_date, task_status,tags);
                            tasks.add(task);
                        }
                    } catch (Exception e){Log.e("error", "parse date error - getWeekTasks");}
                } while (c.moveToNext());
            }
            c.close();
        }
        return tasks;
    }
    public static ArrayList <Task> getMonthTasks(Date monthDay){
        ArrayList <Task> tasks = new ArrayList<Task>();

        String monthDayString = DATE_FORMAT.format(monthDay);
        String month = monthDayString.substring(3,5);

        String selection1 = " " + COLUMN_NAME_TASK_DATE + " LIKE '__." + month + ".____'";
        Log.i("info","selection = " + selection1);

        Cursor c = db.query( table, columns, selection1, selectionArgs, groupBy, having, orderBy);
        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String task_name = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_NAME));
                        String task_description = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DESCRIPTION));
                        String task_date_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_DATE));
                        String task_status_str = c.getString(c.getColumnIndex(COLUMN_NAME_TASK_STATUS));
                        Task.Status task_status = Task.Status.valueOf(task_status_str);
                        ArrayList<Tag> tags = new ArrayList<>();
                        Date task_date = DATE_FORMAT.parse(task_date_str);
                        Task task = new Task(task_name, task_description, task_date, task_status,tags);
                        tasks.add(task);
                    } catch (Exception e){Log.e("error", "parse date error - getMonthTasks");}
                } while (c.moveToNext());
            }
            c.close();
        }
        return tasks;
    }

    public static void addTag(Tag tag){

    }

    public static void deleteTask(){

    }

    public static void deleteTag(){

    }

    public static String getStringFromTags (ArrayList <Tag> tags){
        String str = "";
        for (Tag t : tags ) {
            str += t.getTagID() + ",";
        }
        return str;
    }

    public static ArrayList <Tag> getTagsFromString(String str){
        ArrayList <Tag> tags = new ArrayList<>();

        return tags;
    }

}
