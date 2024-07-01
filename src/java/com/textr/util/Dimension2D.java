package com.textr.util;

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
    public Dimension2D(int width, int height){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width or height is negative or 0.");
        }
        this.width = width;
        this.height = height;
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
     *
     * @return True if equal, False otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Dimension2D dimensions))
            return false;
        return this.width == dimensions.width && this.height == dimensions.height;
    }

    /**
     * @return The hash code
     */
    @Override
    public int hashCode(){
        int result = width;
        result = 31 * result + width;
        return result;
    }

    /**
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("Dimension2D[width = %d, height = %d]", width, height);
    }
}