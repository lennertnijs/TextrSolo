package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.Objects;

public final class FileBuffer {

    private final int id;
    private final int fileId;
    private final String[] bufferText;
    private final InsertionPoint insertionPoint;
    private BufferState bufferState;

    /**
     * Constructor for a {@link FileBuffer}.
     * A {@link FileBuffer} is used to store changes to the {@link File}'s text until the changes are saved.
     * Uses a static {@link FileBuffer.Builder} to ensure creation of valid {@link FileBuffer}'s.
     * @param builder The {@link FileBuffer.Builder}. Cannot be null.
     */
    private FileBuffer(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a FileBuffer because the Builder is null.");
        this.id = FileBufferIdGenerator.getId();
        this.fileId = builder.fileId;
        this.bufferText = builder.bufferText;
        this.insertionPoint = builder.insertionPoint;
        this.bufferState = builder.bufferState;
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
    public String[] getBufferTextLines(){
        return this.bufferText;
    }

    public String getBufferText(){
        return String.join("", bufferText);
    }

    public int getAmountOfBufferTextLines(){
        return bufferText.length;
    }

    public int getAmountOfCharacters(){
        return String.join("", bufferText).length();
    }

    public void addCharacterToBufferText(char character){
        int row = insertionPoint.getY();
        int col = insertionPoint.getX();
        String line = bufferText[row];
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < line.length() + 1; i++){
            if(i < col){
                builder.append(line.charAt(i));
            }else if(i == col){
                builder.append(character);
            }else{
                builder.append(line.charAt(i-1));
            }
        }
        bufferText[row] = builder.toString();
    }


    /**
     * Returns the insertion point index of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s insertion point index.
     */
    public InsertionPoint getInsertionPosition(){
        return this.insertionPoint;
    }

    /**
     * Returns the {@link BufferState} of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s state as a {@link BufferState}
     */
    public BufferState getState(){
        return this.bufferState;
    }

    public void setDirty(){
        bufferState = BufferState.DIRTY;
    }

    public void setClean(){
        bufferState = BufferState.CLEAN;
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
        return this.id == fileBuffer.id;
    }

    /**
     * Creates and returns a hash code for this {@link FileBuffer}.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        return this.id;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link FileBuffer}.
     *
     * @return The {@link String} representation
     */
    @Override
    public String toString(){
        return String.format("FileBuffer[id = %d, activeFileId = %d, bufferText = %s, insertionPosition = %s, state = %s]",
                id, fileId, String.join("", bufferText), insertionPoint, bufferState);
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

        private int fileId = -1;
        private String[] bufferText = null;
        private InsertionPoint insertionPoint = null;
        private BufferState bufferState = null;

        /**
         * Constructor for the {@link FileBuffer.Builder}
         */
        private Builder(){
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
        public Builder bufferText(String[] text){
            this.bufferText = text;
            return this;
        }

        /**
         * Sets the insertion point index of this {@link FileBuffer.Builder} to the given index.
         * @param point The position
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder insertionPosition(InsertionPoint point){
            this.insertionPoint = point;
            return this;
        }

        /**
         * Sets the {@link BufferState} of this {@link FileBuffer.Builder} to the given state.
         * @param bufferState The state
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder state(BufferState bufferState){
            this.bufferState = bufferState;
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
            if(fileId < 0){
                throw new IllegalArgumentException("The id the active File in the FileBuffer cannot be negative.");
            }
            try{
                Objects.requireNonNull(bufferText);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The buffer text in the FileBuffer cannot be null.");
            }
            try{
                Objects.requireNonNull(insertionPoint);
                Objects.requireNonNull(bufferState);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The state of the FileBuffer cannot be null.");
            }
            return new FileBuffer(this);
        }
    }
}
