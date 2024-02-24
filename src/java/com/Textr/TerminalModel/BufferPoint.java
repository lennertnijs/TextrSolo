package com.Textr.TerminalModel;

import java.util.Objects;

public class BufferPoint {

    private final int x;
    private final int y;

    /**
     * Constructor for a {@link BufferPoint}.
     * Uses a static {@link BufferPoint.Builder} to create a valid {@link BufferPoint}.
     * @param builder The {@link BufferPoint.Builder}. Cannot be null.
     */
    private BufferPoint(Builder builder){
        this.x = builder.x;
        this.y = builder.y;
    }

    /**
     * Returns this {@link BufferPoint}'s x coordinate.
     * @return the {@link BufferPoint}'s x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns this {@link BufferPoint}'s y coordinate.
     * @return the {@link BufferPoint}'s y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Compares this {@link BufferPoint} to the given {@link Object} and returns True if they're equal.
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
        if(!(o instanceof BufferPoint point)){
            return false;
        }
        return this.x == point.x && this.y == point.y;
    }

    /**
     * Generates and returns a hash code for this {@link BufferPoint}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    /**
     * Creates and returns a {@link String} representation of this {@link BufferPoint}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("BufferPoint[x = %d, y = %d]", this.x, this.y);
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private int x;
        private int y;

        private Builder(){

        }

        public Builder x(int x){
            this.x = x;
            return this;
        }

        public Builder y(int y){
            this.y = y;
            return this;
        }

        public BufferPoint build(){
            if(x <= 0 || y <= 0){
                throw new IllegalArgumentException("The coordinates of the point cannot be negative or 0.");
            }
            return new BufferPoint(this);
        }
    }
}
