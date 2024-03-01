package com.Textr.View;

import com.Textr.Point.Point;
import com.Textr.Validator.Validator;

import java.util.Objects;

/**
 * Class to represent a view.
 * A view is a part of the Terminal, used to display something.
 */
public final class View {

    private final int id;
    private final int fileBufferId;
    private final Point position;
    private final Dimension2D dimensions;
    private final Point anchor;

    /**
     * Constructor for a {@link View}.
     * Uses a static {@link View.Builder} to create a {@link View}.
     * @param builder The {@link View.Builder}. Cannot be null.
     */
    private View(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a BufferView with a null Builder.");
        this.id = ViewIdGenerator.getId();
        this.fileBufferId = builder.fileBufferId;
        this.position = builder.position;
        this.dimensions = builder.dimensions;
        this.anchor = builder.anchor;
    }

    /**
     * @return This {@link View}'s id.
     */
    public int getId(){
        return this.id;
    }
    /**
     * @return This {@link View}'s {@link FileBuffer}'s id.
     */
    public int getFileBufferId(){
        return this.fileBufferId;
    }

    /**
     * @return This {@link View}'s position.
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * @return This {@link View}'s dimensions.
     */
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * @return This {@link View}'s anchor point.
     */
    public Point getAnchor(){
        return anchor;
    }


    /**
     * Compares this {@link View} to the given {@link Object} and returns True if they're equal. Returns False otherwise.
     * Equality means that all of their fields are equal.
     * @param o The other {@link Object}
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
                this.position.equals(view.position) &&
                this.dimensions.equals(view.dimensions) &&
                this.anchor.equals(view.anchor);
    }

    /**
     * Generates and returns a hash code for this {@link View}.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        int result = fileBufferId;
        result = 31 * result + position.hashCode();
        result = 31 * result + dimensions.hashCode();
        result = 31 * result + anchor.hashCode();
        return result;
    }


    /**
     * Creates and returns a {@link String} representation of this {@link View}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("View[fileBufferId = %d, position = %s, dimensions = %s, anchor = %s]",
                fileBufferId, position, dimensions, anchor);
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
        private Point position;
        private Dimension2D dimensions;
        private Point anchor;

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
         * Sets the position of this {@link View.Builder} to the given {@link Point}
         * @param position the position as a {@link Point}
         *
         * @return This {@link View.Builder}
         */
        public Builder position(Point position){
            this.position = position;
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

        public Builder anchor(Point anchor){
            this.anchor = anchor;
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
            Validator.notNegative(fileBufferId, "The id of the FileBuffer of the View cannot be negative.");
            Validator.notNull(position, "The global position of the View in the Terminal cannot be null.");
            Validator.notNull(dimensions, "The dimensions of the View cannot be null.");
            Validator.notNull(anchor, "The anchor point of the View cannot be null");
            return new View(this);
        }
    }
}
