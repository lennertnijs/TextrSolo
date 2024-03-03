package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.Util.Point;
import com.Textr.Validator.Validator;
import com.Textr.View.Direction;

import java.util.Objects;

/**
 * Represents a buffer.
 */
public final class FileBuffer {

    private final int id;
    private final int fileId;
    private final Text text;
    private final Point cursor;
    private BufferState state;

    /**
     * Constructor for a file buffer.
     * A file buffer is used to temporarily store changes to the file's text until the changes are saved.
     * Uses a static file buffer builder to ensure creation of valid file buffers.
     * @param builder The file buffer builder. Cannot be null.
     */
    private FileBuffer(Builder builder){
        Validator.notNull(builder, "Cannot build a FileBuffer because the Builder is null.");
        this.id = FileBufferIdGenerator.getId();
        this.fileId = builder.fileId;
        this.text = builder.text;
        this.cursor = builder.cursor;
        this.state = builder.state;
    }

    /**
     * @return This file buffer's id.
     */
    public int getId(){
        return this.id;
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
        cursor.incrementX();
    }

    /**
     * Removes the character before the insertion {@link Point} from this {@link FileBuffer}'s buffer {@link Text}.
     * Used when backspace is pressed.
     */
    public void removeCharacter(){
        int lineAboveLength = text.getLineLength(Math.max(0, cursor.getY() - 1));
        int oldAmountOfLines = text.getAmountOfLines();
        text.removeCharacter(cursor.getY(), cursor.getX() - 1);
        int newAmountOfLines = text.getAmountOfLines();
        boolean deletedALine = newAmountOfLines < oldAmountOfLines;
        if(deletedALine){
            cursor.decrementY();
            cursor.setX(lineAboveLength);
        }else{
            cursor.decrementX();
        }
    }

    public void removeNextCharacter(){
        text.removeCharacter(cursor.getY(), cursor.getX());
    }

    /**
     * Splits the text up into a new line at the insertion {@link Point}.
     * Used when Enter is pressed.
     * Also moves the insertion {@link Point} to the appropriate location.
     */
    public void createNewLine(){
        text.splitLineAtColumn(cursor.getY(), cursor.getX());
        cursor.setX(0);
        cursor.incrementY();
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
        return String.format("FileBuffer[id = %d, fileId = %d, text = %s, cursor = %s, state = %s]",
                id, fileId, text, cursor, state);
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
        private Text text = null;
        private Point cursor = null;
        private BufferState state = null;

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
        public Builder text(Text text){
            this.text = text;
            return this;
        }

        /**
         * Sets the insertion point index of this {@link FileBuffer.Builder} to the given index.
         * @param cursor The position
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder cursor(Point cursor){
            this.cursor = cursor;
            return this;
        }

        /**
         * Sets the {@link BufferState} of this {@link FileBuffer.Builder} to the given state.
         * @param state The state
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder state(BufferState state){
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
            Validator.notNegative(fileId, "The id the File in the FileBuffer cannot be negative.");
            Validator.notNull(text, "The buffer text in the FileBuffer cannot be null.");
            Validator.notNull(cursor, "The insertion point of the FileBuffer cannot be null.");
            Validator.notNull(state,"The state of the FileBuffer cannot be null.");
            return new FileBuffer(this);
        }
    }
}
