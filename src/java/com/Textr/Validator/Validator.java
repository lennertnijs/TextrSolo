package com.Textr.Validator;

import java.util.Objects;

public class Validator {

    private Validator(){

    }

    public static void notNull(Object object, String message){
        try{
            Objects.requireNonNull(object);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNegative(int i, String message){
        if( i < 0){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNegativeOrZero(int i, String message){
        if(i <= 0){
            throw new IllegalArgumentException(message);
        }
    }
}
