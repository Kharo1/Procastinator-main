package com.example.android.procastinator;

/**
 * Created by Kevin on 11/16/2017.
 */

public class User {
    private String username;
    private String phonenumber;
    private String email;
    private String dueDate;
    private String courseName;
    private String taskName;

    public User(){

    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPhoneNumber(){
        return phonenumber;
    }
    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
    public String getDueDate(){
        return dueDate;
    }
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }
    public String getCourseName(){
        return courseName;
    }
    public void setTaskName(String taskName){
        this.taskName = taskName;
    }
    public String getTaskName(){
        return taskName;
    }
    public void setUserInformation(String task){
        String removeFirstLast = task.substring(1, task.length()-1);
        String[] array = removeFirstLast.split(",");
        dueDate = array[0].substring(9, array[0].length());
        taskName = array[1].substring(11, array[1].length());
        courseName = array[2].substring(13, array[2].length());
    }

}
