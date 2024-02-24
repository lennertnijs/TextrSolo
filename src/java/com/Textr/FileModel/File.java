package com.Textr.FileModel;

import java.util.Objects;

public final class File {

    private final int id;
    private final String path;
    private final String text;

    /**
     * Constructor for a {@link File} object.
     * Uses a static {@link File.Builder} to create valid {@link File}.
     * @param builder The {@link File.Builder}. Cannot be null.
     */
    private File(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a File with a null Builder.");
        this.id = builder.id;
        this.path = builder.path;
        this.text = builder.text;
    }

    /**
     * Returns this {@link File}'s UNIQUE id.
     * @return the {@link File}'s unique id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns this {@link File}'s file path.
     * @return the {@link File}'s path
     */
    public String getPath(){
        return this.path;
    }

    /**
     * Returns this {@link File}'s text.
     * @return the {@link File}'s text
     */
    public String getText(){
        return this.text;
    }

    /**
     * Compares this {@link File} to the given {@link File} and returns True if they're equal.
     * Equality means they have the same unique identifier.
     * @param o the other {@link File}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof File file)){
            return false;
        }
        return this.id == file.id;
    }

    /**
     * Creates a hash code for this {@link File}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

    /**
     * Creates and returns a {@link String} representation of this {@link File}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("File[id = %d, path = %s, text = %s]", this.id, this.path, this.text);
    }


    /**
     * Creates and returns a new {@link File.Builder} to build a {@link File} object with.
     * @return the {@link File.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A package-private subclass {@link Builder} used to build valid {@link File} instances with.
     * To obtain a {@link Builder}, use File.builder();
     */
    public static class Builder{

        private int id = -1;
        private String path = null;
        private String  text = null;

        /**
         * Constructor for the {@link File.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the id of this {@link File.Builder} to the given id.
         * IT IS IMPORTANT TO ENSURE THIS ID IS UNIQUE FOR EACH FILE.
         * @param id The id
         *
         * @return the {@link File.Builder}
         */
        public Builder id(int id){
            this.id = id;
            return this;
        }

        /**
         * Sets the path of this {@link File.Builder} to the given file path.
         * @param path The file path
         *
         * @return the {@link File.Builder}
         */
        public Builder path(String path){
            this.path = path;
            return this;
        }

        /**
         * Sets the text of this {@link File.Builder} to the given text.
         * @param text The file text
         *
         * @return the {@link File.Builder}
         */
        public Builder text(String text){
            this.text = text;
            return this;
        }

        /**
         * Validates all the fields of this {@link File.Builder}.
         * If all are valid, creates and returns a new immutable {@link File} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - The id cannot be negative.
         * - The file path cannot be null.
         * - The text cannot be null.
         * @throws  IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link File}
         */
        public File build(){
            if(id < 0){
                throw new IllegalArgumentException("Cannot create a File with a negative id.");
            }
            try{
                Objects.requireNonNull(path, "Cannot create a File object with a null file path.");
                Objects.requireNonNull(text, "Cannot create a File object with a null text.");
            }catch(NullPointerException n){
                throw new IllegalArgumentException("Cannot create a File with a null path/text.");
            }
            return new File(this);
        }
    }
}
