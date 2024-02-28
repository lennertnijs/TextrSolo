package com.Textr.View;


public final class Dimension2D {

    private final int width;
    private final int height;


    /**
     * Constructor for a {@link Dimension2D} object.
     */
    private Dimension2D(int width, int height){
        this.width = width;
        this.height = height;
    }

    public static Dimension2D create(int width, int height){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Cannot create a Dimension2D with a negative or 0 width/height.");
        }
        return new Dimension2D(width, height);
    }
    /**
     * Returns the width of this {@link Dimension2D}.
     * @return this {@link Dimension2D}'s width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Returns the height of this {@link Dimension2D}.
     * @return this {@link Dimension2D}'s height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Compares this {@link Dimension2D} to the given {@link Object} and returns True if they're equal.
     * Equality means they have the same width & height.
     * @param o the other {@link Object} to be compared to this {@link Dimension2D}
     *
     * @return True if equal, false otherwise.
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
     * @return the hash code
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
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("Dimension2D[width = %d, height = %d]", width, height);
    }
}
