package com.mygy.studentbook.Data;

import com.google.common.collect.Comparators;
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
    private ArrayList<Subject> subjects;
    private Student headman;
    private static final ArrayList<StudentGroup> allStudentGroups;
    private static Comparator<Student> studentsComparator;
    private static Comparator<Subject> subjectsComparator;

    static {
        allStudentGroups = new ArrayList<>();
        studentsComparator = Comparator.comparing(Student::getFio);
        subjectsComparator = (s1, s2) -> {
            if (s1.getEndDate().before(s2.getEndDate())) {
                return 1;
            } else if (s1.getEndDate().equals(s2.getStartDate())){
                return s2.getName().compareTo(s1.getName());
            }else{
                return -1;
            }
        };
    }

    public StudentGroup(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        groupDoc = new HashMap<>();
        students = new ArrayList<>();
        subjects = new ArrayList<>();
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
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public Student getHeadman() {
        return headman;
    }

    public void addStudent(Student student){
        students.add(student);
        if(student.userType == User.UserType.HEADMAN){
            headman = student;
        }
        students.sort(studentsComparator);
    }
    public void removeStudentFromGroup(Student student){
        students.remove(student);
        if(headman == student) headman=null;
    }
    public void setHeadman(Student headman) {
        this.headman = headman;
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
        subjects.sort(subjectsComparator);
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
