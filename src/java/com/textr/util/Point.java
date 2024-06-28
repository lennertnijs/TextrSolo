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
    private Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Static factory method to create a valid {@link Point} with.
     * @param x The x coordinate. Cannot be negative
     * @param y The y coordinate. Cannot be negative
     *
     * @return The new point.
     * @throws IllegalArgumentException If the passed x or y value is negative.
     */
    public static Point create(int x, int y){
        Validator.notNegative(x, "Cannot create a Point with a negative x value.");
        Validator.notNegative(y, "Cannot create a Point with a negative y value.");
        return new Point(x, y);
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
        Validator.notNegative(x, "Cannot set this point's x coordinate to a negative value.");
        this.x = x;
    }

    /**
     * Sets the y coordinate of this point to the given y.
     * @param y The new y coordinate.
     *
     * @throws IllegalArgumentException If the given y is negative.
     */
    public void setY(int y){
        Validator.notNegative(y, "Cannot set this point's y coordinate to a negative value.");
        this.y = y;
    }

    /**
     * Increments the x coordinate of this point by 1.
     */
    public void incrementX(){
        x += 1;
    }

    /**
     * Decrements the x coordinate of this point by 1, if it is not already at 0.
     */
    public void decrementX(){
        x = Math.max(x - 1, 0);
    }

    /**
     * Increments the y coordinate of this point by 1.
     */
    public void incrementY(){
        y += 1;
    }

    /**
     * Decrements the y coordinate of this point by 1, if it is not already at 0.
     */
    public void decrementY(){
        y = Math.max(y - 1, 0);
    }


    /**
     * Compares this point to the given object. Returns True if equal, False otherwise.
     * @param o The object
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Point point)){
            return false;
        }
        return x == point.x && y == point.y;
    }

    /**
     * Generates and returns a hash code for this point.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this point.
     *
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("Point[x = %d, y = %d]", x, y);
    }

    public Point copy(){
        return new Point(this.x, this.y);
    }


}