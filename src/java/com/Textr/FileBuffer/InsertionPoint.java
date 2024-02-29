package com.Textr.FileBuffer;

public class InsertionPoint {

    private int x;
    private int y;

    /**
     * Private constructor for an {@link InsertionPoint}. (0-based)
     * @param x The x coordinate
     * @param y The y coordinate
     */
    private InsertionPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Static factory method to create a valid {@link InsertionPoint} with.
     * @param x The x coordinate. Cannot be negative
     * @param y The y coordinate. Cannot be negative
     *
     * @return The {@link InsertionPoint}
     */
    public static InsertionPoint create(int x, int y){
        if(x < 0 || y < 0){
            throw  new IllegalArgumentException("Cannot create an InsertionPoint with a negative coordinate.");
        }
        return new InsertionPoint(x, y);
    }

    /**
     * Returns the x coordinate of this {@link InsertionPoint}.
     * @return The x coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y coordinate of this {@link InsertionPoint}.
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
     * Increments the x coordinate of this {@link InsertionPoint} by 1.
     */
    protected void incrementX(){
        x += 1;
    }

    /**
     * Decrements the x coordinate of this {@link InsertionPoint} by 1, if it is not already at 0.
     */
    protected void decrementX(){
        x = Math.max(x - 1, 0);
    }

    /**
     * Increments the y coordinate of this {@link InsertionPoint} by 1.
     */
    protected void incrementY(){
        y += 1;
    }

    /**
     * Decrements the y coordinate of this {@link InsertionPoint} by 1, if it is not already at 0.
     */
    protected void decrementY(){
        y = Math.max(y - 1, 0);
    }

    /**
     * Compares this {@link InsertionPoint} to the given {@link Object}. Returns True if equal, False otherwise.
     * @param o The {@link Object}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof InsertionPoint point)){
            return false;
        }
        return x == point.x && y == point.y;
    }

    /**
     * Generates and returns a hash code for this {@link InsertionPoint}.
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
     * Creates and returns a {@link String} representation of this {@link InsertionPoint}.
     *
     * @return The {@link String} representation
     */
    @Override
    public String toString(){
        return String.format("InsertionPoint[x = %d, y = %d]", x, y);
    }
}