package com.Textr.Validator;

import java.util.Objects;

public class Validator {

    private Validator(){

    }

    public static void validateNotNull(Object object, String message){
        try{
            Objects.requireNonNull(object);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(message);
        }
    }
}
