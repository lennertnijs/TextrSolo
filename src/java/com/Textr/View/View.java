package com.Textr.View;

import java.util.Objects;

public final class View {

    private final int fileBufferId;
    private final Point1B point1B;
    private final Dimension2D dimensions;

    /**
     * Constructor for a {@link View}.
     * Uses a static {@link View.Builder} to create a valid {@link View}.
     * @param builder The {@link View.Builder}. Cannot be null.
     */
    private View(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a BufferView with a null Builder.");
        this.fileBufferId = builder.fileBufferId;
        this.point1B = builder.point1B;
        this.dimensions = builder.dimensions;
    }

    /**
     * Returns the file id of this {@link View}.
     * @return the {@link View}'s file id
     */
    public int getFileBufferId(){
        return this.fileBufferId;
    }

    /**
     * Returns the position of this {@link View}.
     * @return the {@link View}'s position as a {@link Point1B}
     */
    public Point1B getPosition(){
        return this.point1B;
    }

    /**
     * Returns the dimensions of this {@link View}
     * @return the {@link View}'s dimensions as a {@link Dimension2D}
     */
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * Compares this {@link View} to the given {@link Object} and returns True if they're equal.
     * Equality means that all of their fields are equal.
     * @param o The other {@link Object} to be compared to this
     *
     * @return True if they're equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof View view)){
            return false;
        }
        return this.fileBufferId == view.fileBufferId &&
                this.point1B.equals(view.point1B) &&
                this.dimensions.equals(view.dimensions);
    }

    /**
     * Generates and returns a hash code for this {@link View}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        int result = fileBufferId;
        result = 31 * result + point1B.hashCode();
        result = 31 * result + dimensions.hashCode();
        return result;
    }


    /**
     * Creates and returns a {@link String} representation of this {@link View}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("BufferView[fileId = %d, point = %s, dimensions = %s]",
                fileBufferId, point1B.toString(), dimensions);
    }

    /**
     * Creates and returns a new {@link View.Builder} to build a {@link View} object with.
     * @return the {@link View.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link View.Builder} used to build valid {@link View} instances with.
     * To obtain a {@link View.Builder}, use BufferView.builder();
     */
    public static class Builder{

        private int fileBufferId;
        private Point1B point1B;
        private Dimension2D dimensions;

        /**
         * Constructor of the {@link View.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the file buffer id of this {@link View.Builder} to the given id.
         * @param id the id
         *
         * @return This {@link View.Builder}
         */
        public Builder fileBufferId(int id){
            this.fileBufferId = id;
            return this;
        }

        /**
         * Sets the position of this {@link View.Builder} to the given {@link Point1B}
         * @param point1B the position as a {@link Point1B}
         *
         * @return This {@link View.Builder}
         */
        public Builder point(Point1B point1B){
            this.point1B = point1B;
            return this;
        }

        /**
         * Sets the dimensions of this {@link View.Builder} to the given {@link Dimension2D}
         * @param dimensions the dimensions as a {@link Dimension2D}
         *
         * @return This {@link View.Builder}
         */
        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }


        /**
         * Validates all the fields of this {@link View.Builder}.
         * If all the fields are valid, creates and returns a new immutable {@link View} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - fileId >= 0
         * - point != null
         * - dimensions != null
         * @throws IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link View}.
         */
        public View build(){
            if(fileBufferId < 0){
                throw new IllegalArgumentException("The file id of a BufferView cannot be negative.");
            }
            try{
                Objects.requireNonNull(point1B, "The buffer point of a bufferView cannot be null");
                Objects.requireNonNull(dimensions, "The dimensions of a bufferView cannot be null");
            }catch(NullPointerException e){
                throw new IllegalArgumentException("Cannot build a bufferView with a null parameter");
            }
            return new View(this);
        }
    }
}
