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

    /**
     * The amount of references to this FileBuffer.
     */
    private int referenceCount;

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
     * Returns the amount of references to this FileBuffer.
     * @return the amount of references to this FileBuffer
     */
    public int getReferenceCount() {
        return this.referenceCount;
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
        changeHistory.undo(cursor);
    }

    public void redo(ICursor cursor){
        changeHistory.redo(cursor);
    }


    public void executeAndStore(Action action, ICursor cursor){
        changeHistory.executeAndAddAction(action, cursor);
        setState(BufferState.DIRTY);
    }

    /**
     * Writes this {@link FileBuffer}'s {@link IText} content's to its {@link File}.
     */
    public void writeToDisk(){
        FileWriter.write(text.getContent(), file);
        this.setState(BufferState.CLEAN);
    }

    /**
     * Adds one to the tracked reference count.
     */
    public void incrementReferenceCount() {
        this.referenceCount += 1;
    }

    /**
     * Decrements the reference count by one.
     * @throws IllegalStateException if reference count is already at 0
     */
    public void decrementReferenceCount() {
        if (this.referenceCount == 0)
            throw new IllegalStateException("Cannot lower reference count below 0");
        this.referenceCount -= 1;
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
