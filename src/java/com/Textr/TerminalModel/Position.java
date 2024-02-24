package com.Textr.TerminalModel;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    /**
     * Constructor for a {@link Position}.
     * Uses a static {@link Position.Builder} to create a valid {@link Position}.
     * @param builder The {@link Position.Builder}. Cannot be null.
     */
    private Position(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a BufferPoint with a null Builder.");
        this.x = builder.x;
        this.y = builder.y;
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
        return Objects.hash(x, y);
    }

    /**
     * Creates and returns a {@link String} representation of this {@link Position}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("BufferPoint[x = %d, y = %d]", this.x, this.y);
    }

    /**
     * Creates and returns a new {@link Position.Builder} to build a {@link Position} object with.
     * @return the {@link Position.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link Position.Builder} used to build valid {@link Position} instances with.
     * To obtain a {@link Position.Builder}, use BufferPoint.builder();
     */
    public static class Builder{

        private int x;
        private int y;

        /**
         * Constructor for the {@link Position.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the x coordinate of this {@link Position.Builder} to the given x.
         * @param x The x coordinate
         *
         * @return This {@link Position.Builder}
         */
        public Builder x(int x){
            this.x = x;
            return this;
        }

        /**
         * Sets the y coordinate of this {@link Position.Builder} to the given y.
         * @param y The y coordinate
         *
         * @return This {@link Position.Builder}
         */
        public Builder y(int y){
            this.y = y;
            return this;
        }

        /**
         * Validates all the fields of this {@link Position.Builder}.
         * If all the fields are valid, creates and returns a new immutable {@link Position} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - x >= 1
         * - y >= 1
         * @throws IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link Position}.
         */
        public Position build(){
            if(x <= 0 || y <= 0){
                throw new IllegalArgumentException("The coordinates of the point cannot be negative or 0.");
            }
            return new Position(this);
        }
    }
}
