package com.textr.util;

/**
 * Class to represent a 2D point (0-based).
 * This means that the point can have any non-negative values.
 */
public final class Point {

    private int x;
    private int y;

    /**
     * Private constructor for an {@link Point}. (0-based)
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * @return The y coordinate
     */
    public int getY(){
        return y;
    }

    /**
     * Sets the x coordinate of this point to the given x.
     * @param x The new x coordinate.
     *
     * @throws IllegalArgumentException If the given x is negative.
     */
    public void setX(int x){
        if(x < 0){
            throw new IllegalArgumentException("X is negative.");
        }
        this.x = x;
    }

    /**
     * Sets the y coordinate of this point to the given y.
     * @param y The new y coordinate.
     *
     * @throws IllegalArgumentException If the given y is negative.
     */
    public void setY(int y){
        if(y < 0){
            throw new IllegalArgumentException("Y is negative.");
        }
        this.y = y;
    }


    public Point copy(){
        return new Point(this.x, this.y);
    }


    /**
     * Compares this point to the given object. Returns True if equal, False otherwise.
     * @param o The object
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Point point))
            return false;
        return x == point.x && y == point.y;
    }

    /**
     * Generates and returns a hash code for this point.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result * x;
        result = 31 * result + y;
        return result;
    }

    /**
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("Point[x = %d, y = %d]", x, y);
    }
}