package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;
import com.Textr.Util.Direction;

/**
 * Represents a buffer.
 */
public final class FileBuffer {

    private final int fileId;
    private final Text text;
    private final Point cursor;
    private BufferState state;

    /**
     * Constructor for a file buffer.
     * A file buffer is used to temporarily store changes to the file's text until the changes are saved.
     * Use {@link FileBufferCreator} to create buffers.
     * @param builder The file buffer builder. Cannot be null.
     */
    private FileBuffer(Builder builder){
        Validator.notNull(builder, "Cannot build a FileBuffer because the Builder is null.");
        this.fileId = builder.fileId;
        this.text = builder.text;
        this.cursor = builder.cursor;
        this.state = builder.state;
    }

    private FileBuffer(int fileId, Text text, Point cursor, BufferState state){
        this.fileId = fileId;
        this.text = text;
        this.cursor = cursor;
        this.state = state;
    }

    /**
     * @return This file buffer's {@link File} id.
     */
    public int getFileId(){
        return this.fileId;
    }

    /**
     * @return This file buffer's text.
     */
    public Text getText(){
        return this.text;
    }

    /**
     * @return This file buffer's cursor.
     */
    public Point getCursor(){
        return this.cursor;
    }


    /**
     * @return This file buffer's state.
     */
    public BufferState getState(){
        return this.state;
    }

    /**
     * Moves the cursor 1 unit in the given {@link Direction}, within text boundaries.
     * @param direction The direction. Cannot be null.
     *
     * @throws IllegalArgumentException If the given direction is null.
     */
    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the cursor in the null Direction.");
        CursorMover.move(cursor, direction, text);
    }

    /**
     * Sets the state of this file buffer to the given state.
     * @param state The new state. Cannot be null.
     *
     * @throws IllegalArgumentException If the state is null.
     */
    public void setState(BufferState state) {
        Validator.notNull(state, "Cannot set the file buffer's state to null.");
        this.state = state;
    }

    /**
     * Inserts the given character into this {@link FileBuffer}'s buffer {@link Text} at the insertion {@link Point}.
     * @param character The character
     */
    public void insertCharacter(char character){
        text.insertCharacter(character, cursor.getY(), cursor.getX());
        CursorMover.move(cursor, Direction.RIGHT, text);
    }

    /**
     * Removes the character before the cursor from the text.
     * Also moves the cursor one unit to the left.
     * Used when backspace is pressed.
     */
    public void removeCharacterBefore(){
        text.removeCharacter(cursor.getY(), cursor.getX() - 1);
        CursorMover.move(cursor, Direction.LEFT, text);
    }

    /**
     * Removes the character after the cursor from the text.
     * Does not move the cursor.
     * Used when delete is pressed.
     */
    public void removeCharacterAfter(){
        text.removeCharacter(cursor.getY(), cursor.getX());
    }

    /**
     * Breaks the line of the text into 2 new lines (seperated by a space), at the cursor.
     * Also moves the cursor into the new line.
     * Used when enter is pressed.
     */
    public void createNewLine(){
        text.splitLineAtColumn(cursor.getY(), cursor.getX());
        CursorMover.move(cursor, Direction.RIGHT, text);
    }


    /**
     * Compares this file buffer to the given object and returns True if they're equal.
     * @param o The other object
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
                this.text.equals(fileBuffer.text) &&
                this.cursor.equals(fileBuffer.cursor) &&
                this.state == fileBuffer.state;
    }

    /**
     * Creates and returns a hash code for this file buffer.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        int result = fileId;
        result = 31 * result + text.hashCode();
        result = 31 * result + cursor.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this file buffer.
     *
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("FileBuffer[fileId = %d, text = %s, cursor = %s, state = %s]",
                fileId, text, cursor, state);
    }

    public FileBuffer copy(){
        return new FileBuffer(this.fileId, this.text.copy(), this.cursor.copy(), this.state);
    }

    /**
     * Creates and returns a new {@link FileBuffer.Builder} to build a {@link FileBuffer} with.
     * @return The builder.
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * Used to build valid {@link FileBuffer} instances with.
     * To obtain a {@link FileBuffer.Builder}, use FileBuffer.builder();
     */
    public static class Builder{

        private int fileId = -1;
        private Text text = null;
        private Point cursor = null;
        private BufferState state = null;

        private Builder(){
        }

        /**
         * Sets the active file id of this builder to the given id.
         * @param id The id
         *
         * @return The builder
         */
        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        /**
         * Sets the text of this buffer to the given text.
         * @param text The text
         *
         * @return The builder
         */
        public Builder text(Text text){
            this.text = text.copy();
            return this;
        }

        /**
         * Sets the cursor of this buffer to the given cursor.
         * @param cursor The cursor
         *
         * @return The builder
         */
        public Builder cursor(Point cursor){
            this.cursor = cursor.copy();
            return this;
        }

        /**
         * Sets the state of this buffer to the given state.
         * @param state The state
         *
         * @return This builder
         */
        public Builder state(BufferState state){
            this.state = state;
            return this;
        }


        /**
         * Validates all the fields of this builder.
         * If all are valid, creates and returns a new {@link FileBuffer} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - The active file id cannot be negative.
         * - The text cannot be null.
         * - The cursor cannot be null.
         * - The state cannot be null.
         *
         * @return The new file buffer.
         * @throws  IllegalArgumentException If any of the fields are invalid.
         */
        public FileBuffer build(){
            Validator.notNegative(fileId, "The id the File in the FileBuffer cannot be negative.");
            Validator.notNull(text, "The text in the FileBuffer cannot be null.");
            Validator.notNull(cursor, "The cursor of the FileBuffer cannot be null.");
            Validator.notNull(state,"The state of the FileBuffer cannot be null.");
            return new FileBuffer(this);
        }
    }
}
