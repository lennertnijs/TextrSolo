package com.Textr.View;

public final class Point {

    private final int x;
    private final int y;

    /**
     * Constructor for a {@link Point}.
     */
    private Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Static factory method to create a {@link Point} with.
     * A {@link Point} represents a position in 2 dimensions (1-based).
     * @param x The x coordinate. Must be strictly positive
     * @param y The y coordinate. Must be strictly positive
     *
     * @return The {@link Point}
     */
    public static Point create(int x, int y){
        if(x <= 0 || y <= 0){
            throw new IllegalArgumentException("Cannot create a Point with a negative or 0 coordinate.");
        }
        return new Point(x,y);
    }

    /**
     * Returns this {@link Point}'s x coordinate.
     * @return the {@link Point}'s x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns this {@link Point}'s y coordinate.
     * @return the {@link Point}'s y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Compares this {@link Point} to the given {@link Object} and returns True if they're equal.
     * Equality means that they have the same (x, y) coordinates.
     * @param o The other {@link Object} to be compared to this
     *
     * @return True if they're equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Point point)){
            return false;
        }
        return this.x == point.x && this.y == point.y;
    }

    /**
     * Generates and returns a hash code for this {@link Point}.
     *
     * @return the hash code
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
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("Point[x = %d, y = %d]", x, y);
    }
}
