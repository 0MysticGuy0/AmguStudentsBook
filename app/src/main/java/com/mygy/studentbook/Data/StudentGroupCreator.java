package com.mygy.studentbook.Data;

import com.google.firebase.Timestamp;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.Data.Utilites.DatabaseActionListener;

import java.util.Date;
import java.util.HashMap;

public abstract class StudentGroupCreator {
//    private static DatabaseActionListener ignoringListener;
//
//    static{
//        ignoringListener = successful -> {
//
//        };
//    }


    public static StudentGroup createStudentGroup(String name, Date start, Date end){
        for(StudentGroup gr:StudentGroup.getAllStudentGroups()){
            if(gr.getName().equals(name)){
                return  null;
            }
        }

        StudentGroup group = new StudentGroup(name,start,end);
        DataBaseHelper.addGroupToBase(group, success -> {
            DataBaseHelper.updateGroupDataInBase(group,Constants.IgnoringDatabaseActionListener);
        });
        return group;
    }

    public static StudentGroup retrieveGroupFromDoc(HashMap<String,Object> doc){
        StudentGroup group = null;
        try{
            String id = (String) doc.get(Constants.GROUP_ID);
            String name = (String) doc.get(Constants.GROUP_NAME);
            Date start = ((Timestamp) doc.get(Constants.GROUP_START_DATE)).toDate();
            Date end = ((Timestamp) doc.get(Constants.GROUP_END_DATE)).toDate();

            group = new StudentGroup(name,start,end);
            group.setId(id);
        }catch (Exception ex){
            return null;
        }
        return group;
    }
}
