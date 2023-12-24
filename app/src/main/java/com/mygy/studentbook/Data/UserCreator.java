package com.mygy.studentbook.Data;

import com.google.firebase.Timestamp;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.Data.Utilites.DatabaseActionListener;
import com.mygy.studentbook.Data.Utilites.PasswordGenerator;
import com.mygy.studentbook.Data.Utilites.Store;
import com.mygy.studentbook.Data.Utilites.UserPasswordGenerator;

import java.util.Date;
import java.util.HashMap;

public abstract class UserCreator {

//    private static DatabaseActionListener ignoringListener;
//
//    static{
//        ignoringListener = successful -> {
//
//        };
//    }

    public static User createAdmin(String passwd) {
        User user = new User(passwd, User.UserType.ADMIN);
        DataBaseHelper.addUserToBase(user, successful -> {
            if (successful) {
                DataBaseHelper.updateUserDataInBase(user, Constants.IgnoringDatabaseActionListener);
            }
        });
        return user;
    }

    public static Student createStudent(String fio, Date birthDate, StudentGroup group) {
        String passwd;
        PasswordGenerator passwordGenerator = new UserPasswordGenerator();
        do {
            passwd = passwordGenerator.generate(6);
        } while (!Store.addUsedPassword(passwd));

        Student student = new Student(passwd, fio, birthDate, group);
        DataBaseHelper.addUserToBase(student, successful -> {
            DataBaseHelper.updateUserDataInBase(student, Constants.IgnoringDatabaseActionListener);
        });
        return student;
    }
    public static void deleteStudent(Student student){
        Store.removeUsedPassword(student.getPassword());
        User.getAllUsers().remove(student);
        Student.getAllStudents().remove(student);
        student.getStudentGroup().removeStudentFromGroup(student);
        DataBaseHelper.removeUserFromBase(student,Constants.IgnoringDatabaseActionListener);

        for(Note note:student.getNotes()){
            NoteCreator.deleteNote(note);
        }
        student.getNotes().clear();
    }

    public static User retrieveUserFromDoc(HashMap<String, Object> doc) {
        User user;
        try {
            String id = (String) doc.get(Constants.USER_ID);
            String passwd = (String) doc.get(Constants.USER_PASSWORD);
            User.UserType type = User.UserType.valueOf((String) doc.get(Constants.USER_TYPE));

            if (type == User.UserType.ADMIN) {
                user = new User(passwd, type);
            } else
                user = retrieveStudentFromDoc(passwd, type, doc);

            user.setId(id);
        } catch (Exception ex) {
            return null;
        }

        Store.addUsedPassword(user.getPassword());
        return user;
    }

    private static Student retrieveStudentFromDoc(String passwd, User.UserType userType, HashMap<String, Object> doc) {
        Student student;
        try {
            String fio = (String) doc.get(Constants.USER_FIO);
            Date birthDate = ((Timestamp) doc.get(Constants.USER_BIRTHDATE)).toDate();
            String groupId = (String) doc.get(Constants.USER_GROUP_ID);
            StudentGroup group = StudentGroup.getStudentGroupById(groupId);

            if (group == null)
                return null;
            student = new Student(passwd, fio, birthDate, userType, group);
        } catch (Exception ex) {
            return null;
        }
        return student;
    }
}
