package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class StudentGroup {
    private String name;
    private String id;
    private Date startDate,endDate;
    private HashMap<String, Object> groupDoc;
    private ArrayList<Student> students;
    private Student headman;
    private static final ArrayList<StudentGroup> allStudentGroups;
    private static Comparator<Student> nameComparator;

    static{
        allStudentGroups = new ArrayList<>();
        nameComparator = Comparator.comparing(Student::getFio);
    }

    public StudentGroup(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        groupDoc = new HashMap<>();
        students = new ArrayList<>();
        headman = null;

        updateAllDataInDoc();

        allStudentGroups.add(this);
    }

    public void updateAllDataInDoc(){
        groupDoc.put(Constants.GROUP_NAME,name);
        groupDoc.put(Constants.GROUP_ID,id);
        groupDoc.put(Constants.GROUP_START_DATE,startDate);
        groupDoc.put(Constants.GROUP_END_DATE,endDate);
    }

    public void setId(String id) {
        this.id = id;
        groupDoc.put(Constants.GROUP_ID,id);
    }

    public HashMap<String, Object> getGroupDoc() {
        return groupDoc;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
    public Student getHeadman() {
        return headman;
    }

    public void addStudent(Student student){
        students.add(student);
        if(student.userType == User.UserType.HEADMAN){
            headman = student;
        }
        students.sort(nameComparator);
    }
    public void setHeadman(Student headman) {
        this.headman = headman;
    }

    public static ArrayList<StudentGroup> getAllStudentGroups(){
        return allStudentGroups;
    }
    public static StudentGroup getStudentGroupById(String id){
        for(StudentGroup gr:allStudentGroups){
            if(gr.id.equals(id))
                return gr;
        }
        return null;
    }
}
