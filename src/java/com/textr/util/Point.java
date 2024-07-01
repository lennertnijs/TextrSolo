package com.textr.util;

/**
 * Represents a 2-dimensional point.
 */
public final class Point {

    /**
     * The x value.
     */
    private int x;
    /**
     * The y value.
     */
    private int y;

    /**
     * Creates a MUTABLE {@link Point}.
     * @param x The x coordinate. Cannot be negative.
     * @param y The y coordinate. Cannot be negative.
     */
    public Point(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Coordinate is negative.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x coordinate.
     */
    public int getX(){
        return x;
    }

    /**
     * @return The y coordinate.
     */
    public int getY(){
        return y;
    }

    /**
     * Sets the x coordinate of this point to the given x.
     * @param x The new x coordinate. Cannot be negative.
     */
    public void setX(int x){
        if(x < 0){
            throw new IllegalArgumentException("X is negative.");
        }
        this.x = x;
    }

    /**
     * Sets the y coordinate of this point to the given y.
     * @param y The new y coordinate. Cannot be negative.
     */
    public void setY(int y){
        if(y < 0){
            throw new IllegalArgumentException("Y is negative.");
        }
        this.y = y;
    }

    /**
     * @return A copy of this point.
     */
    public Point copy(){
        return new Point(x, y);
    }


    /**
     * Compares two objects and returns true if they're equal points. Returns false otherwise.
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
        return String.format("(%d, %d)", x, y);
    }
}