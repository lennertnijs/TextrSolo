package com.Textr.View;

import java.util.Objects;

public final class Dimension2D {

    private final int width;
    private final int height;


    /**
     * Constructor for a {@link Dimension2D} object.
     * Uses a static {@link Dimension2D.Builder} to create valid {@link Dimension2D}.
     * @param builder The {@link Dimension2D.Builder}. Cannot be null.
     */
    private Dimension2D(Builder builder){
        Objects.requireNonNull(builder, "Cannot create a Dimension2D because the Builder is null");
        this.width = builder.width;
        this.height = builder.height;
    }

    /**
     * Returns the width of this {@link Dimension2D}.
     * @return this {@link Dimension2D}'s width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Returns the height of this {@link Dimension2D}.
     * @return this {@link Dimension2D}'s height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Compares this {@link Dimension2D} to the given {@link Object} and returns True if they're equal.
     * Equality means they have the same width & height.
     * @param o the other {@link Object} to be compared to this {@link Dimension2D}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Dimension2D dimensions)){
            return false;
        }
        return this.width == dimensions.width && this.height == dimensions.height;
    }

    /**
     * Generates and returns a hash code for this {@link Dimension2D}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        int result = width;
        result = 31 * result + width;
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link Dimension2D}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("Dimension2D[width = %d, height = %d]", width, height);
    }

    /**
     * Creates and returns a new {@link Dimension2D.Builder} to build a {@link Dimension2D} object with.
     * @return the {@link Dimension2D.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link Dimension2D.Builder} used to build valid {@link Dimension2D} instances with.
     * To obtain a {@link Dimension2D.Builder}, use Dimension2D.builder();
     */
    public static class Builder{

        private int width = 0;
        private int height = 0;

        /**
         * Constructor for the {@link Dimension2D.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the width of this {@link Dimension2D.Builder} to the given width.
         * @param width The width
         *
         * @return This {@link Dimension2D.Builder}
         */
        public Builder width(int width){
            this.width = width;
            return this;
        }

        /**
         * Sets the height of this {@link Dimension2D.Builder} to the given height.
         * @param height The height
         *
         * @return This {@link Dimension2D.Builder}
         */
        public Builder height(int height){
            this.height = height;
            return this;
        }

        /**
         * Validates all the fields of this {@link Dimension2D.Builder}.
         * If all the fields are valid, creates and returns a new immutable {@link Dimension2D} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - width >= 0
         * - height >= 0
         * @throws IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link Dimension2D}.
         */
        public Dimension2D build(){
            if(width <= 0 || height <= 0){
                throw new IllegalArgumentException("The width and height of a Dimension2D cannot be negative.");
            }
            return new Dimension2D(this);
        }
    }
}
