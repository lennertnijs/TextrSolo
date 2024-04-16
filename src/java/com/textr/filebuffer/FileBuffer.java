package com.textr.filebuffer;

import com.textr.file.FileReader;
import com.textr.file.FileWriter;
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
     * Inserts the given character at the given index in this {@link FileBuffer}'s {@link IText}.
     * @param index The index. Cannot be negative, or bigger than the lengths of the current text.
     * @param character The character.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void insertCharacter(int index, char character){
        if(index < 0 || index > text.getCharAmount())
            throw new IllegalArgumentException("Index is illegal.");
        text.insert(index, character);
        changeHistory.addInsertAction(index, character);
        this.setState(BufferState.DIRTY);
    }

    /**
     * Removes the character at the given index in this {@link FileBuffer}'s {@link IText}.
     * @param index The index. Cannot be negative or equal/bigger than the lengths of the current text.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void removeCharacter(int index){
        if(index < 0 || index >= text.getCharAmount())
            throw new IllegalArgumentException("Index is illegal.");
        char removedCharacter = text.getCharacter(index);
        text.remove(index);
        changeHistory.addDeleteAction(index, removedCharacter);
        this.setState(BufferState.DIRTY);
    }

    /**
     * Inserts a line break at the given index in this {@link FileBuffer}'s {@link IText}.
     * @param index The index. Cannot be negative or bigger than the lengths of the current text.
     *
     * @throws IllegalArgumentException If the index is illegal.
     */
    public void insertLineBreak(int index){
        if(index < 0 || index > text.getCharAmount())
            throw new IllegalArgumentException("Index is illegal.");
        text.insertLineBreak(index);
        changeHistory.addInsertAction(index, '\n');
        this.setState(BufferState.DIRTY);
    }

    /**
     * Writes this {@link FileBuffer}'s {@link IText} content's to its {@link File}.
     */
    public void writeToDisk(){
        FileWriter.write(text.getContent(), file);
        this.setState(BufferState.CLEAN);
    }

    /**
     * Compares this file buffer to the given object and returns True if they're equal.
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
