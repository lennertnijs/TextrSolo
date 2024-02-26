package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.Objects;

public final class FileBuffer {

    private final int id;
    private final int fileId;
    private final String  bufferText;
    private final int insertionIndex;
    private final State state;

    /**
     * Constructor for a {@link FileBuffer}.
     * A {@link FileBuffer} is used to store changes to the {@link File}'s text until the changes are saved.
     * Uses a static {@link FileBuffer.Builder} to ensure creation of valid {@link FileBuffer}'s.
     * @param builder The {@link FileBuffer.Builder}. Cannot be null.
     */
    private FileBuffer(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a FileBuffer because the Builder is null.");
        this.id = builder.id;
        this.fileId = builder.fileId;
        this.bufferText = builder.bufferText;
        this.insertionIndex = builder.insertionIndex;
        this.state = builder.state;
    }


    public int getId(){
        return this.id;
    }
    /**
     * Returns the id of the active {@link File} of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s active {@link File} id.
     */
    public int getFileId(){
        return this.fileId;
    }

    /**
     * Returns the buffered text of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s buffered text.
     */
    public String getBufferText(){
        return this.bufferText;
    }

    /**
     * Returns the insertion point index of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s insertion point index.
     */
    public int getInsertionIndex(){
        return this.insertionIndex;
    }

    /**
     * Returns the {@link State} of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s state as a {@link State}
     */
    public State getState(){
        return this.state;
    }

    /**
     * Compares this {@link FileBuffer} to the given {@link Objects} and returns True if they're equal.
     * More precisely, returns true if all of their fields are equal.
     * @param o The other {@link Object}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof FileBuffer fileBuffer)){
            return false;
        }
        return this.fileId == fileBuffer.fileId &&
                this.bufferText.equals(fileBuffer.bufferText) &&
                this.insertionIndex == fileBuffer.insertionIndex &&
                this.state == fileBuffer.state;
    }

    /**
     * Creates and returns a hash code for this {@link FileBuffer}.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        return Objects.hash(fileId, bufferText, insertionIndex, state);
    }

    /**
     * Creates and returns a {@link String} representation of this {@link FileBuffer}.
     *
     * @return The {@link String} representation
     */
    @Override
    public String toString(){
        return String.format("FileBuffer[activeFileId = %d, bufferText = %s, insertionIndex = %d, state = %s]",
                fileId, bufferText, insertionIndex, state);
    }

    /**
     * Creates and returns a new {@link FileBuffer.Builder} to build a {@link FileBuffer} with.
     * @return The {@link FileBuffer.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A subclass {@link FileBuffer.Builder} used to build valid {@link FileBuffer} instances with.
     * To obtain a {@link FileBuffer.Builder}, use FileBuffer.builder();
     */
    public static class Builder{

        private int id = -1;
        private int fileId;
        private String bufferText;
        private int insertionIndex;
        private State state;

        /**
         * Constructor for the {@link FileBuffer.Builder}
         */
        private Builder(){
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }
        /**
         * Sets the active file id of this {@link FileBuffer.Builder} to the given id.
         * @param id The id
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        /**
         * Sets the buffer text of this {@link FileBuffer.Builder} to the given text.
         * @param text The text
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder bufferText(String text){
            this.bufferText = text;
            return this;
        }

        /**
         * Sets the insertion point index of this {@link FileBuffer.Builder} to the given index.
         * @param index The index
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder insertionIndex(int index){
            this.insertionIndex = index;
            return this;
        }

        /**
         * Sets the {@link State} of this {@link FileBuffer.Builder} to the given state.
         * @param state The state
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder state(State state){
            this.state = state;
            return this;
        }

        /**
         * Validates all the fields of this {@link FileBuffer.Builder}.
         * If all are valid, creates and returns a new immutable {@link FileBuffer} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - The active file id cannot be negative.
         * - The buffer text cannot be null.
         * - The insertion point index must be within: 0 <= insertionIndex <= bufferText.length().
         * - The state cannot be null.
         * @throws  IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link FileBuffer}
         */
        public FileBuffer build(){
            if(id < 0 || fileId < 0){
                throw new IllegalArgumentException("The id the active File in the FileBuffer cannot be negative.");
            }
            try{
                Objects.requireNonNull(bufferText);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The buffer text in the FileBuffer cannot be null.");
            }
            if(insertionIndex < 0 || insertionIndex > bufferText.length()){
                throw new IllegalArgumentException("The insertion index of the FileBuffer cannot be outside the text's range.");
            }
            try{
                Objects.requireNonNull(state);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The state of the FileBuffer cannot be null.");
            }
            return new FileBuffer(this);
        }
    }
}
