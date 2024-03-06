package com.Textr.View;

import com.Textr.Util.Validator;

/**
 * Class to represent 2D dimensions.
 * 2D dimensions means a width and a height.
 */
public final class Dimension2D {

    private final int width;
    private final int height;

    /**
     * Constructor for a {@link Dimension2D}.
     */
    private Dimension2D(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * Static factory method to create a {@link Dimension2D} with.
     * @param width The width. Must be strictly positive
     * @param height The height. Must be strictly positive
     *
     * @return The {@link Dimension2D}
     */
    public static Dimension2D create(int width, int height){
        Validator.notNegativeOrZero(width, "Cannot create a Dimension2D with a negative or zero width.");
        Validator.notNegativeOrZero(height, "Cannot create a Dimension2D with a negative or zero height.");
        return new Dimension2D(width, height);
    }

    /**
     * @return This {@link Dimension2D}'s width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * @return This {@link Dimension2D}'s height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Compares this {@link Dimension2D} to the given object and returns True if they're equal, False otherwise.
     * @param o The other object
     *
     * @return True if equal, False otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Dimension2D dimensions)){
            return false;
        }
        return this.width == dimensions.width && this.height == dimensions.height;
    }

    /**
     * Generates and returns a hash code for this {@link Dimension2D}.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        int result = width;
        result = 31 * result + width;
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link Dimension2D}.
     *
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("Dimension2D[width = %d, height = %d]", width, height);
    }
}