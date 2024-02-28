package com.Textr.View;

public final class Position {

    private final int x;
    private final int y;

    /**
     * Constructor for a {@link Position}.
     */
    private Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Position create(int x, int y){
        if(x <= 0 || y <= 0){
            throw new IllegalArgumentException("Cannot");
        }
        return new Position(x,y);
    }

    /**
     * Returns this {@link Position}'s x coordinate.
     * @return the {@link Position}'s x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns this {@link Position}'s y coordinate.
     * @return the {@link Position}'s y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Compares this {@link Position} to the given {@link Object} and returns True if they're equal.
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
        if(!(o instanceof Position point)){
            return false;
        }
        return this.x == point.x && this.y == point.y;
    }

    /**
     * Generates and returns a hash code for this {@link Position}.
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
     * Creates and returns a {@link String} representation of this {@link Position}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("Position[x = %d, y = %d]", x, y);
    }
}
