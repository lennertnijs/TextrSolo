package com.Textr.TerminalModel;

import java.util.Objects;

public class TerminalView {

    private final int fileId;
    private final Position position;
    private final Dimension2D dimensions;

    /**
     * Constructor for a {@link TerminalView}.
     * Uses a static {@link TerminalView.Builder} to create a valid {@link TerminalView}.
     * @param builder The {@link TerminalView.Builder}. Cannot be null.
     */
    private TerminalView(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a BufferView with a null Builder.");
        this.fileId = builder.fileId;
        this.position = builder.position;
        this.dimensions = builder.dimensions;
    }

    /**
     * Returns the file id of this {@link TerminalView}.
     * @return the {@link TerminalView}'s file id
     */
    public int getFileId(){
        return this.fileId;
    }

    /**
     * Returns the position of this {@link TerminalView}.
     * @return the {@link TerminalView}'s position as a {@link Position}
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Returns the dimensions of this {@link TerminalView}
     * @return the {@link TerminalView}'s dimensions as a {@link Dimension2D}
     */
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * Compares this {@link TerminalView} to the given {@link Object} and returns True if they're equal.
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
        if(!(o instanceof TerminalView view)){
            return false;
        }
        return this.fileId == view.fileId &&
                this.position.equals(view.position) &&
                this.dimensions.equals(view.dimensions);
    }

    /**
     * Generates and returns a hash code for this {@link TerminalView}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return Objects.hash(fileId, position, dimensions);
    }


    /**
     * Creates and returns a {@link String} representation of this {@link TerminalView}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("BufferView[fileId = %d, point = %s, dimensions = %s]",
                fileId, position.toString(), dimensions);
    }

    /**
     * Creates and returns a new {@link TerminalView.Builder} to build a {@link TerminalView} object with.
     * @return the {@link TerminalView.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link TerminalView.Builder} used to build valid {@link TerminalView} instances with.
     * To obtain a {@link TerminalView.Builder}, use BufferView.builder();
     */
    public static class Builder{

        private int fileId;
        private Position position;
        private Dimension2D dimensions;

        /**
         * Constructor of the {@link TerminalView.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the file id of this {@link TerminalView.Builder} to the given id.
         * @param id the id
         *
         * @return This {@link TerminalView.Builder}
         */
        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        /**
         * Sets the position of this {@link TerminalView.Builder} to the given {@link Position}
         * @param position the position as a {@link Position}
         *
         * @return This {@link TerminalView.Builder}
         */
        public Builder point(Position position){
            this.position = position;
            return this;
        }

        /**
         * Sets the dimensions of this {@link TerminalView.Builder} to the given {@link Dimension2D}
         * @param dimensions the dimensions as a {@link Dimension2D}
         *
         * @return This {@link TerminalView.Builder}
         */
        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }


        /**
         * Validates all the fields of this {@link TerminalView.Builder}.
         * If all the fields are valid, creates and returns a new immutable {@link TerminalView} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - fileId >= 0
         * - point != null
         * - dimensions != null
         * @throws IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link TerminalView}.
         */
        public TerminalView build(){
            if(fileId < 0){
                throw new IllegalArgumentException("The file id of a BufferView cannot be negative.");
            }
            try{
                Objects.requireNonNull(position, "The buffer point of a bufferView cannot be null");
                Objects.requireNonNull(dimensions, "The dimensions of a bufferView cannot be null");
            }catch(NullPointerException e){
                throw new IllegalArgumentException("Cannot build a bufferView with a null parameter");
            }
            return new TerminalView(this);
        }
    }
}
