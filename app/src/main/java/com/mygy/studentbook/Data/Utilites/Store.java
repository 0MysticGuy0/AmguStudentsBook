package com.mygy.studentbook.Data.Utilites;

import com.mygy.studentbook.Data.StudentGroup;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Store {
    private static HashSet<String> usedPasswords;
    private static ArrayList<StudentGroup> studentGroups;

    static {
        usedPasswords = new HashSet<>();
    }

    public static void setUsedPasswords(HashSet<String> usedPasswords) {
        Store.usedPasswords = usedPasswords;
    }
    public static boolean addUsedPassword(String password){ //returns false if password has already been used by someone
        if(usedPasswords.contains(password)){
            return false;
        }else{
            usedPasswords.add(password);
            return true;
        }
    }

    public static ArrayList<StudentGroup> getStudentGroups() {
        return studentGroups;
    }
}
