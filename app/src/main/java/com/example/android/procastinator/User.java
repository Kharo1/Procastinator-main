package com.example.android.procastinator;

/**
 * Created by Kevin on 11/16/2017.
 */

public class User {
    private String username;
    private String phonenumber;
    private String email;

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

}
