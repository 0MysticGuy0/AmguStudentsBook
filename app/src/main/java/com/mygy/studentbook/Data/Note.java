package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;

import java.util.Date;
import java.util.HashMap;

public class Note {
    private String mark;
    private Student student;
    private Subject subject;
    private Date date;
    private String id;
    private int subjectNumber;
    private HashMap<String, Object> noteDoc;
    public static final String[] MARKS = {"-","Н","5","4","3","2"};

    public Note(String mark, Student student, Subject subject, int subjectNumber, Date date) {
        this.mark = mark;
        this.student = student;
        this.subject = subject;
        this.subjectNumber = subjectNumber;
        this.date = date;

        student.addNote(this);

        noteDoc = new HashMap<>();
        putAllDataInDoc();
    }

    public void putAllDataInDoc(){
        noteDoc.put(Constants.NOTE_ID,id);
        noteDoc.put(Constants.NOTE_MARK,mark);
        noteDoc.put(Constants.NOTE_STUDENT_ID,student.getId());
        noteDoc.put(Constants.NOTE_SUBJECT_ID,subject.getId());
        noteDoc.put(Constants.NOTE_SUBJECT_NUMBER,subjectNumber);
        noteDoc.put(Constants.NOTE_DATE,date);
    }

    public void setMark(String mark) {
        this.mark = mark;
        noteDoc.put(Constants.NOTE_MARK,mark);
    }

    public void setId(String id) {
        this.id = id;
        noteDoc.put(Constants.NOTE_ID,id);
    }

    public Subject getSubject() {
        return subject;
    }
    public String getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public int getSubjectNumber() {
        return subjectNumber;
    }
    public HashMap<String, Object> getNoteDoc() {
        return noteDoc;
    }
    public Student getStudent() {
        return student;
    }
    public String getMark() {
        return mark;
    }
}
