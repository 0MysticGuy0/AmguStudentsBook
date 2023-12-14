package com.mygy.studentbook.Data.Utilites;

import java.util.concurrent.ThreadLocalRandom;

public class UserPasswordGenerator implements PasswordGenerator{
    private static final String ALPHABET;

    static {
        ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    }
    @Override
    public String generate(int length) {
        StringBuilder passwd = new StringBuilder();

        for(int i = 0; i < length; i++){
            int charIndx = ThreadLocalRandom.current().nextInt(0,ALPHABET.length());
            passwd.append(ALPHABET.charAt(charIndx));
        }

        return passwd.toString();
    }
}
