package com.mygy.studentbook.Data;

import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;

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
    public Student(String password, String fio, Date birthdate, UserType userType, StudentGroup studentGroup) {
        super(password, UserType.STUDENT);
        this.fio = fio;
        this.birthdate = birthdate;
        this.userType = userType;
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
    public void makeHeadman(){
        if(userType != UserType.HEADMAN){
            userType = UserType.HEADMAN;
            Student lastHeadman = studentGroup.getHeadman();
            if(lastHeadman != null){
                lastHeadman.makeStudent();
            }

            userDoc.put(Constants.USER_TYPE,userType);
            studentGroup.setHeadman(this);

            DataBaseHelper.updateUserDataInBase(this,Constants.IgnoringDatabaseActionListener);
        }
    }
    private void makeStudent(){
        userType = UserType.STUDENT;
        userDoc.put(Constants.USER_TYPE,userType);
        DataBaseHelper.updateUserDataInBase(this,Constants.IgnoringDatabaseActionListener);
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
