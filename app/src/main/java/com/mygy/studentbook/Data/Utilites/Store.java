package com.mygy.studentbook.Data.Utilites;

import com.mygy.studentbook.Data.StudentGroup;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Store {
    private static HashSet<String> usedPasswords;

    static {
        usedPasswords = new HashSet<>();
    }


    public static boolean addUsedPassword(String password){ //returns false if password has already been used by someone
        if(usedPasswords.contains(password)){
            return false;
        }else{
            usedPasswords.add(password);
            return true;
        }
    }
    public static void removeUsedPassword(String passwd){
        usedPasswords.remove(passwd);
    }
}
