package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;

import java.util.Objects;

/**
 * Class to represent a view.
 */
public final class View {

    private final FileBuffer buffer;
    private Point position;
    private Dimension2D dimensions;
    private final Point anchor;

    /**
     * Constructor for a view.
     * A view is a part of the Terminal, used to display (part of) a buffer.
     * @param builder The builder. Cannot be null.
     */
    private View(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a View with a null Builder.");
        this.buffer = builder.buffer;
        this.position = builder.position;
        this.dimensions = builder.dimensions;
        this.anchor = builder.anchor;
    }

    private View(FileBuffer buffer, Point position, Dimension2D dimensions, Point anchor){
        this.buffer = buffer;
        this.position = position;
        this.dimensions = dimensions;
        this.anchor = anchor;

    }

    public FileBuffer getBuffer(){
        return this.buffer;
    }

    /**
     * @return This view's position. (0-based)
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * @return This view's dimensions.
     */
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor;
    }

    /**
     * Set this views Position
     * @param position = the new position of the view
     */
    public void setPosition(Point position){
        Validator.notNull(position, "Error: Cannot set the new Position of a View to null.");
        this.position = position;
    }

    /**
     * Set this views Dimensions
     * @param dimensions = the new dimensions of the view
     */
    public void setDimensions(Dimension2D dimensions){
        Validator.notNull(position, "Error: Cannot set the new Dimension of a View to null.");
        this.dimensions = dimensions;
    }


    /**
     * Compares this view to the given object and returns True if they're equal. Returns False otherwise.
     * @param o The other object
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
        return this.buffer.equals(view.buffer) &&
                this.dimensions.equals(view.dimensions) &&
                this.position.equals(view.position) &&
                this.anchor.equals(view.anchor);
    }

    /**
     * Generates and returns a hash code for this view.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        int result = buffer.hashCode();
        result = result * 31 + dimensions.hashCode();
        result = result * 31 + position.hashCode();
        result = result * 31 + anchor.hashCode();
        return result;
    }


    /**
     * Creates and returns a {@link String} representation of this view.
     *
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("View[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                buffer, position, dimensions, anchor);
    }

    public View copy(){
        return new View(this.buffer.copy(), this.position.copy(), this.dimensions.copy(), this.anchor.copy());
    }

    public boolean leftOff(View next){
        return this.getPosition().getX() < next.getPosition().getX();
    }

    /**
     * Creates and returns a new {@link View.Builder} to build a view with.
     * @return The builder
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * Used to build valid {@link View} instances with.
     * To obtain a builder, use View.builder();
     */
    public static class Builder{

        private FileBuffer buffer;
        private Point position;
        private Dimension2D dimensions;
        private Point anchor;

        private Builder(){
        }

        /**
         * Sets the file buffer id of this builder to the given id.
         * @param buffer the buffer
         *
         * @return This builder
         */
        public Builder buffer(FileBuffer buffer){
            this.buffer = buffer.copy();
            return this;
        }

        /**
         * Sets the position of this builder to the given position.
         * @param position The position
         *
         * @return This builder
         */
        public Builder position(Point position){
            this.position = position.copy();
            return this;
        }

        /**
         * Sets the dimensions of this builder to the given dimensions.
         * @param dimensions The dimensions.
         *
         * @return This builder
         */
        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions.copy();
            return this;
        }

        /**
         * Sets the anchor of this builder to the given anchor.
         * @param anchor The anchor.
         *
         * @return This builder
         */
        public Builder anchor(Point anchor){
            this.anchor = anchor.copy();
            return this;
        }

        /**
         * Validates all the fields of this builder and returns a valid view.
         * More precisely, the following conditions must hold on the fields:
         * - fileId >= 0
         * - point != null
         * - dimensions != null
         * - anchor != null
         *
         * @return A new, valid view.
         * @throws IllegalArgumentException If any of the fields are invalid.
         */
        public View build(){
            Validator.notNull(buffer, "The id of the FileBuffer of the View cannot be negative.");
            Validator.notNull(position, "The global position of the View in the Terminal cannot be null.");
            Validator.notNull(dimensions, "The dimensions of the View cannot be null.");
            Validator.notNull(anchor, "The anchor of the View cannot be null");
            return new View(this);
        }

    }
}
