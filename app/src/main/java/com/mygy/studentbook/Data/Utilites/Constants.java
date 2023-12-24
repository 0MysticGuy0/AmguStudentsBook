package com.mygy.studentbook.Data.Utilites;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Constants {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru","RU"));

    public static DatabaseActionListener IgnoringDatabaseActionListener = successful -> {};

    public static final String USERS_BASE = "users";
    public static final String GROUPS_BASE = "groups";
    public static final String SUBJECTS_BASE = "subjects";
    public static final String NOTES_BASE = "notes";


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

    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String SUBJECT_GROUP_ID = "subject_group_id";
    public static final String SUBJECT_COUNT_PER_DAYS = "count_per_days";
    public static final String SUBJECT_START_DATE = "subject_start_date";
    public static final String SUBJECT_END_DATE = "subject_end_date";

    public static final String NOTE_ID = "note_id";
    public static final String NOTE_MARK = "note_mark";
    public static final String NOTE_STUDENT_ID = "note_student_id";
    public static final String NOTE_SUBJECT_ID = "note_subject_id";

    public static final String NOTE_SUBJECT_NUMBER = "note_subject_number";
    public static final String NOTE_DATE = "note_date";
}
