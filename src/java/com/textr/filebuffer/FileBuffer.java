package com.textr.filebuffer;

import com.textr.file.FileReader;
import com.textr.file.FileWriter;
import com.textr.util.Point;
import com.textr.util.Validator;

import java.io.File;

/**
 * Represents a buffer.
 */
public final class FileBuffer {

    private final File file;
    private final IText text;
    private final ChangeHistory changeHistory;
    private BufferState state;

    private FileBuffer(File file, IText text, BufferState state){
        this.file = file;
        this.text = text;
        this.changeHistory = new ChangeHistory();
        this.state = state;
    }

    public static FileBuffer createFromFilePath(String url){
        Validator.notNull(url, "Cannot create a FileBuffer from a null url.");
        File f = new File(url);
        LineText t = LineText.createFromString(FileReader.readContents(f));
        return new FileBuffer(f, t, BufferState.CLEAN);
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
    public IText getText(){
        return this.text;
    }


    /**
     * @return This file buffer's state.
     */
    public BufferState getState(){
        return this.state;
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

    public void undo(ICursor cursor){
        changeHistory.undo(text, cursor);
    }

    public void redo(ICursor cursor){
        changeHistory.redo(text, cursor);
    }

    /**
     * Inserts the given character into this {@link FileBuffer}'s buffer {@link Text} at the insertion {@link Point}.
     * @param character The character
     */
    public void insertCharacter(char character, ICursor cursor){
        changeHistory.addInsertAction(character, cursor);
        text.insertCharacter(character, cursor);
        this.setState(BufferState.DIRTY);
    }

    /**
     * Removes the character before the cursor from the text.
     * Also moves the cursor one unit to the left.
     * Used when backspace is pressed.
     */
    public void removeCharacterBefore(ICursor cursor){
        char character = text.getLines()[cursor.getInsertPoint().getY()].charAt(cursor.getInsertPoint().getX());
        changeHistory.addDeleteAction(character, cursor);
        text.removeCharacterBefore(cursor);
        this.setState(BufferState.DIRTY);
    }

    /**
     * Removes the character after the cursor from the text.
     * Does not move the cursor.
     * Used when delete is pressed.
     */
    public void removeCharacterAfter(ICursor cursor){
        text.removeCharacterAfter(cursor);
        this.setState(BufferState.DIRTY);
    }

    /**
     * Breaks the line of the text into 2 new lines (seperated by a space), at the cursor.
     * Also moves the cursor into the new line.
     * Used when enter is pressed.
     */
    public void createNewLine(ICursor cursor){
        text.insertNewLine(cursor);
        this.setState(BufferState.DIRTY);
    }

    public void writeToDisk(){
        FileWriter.write(this.getText().getContent(), this.getFile());
        this.setState(BufferState.CLEAN);
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
        return String.format("FileBuffer[fileId = %s, text = %s, state = %s]",
                file, text, state);
    }

    public FileBuffer copy(){
        return new FileBuffer(this.file, this.text, this.state);
    }
}
