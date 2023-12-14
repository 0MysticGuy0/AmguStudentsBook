package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Student extends User{
    private String fio;
    private Date birthdate;
    private StudentGroup studentGroup;

    public Student(String password, String fio, Date birthdate, StudentGroup studentGroup) {
        super(password, UserType.STUDENT);
        this.fio = fio;
        this.birthdate = birthdate;
        this.studentGroup = studentGroup;
        updateAllDataInDoc();
        studentGroup.addStudent(this);

        allStudents.add(this);
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }
    public String getFio() {
        return fio;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public int getAge(){
        return (int)(TimeUnit.DAYS.convert( Math.abs(new Date().getTime() - birthdate.getTime()), TimeUnit.MILLISECONDS)/365);
    }

    @Override
    public void updateAllDataInDoc() {
        super.updateAllDataInDoc();
        userDoc.put(Constants.USER_FIO,fio);
        userDoc.put(Constants.USER_BIRTHDATE,birthdate);
        if(studentGroup != null)
            userDoc.put(Constants.USER_GROUP_ID,studentGroup.getId());
        else
            userDoc.put(Constants.USER_GROUP_ID,null);
    }
}
