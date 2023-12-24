package com.mygy.studentbook.Data;

import com.google.firebase.Timestamp;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class SubjectCreator {
    public static Subject createSubject(String name, Integer[] countPerDays, Date startDate, Date endDate, StudentGroup group){
        for(Subject s: group.getSubjects()){
            if(s.getName().equals(name))
                return null;
        }

        Subject subject = new Subject(name,countPerDays,startDate,endDate,group);
        DataBaseHelper.addSubjectToBase(subject, success -> {
            DataBaseHelper.updateSubjectDataInBase(subject, Constants.IgnoringDatabaseActionListener);
        });
        return subject;
    }

    public static Subject retrieveSubjectFromDoc(HashMap<String,Object> doc){
        Subject subject = null;
        try{
            String id = (String) doc.get(Constants.SUBJECT_ID);
            String name = (String) doc.get(Constants.SUBJECT_NAME);
            Date start = ((Timestamp) doc.get(Constants.SUBJECT_START_DATE)).toDate();
            Date end = ((Timestamp) doc.get(Constants.SUBJECT_END_DATE)).toDate();
            Integer[] countPerDays = ((ArrayList<Long>) doc.get(Constants.SUBJECT_COUNT_PER_DAYS)).stream()
                    .map(Long::intValue)
                    .toArray(Integer[]::new);
            String groupId = (String) doc.get(Constants.SUBJECT_GROUP_ID);
            StudentGroup group = StudentGroup.getStudentGroupById(groupId);

            if (group == null)
                return null;

            subject = new Subject(name,countPerDays,start,end,group);
            subject.setId(id);
        }catch (Exception ex){
            return null;
        }
        return subject;
    }
}
