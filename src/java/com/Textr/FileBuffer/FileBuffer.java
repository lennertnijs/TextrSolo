package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;
import com.Textr.Util.Direction;

/**
 * Represents a buffer.
 */
public final class FileBuffer {

    private final File file;
    private final Text text;
    private final Point cursor;
    private BufferState state;

    private FileBuffer(File file, Text text, Point cursor, BufferState state){
        this.file = file;
        this.text = text;
        this.cursor = cursor;
        this.state = state;
    }

    public static FileBuffer create(File file, Text text, Point cursor, BufferState state){
        Validator.notNull(file, "The id the File in the FileBuffer cannot be negative.");
        Validator.notNull(text, "The text in the FileBuffer cannot be null.");
        Validator.notNull(cursor, "The cursor of the FileBuffer cannot be null.");
        Validator.notNull(state,"The state of the FileBuffer cannot be null.");
        return new FileBuffer(file.copy(), text.copy(), cursor.copy(), state);
    }

    /**
     * @return This file buffer's {@link File} id.
     */
    public File getFile(){
        return this.file;
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
        int oldY = cursor.getY();
        int oldX = cursor.getX();
        CursorMover.move(cursor, Direction.LEFT, text);
        text.removeCharacter(oldY, oldX - 1);
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
        return this.file.equals(fileBuffer.file) &&
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
        int result = file.hashCode();
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
        return String.format("FileBuffer[fileId = %s, text = %s, cursor = %s, state = %s]",
                file, text, cursor, state);
    }

    public FileBuffer copy(){
        return new FileBuffer(this.file.copy(), this.text.copy(), this.cursor.copy(), this.state);
    }
}
