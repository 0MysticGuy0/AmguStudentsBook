package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String id;
    protected String password;
    protected UserType userType;
    protected HashMap<String,Object> userDoc;
    private static ArrayList<User> allUsers = new ArrayList<>();
    protected ArrayList<Student> allStudents = new ArrayList<>();

    public User(String password, UserType userType) {
        this.password = password;
        this.userType = userType;
        userDoc = new HashMap<>();

        updateAllDataInDoc();

        allUsers.add(this);
    }

    public void updateAllDataInDoc(){
        userDoc.put(Constants.USER_ID,id);
        userDoc.put(Constants.USER_PASSWORD,password);
        userDoc.put(Constants.USER_TYPE,userType);
    }

    public void setId(String id) {
        this.id = id;
        userDoc.put(Constants.USER_ID,id);
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public UserType getUserType() {
        return userType;
    }

    public HashMap<String, Object> getUserDoc() {
        return userDoc;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public enum UserType implements Serializable {
        ADMIN,
        HEADMAN,//староста
        STUDENT
    }
}
