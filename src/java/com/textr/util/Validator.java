package com.textr.util;

import java.util.Objects;

/**
 * Class used to validate parameters.
 */
public final class Validator {

    /**
     * Private constructor. No use.
     */
    private Validator(){
    }

    /**
     * Checks whether the given object is null. If so, throws an Exception with the given message.
     * @param object The object.
     * @param message The error message.
     *
     * @throws IllegalArgumentException If the {@link Object} is null.
     */
    public static void notNull(Object object, String message){
        try{
            Objects.requireNonNull(object);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks whether the given integer is negative. If so, throws an Exception.
     * @param i The integer
     * @param message The error message
     *
     * @throws IllegalArgumentException If the integer is negative
     */
    public static void notNegative(int i, String message){
        if( i < 0){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks whether the given integer is negative or zero. If so, throws an Exception.
     * @param i The integer
     * @param message The error message
     *
     * @throws IllegalArgumentException If the integer is negative or zero.
     */
    public static void notNegativeOrZero(int i, String message){
        if(i <= 0){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks whether the given number is within the boundaries [start, end] (INCLUSIVE!). If not, throws an Exception.
     * @param num The number to check
     * @param start The lowest allowed value
     * @param end The highest allowed value
     * @param message The error message
     *
     * @throws IllegalArgumentException If the number is outside the range.
     */
    public static void withinRange(int num, int start, int end, String message){
        if(num < start || num > end){
            throw new IllegalArgumentException(message);
        }
    }
}
