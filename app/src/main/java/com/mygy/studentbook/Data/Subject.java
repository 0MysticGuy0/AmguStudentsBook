package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Subject {
    private String name;
    private String id;
    private Date startDate,endDate;
    private Integer[] countPerDays;
    private StudentGroup group;
    private HashMap<String, Object> subjectDoc;
    private static final ArrayList<Subject> allSubjects = new ArrayList<>();

    public Subject(String name, Integer[] countPerDays,  Date startDate, Date endDate, StudentGroup group) {
        this.name = name;
        this.countPerDays = countPerDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.group = group;
        group.addSubject(this);
        subjectDoc = new HashMap<>();

        allSubjects.add(this);

        updateAllDataInDoc();
    }

    public void updateAllDataInDoc(){
        subjectDoc.put(Constants.SUBJECT_NAME,name);
        subjectDoc.put(Constants.SUBJECT_ID,id);
        subjectDoc.put(Constants.SUBJECT_START_DATE,startDate);
        subjectDoc.put(Constants.SUBJECT_END_DATE,endDate);
        subjectDoc.put(Constants.SUBJECT_GROUP_ID,group.getId());
        subjectDoc.put(Constants.SUBJECT_COUNT_PER_DAYS, Arrays.asList(countPerDays));
    }

    public void setId(String id) {
        this.id = id;
        subjectDoc.put(Constants.SUBJECT_ID,id);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer[] getCountPerDays() {
        return countPerDays;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public HashMap<String, Object> getSubjectDoc() {
        return subjectDoc;
    }

    public static Subject getSubjectById(String id){
        for(Subject subject:allSubjects){
            if(subject.getId().equals(id))
                return subject;
        }
        return null;
    }
}
