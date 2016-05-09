package com.katlab.taskplanner.Model;

/**
 * Created by katyakrechko@gmail.com
 */

public class Tag {
    private int tag_id;
    private String tag_name;
    private String tag_type;//project_name, other custom tags


    public int getTagID(){
        return tag_id;
    }
}
