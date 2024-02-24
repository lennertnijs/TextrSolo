package com.Textr.TerminalModel;

import java.util.Objects;

public class BufferView {

    private final int fileId;
    private final BufferPoint point;
    private final Dimension2D dimensions;
    private final String text;
    private final int insertionIndex;
    private final State state;

    /**
     * Constructor for a {@link BufferView}.
     * Uses a static {@link BufferView.Builder} to create a valid {@link BufferView}.
     * @param builder The {@link BufferView.Builder}. Cannot be null.
     */
    private BufferView(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a BufferView with a null Builder.");
        this.fileId = builder.fileId;
        this.point = builder.point;
        this.dimensions = builder.dimensions;
        this.text = builder.text;
        this.insertionIndex = builder.insertionIndex;
        this.state = builder.state;
    }

    /**
     * Returns the file id of this {@link BufferView}.
     * @return the {@link BufferView}'s file id
     */
    public int getFileId(){
        return this.fileId;
    }

    /**
     * Returns the position of this {@link BufferView}.
     * @return the {@link BufferView}'s position as a {@link BufferPoint}
     */
    public BufferPoint getPoint(){
        return this.point;
    }

    /**
     * Returns the dimensions of this {@link BufferView}
     * @return the {@link BufferView}'s dimensions as a {@link Dimension2D}
     */
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * Returns the text of this {@link BufferView}
     * @return the {@link BufferView}'s text as a {@link String}
     */
    public String getText(){
        return this.text;
    }

    /**
     * Returns the insertion point index of this {@link BufferView}
     * @return the {@link BufferView}'s insertion point index
     */
    public int getInsertionIndex(){
        return this.insertionIndex;
    }

    /**
     * Returns the state of this {@link BufferView}
     * @return the {@link BufferView}'s state as a {@link State}
     */
    public State getState(){
        return this.state;
    }

    /**
     * Compares this {@link BufferView} to the given {@link Object} and returns True if they're equal.
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
        if(!(o instanceof BufferView view)){
            return false;
        }
        return this.fileId == view.fileId &&
                this.point.equals(view.point) &&
                this.dimensions.equals(view.dimensions) &&
                this.text.equals(view.text) &&
                this.insertionIndex == view.insertionIndex &&
                this.state == view.state;
    }

    /**
     * Generates and returns a hash code for this {@link BufferView}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return Objects.hash(fileId, point, dimensions, text, insertionIndex, state);
    }


    /**
     * Creates and returns a {@link String} representation of this {@link BufferView}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("BufferView[fileId = %d, point = %s, dimensions = %s, " +
                                "text = %s, insertionIndex = %d, state = %s]",
                                fileId, point.toString(), dimensions, text, insertionIndex, state);
    }

    /**
     * Creates and returns a new {@link BufferView.Builder} to build a {@link BufferView} object with.
     * @return the {@link BufferView.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link BufferView.Builder} used to build valid {@link BufferView} instances with.
     * To obtain a {@link BufferView.Builder}, use BufferView.builder();
     */
    public static class Builder{

        private int fileId;
        private BufferPoint point;
        private Dimension2D dimensions;
        private String text;
        private int insertionIndex = 0;
        private final State state = State.CLEAN;

        /**
         * Constructor of the {@link BufferView.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the file id of this {@link BufferView.Builder} to the given id.
         * @param id the id
         *
         * @return This {@link BufferView.Builder}
         */
        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        /**
         * Sets the position of this {@link BufferView.Builder} to the given {@link BufferPoint}
         * @param point the position as a {@link BufferPoint}
         *
         * @return This {@link BufferView.Builder}
         */
        public Builder point(BufferPoint point){
            this.point = point;
            return this;
        }

        /**
         * Sets the dimensions of this {@link BufferView.Builder} to the given {@link Dimension2D}
         * @param dimensions the dimensions as a {@link Dimension2D}
         *
         * @return This {@link BufferView.Builder}
         */
        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }

        /**
         * Sets the text of this {@link BufferView.Builder} to the given text.
         * @param text the text as a {@link String}
         *
         * @return This {@link BufferView.Builder}
         */
        public Builder text(String text){
            this.text = text;
            return this;
        }

        /**
         * Sets the insertion point index of this {@link BufferView.Builder} to the given index.
         * @param insertionIndex The insertion point index
         *
         * @return This {@link BufferView.Builder}
         */
        public Builder insertionIndex(int insertionIndex){
            this.insertionIndex = insertionIndex;
            return this;
        }


        /**
         * Validates all the fields of this {@link BufferView.Builder}.
         * If all the fields are valid, creates and returns a new immutable {@link BufferView} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - fileId >= 0
         * - point != null
         * - dimensions != null
         * - text != null
         * - 0 <= insertionIndex <= text.length()
         * @throws IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link BufferView}.
         */
        public BufferView build(){
            if(fileId < 0){
                throw new IllegalArgumentException("The file id of a BufferView cannot be negative.");
            }
            try{
                Objects.requireNonNull(point, "The buffer point of a bufferView cannot be null");
                Objects.requireNonNull(dimensions, "The dimensions of a bufferView cannot be null");
                Objects.requireNonNull(text, "The text of a bufferView cannot be null");
            }catch(NullPointerException e){
                throw new IllegalArgumentException("Cannot build a bufferView with a null parameter");
            }
            if(insertionIndex < 0 || insertionIndex > text.length()){
                throw new IllegalArgumentException("The insertion index of a bufferView must be within the text size range.");
            }
            return new BufferView(this);
        }
    }
}
