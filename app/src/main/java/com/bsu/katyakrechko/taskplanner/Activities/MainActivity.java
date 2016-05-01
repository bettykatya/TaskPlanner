package com.bsu.katyakrechko.taskplanner.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.bsu.katyakrechko.taskplanner.Adapters.TaskAdapter;
import com.bsu.katyakrechko.taskplanner.Data.DataHelper;
import com.bsu.katyakrechko.taskplanner.Model.Tag;
import com.bsu.katyakrechko.taskplanner.Model.Task;
import com.bsu.katyakrechko.taskplanner.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by katyakrechko@gmail.com
 */

//TODO добавить слайдер (ViewFlipper) для списков. листать вправо/влево для след/пред дня/недели/месяца
//TODO поиск по таскам текст/дата/теги
//TODO не вызывать onCreate при изменении ориентации
//TODO парсить разные форматы даты
//TODO обновление списка перетягиванием вниз
//TODO изображения для кнопки и тасков
//TODO удаление таска
//TODO редактирование таска
//TODO просмотр инфы о таске
//TODO теги
//TODO play market
//TODO добавление новых статусов тасков
//TODO пользовательские настройки (начало недели, )
//TODO языки и локализация
//TODO добавление проектов-тегов в меню
//TODO рефакторинг
//TODO сортировка тасков по времени для недели/месяца

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Task.Status status_to_do = Task.Status.TASK_TO_DO;

    private ViewGroup incl;
    private FloatingActionButton fab;
    private TaskAdapter adapter;
    ArrayList <Task> tasksToShow;
    private Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        today = new Date();
        incl = (ViewGroup) findViewById(R.id.include_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        try{
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    openAddTaskView();
                }
        });} catch(Exception e){Log.e("error","fab exception");}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* Date day = new Date();
        try {
            day = DataHelper.DATE_FORMAT.parse("10.10.2015");
        } catch(Exception e ){Log.e("error","error-onCreate-parseDate");}*/

        DataHelper.openDB(this);
        //Task task = new Task("task1","description1", day);
        //DataHelper.addTask(task);
        tasksToShow = DataHelper.getAllTasks();
        openListTaskView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //TODO change to switch-case construction
        if (id == R.id.menu_day_tasks) {
            tasksToShow = DataHelper.getDayTasks(today);
            openListTaskView();
        } else if (id == R.id.menu_week_tasks) {
            tasksToShow = DataHelper.getWeekTasks(today);
            openListTaskView();
        } else if (id == R.id.menu_month_tasks) {
            tasksToShow = DataHelper.getMonthTasks(today);
            openListTaskView();
        } else if (id == R.id.menu_all_tasks) {
            tasksToShow = DataHelper.getAllTasks();
            openListTaskView();
        } else if (id == R.id.project_job) {

        } else if (id == R.id.project_home) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openAddTaskView(){
        incl.removeAllViews();
        LayoutInflater ltInflater = getLayoutInflater();
        ltInflater.inflate(R.layout.view_add_task,incl, true);
        setOnclickLisenerAddTask();
        fab.hide();
    }
    public void setOnclickLisenerAddTask(){
        Button button = (Button) findViewById(R.id.add_task_button);
        button.setOnClickListener(addTaskListener);
    }
    OnClickListener addTaskListener = new OnClickListener() {
        @Override
        public void onClick(View v) {//TODO add try-catch blocks
            EditText taskNameEdit = (EditText) findViewById(R.id.add_task_name_edit);
            String taskName = taskNameEdit.getText().toString();

            EditText taskDateEdit = (EditText) findViewById(R.id.add_task_date_edit);
            String taskDateString = taskDateEdit.getText().toString();
            Date day = new Date();
            try {
                day = DataHelper.DATE_FORMAT.parse(taskDateString);
            } catch (Exception e){
                Log.e("error","error-addTaskListener-parseDate");//TODO add message for user that date was changed to today
            }

            EditText taskDecriptionEdit = (EditText) findViewById(R.id.add_task_description_edit);
            String taskDescription = taskDecriptionEdit.getText().toString();

            DataHelper.addTask(new Task(taskName, taskDescription, day, status_to_do,new ArrayList<Tag>()));
            tasksToShow = DataHelper.getDayTasks(today);
            openListTaskView();
        }
    };

    private void openListTaskView(){
        incl.removeAllViews();
        LayoutInflater ltInflater = getLayoutInflater();
        ltInflater.inflate(R.layout.list_tasks,incl, true);
        fab.show();
        adapter = new TaskAdapter(this,tasksToShow);
        ListView listView = (ListView) findViewById(R.id.tasks_list_view);
        listView.setAdapter(adapter);
    }


}
