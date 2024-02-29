package com.Textr.FileBuffer;

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
     * @return The {@link Point}
     */
    public static Point create(int x, int y){
        if(x < 0 || y < 0){
            throw  new IllegalArgumentException("Cannot create an InsertionPoint with a negative coordinate.");
        }
        return new Point(x, y);
    }

    /**
     * Returns the x coordinate of this {@link Point}.
     * @return The x coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y coordinate of this {@link Point}.
     * @return The y coordinate
     */
    public int getY(){
        return y;
    }


    public void setX(int x){
        if(x < 0){
            throw new IllegalArgumentException();
        }
        this.x = x;
    }

    /**
     * Increments the x coordinate of this {@link Point} by 1.
     */
    public void incrementX(){
        x += 1;
    }

    /**
     * Decrements the x coordinate of this {@link Point} by 1, if it is not already at 0.
     */
    public void decrementX(){
        x = Math.max(x - 1, 0);
    }

    /**
     * Increments the y coordinate of this {@link Point} by 1.
     */
    public void incrementY(){
        y += 1;
    }

    /**
     * Decrements the y coordinate of this {@link Point} by 1, if it is not already at 0.
     */
    public void decrementY(){
        y = Math.max(y - 1, 0);
    }

    /**
     * Compares this {@link Point} to the given {@link Object}. Returns True if equal, False otherwise.
     * @param o The {@link Object}
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
     * Generates and returns a hash code for this {@link Point}.
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
     * Creates and returns a {@link String} representation of this {@link Point}.
     *
     * @return The {@link String} representation
     */
    @Override
    public String toString(){
        return String.format("InsertionPoint[x = %d, y = %d]", x, y);
    }
}