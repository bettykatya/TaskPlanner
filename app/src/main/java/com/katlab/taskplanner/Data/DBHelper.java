package com.katlab.taskplanner.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.katlab.taskplanner.Data.DataHelper;

/**
 * Created by katyakrechko@gmail.com
 */
public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "TaskPlanner.db";
    private static int DATABASE_VERSION = 1;

    private static final String ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TAGS_TABLE =
            "CREATE TABLE " + DataHelper.TABLE_TAGS_NAME + " (" +
                    DataHelper.COLUMN_NAME_TAG_ID + ID_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TAG_NAME + TEXT_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TAG_TYPE + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_TAGS_TABLE =
            "DROP TABLE IF EXISTS " + DataHelper.TABLE_TAGS_NAME;

    private static final String SQL_CREATE_TASKS_TABLE =
            "CREATE TABLE " + DataHelper.TABLE_TASKS_NAME + " (" +
                    DataHelper.COLUMN_NAME_TASK_ID + ID_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TASK_NAME + TEXT_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TASK_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TASK_DATE + TEXT_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TASK_STATUS + TEXT_TYPE + COMMA_SEP +
                    DataHelper.COLUMN_NAME_TAGS + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_TASKS_TABLE =
            "DROP TABLE IF EXISTS " + DataHelper.TABLE_TASKS_NAME;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TAGS_TABLE);
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Clear all data
        db.execSQL(SQL_DELETE_TAGS_TABLE);
        db.execSQL(SQL_DELETE_TASKS_TABLE);

        //recreate tables
        onCreate(db);
    }
}
