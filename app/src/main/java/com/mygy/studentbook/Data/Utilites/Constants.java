package com.mygy.studentbook.Data.Utilites;

import java.text.SimpleDateFormat;

public abstract class Constants {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static final String USERS_BASE = "users";
    public static final String GROUPS_BASE = "groups";


    public static final String USER_ID = "id";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TYPE = "type";
    public static final String USER_FIO = "fio";
    public static final String USER_BIRTHDATE = "birthdate";
    public static final String USER_GROUP_ID = "group_id";

    public static final String GROUP_ID = "group_id";
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_START_DATE = "group_start_date";
    public static final String GROUP_END_DATE = "group_end_date";
}
