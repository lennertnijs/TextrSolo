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

    /**
     * Undoes the last action performed on this buffer.
     */
    public void undo(){
        changeHistory.undo();
    }

    /**
     * Redoes the last action that was undone on this buffer, since last newly performed action.
     */
    public void redo(){
        changeHistory.redo();
    }


    /**
     * Executes the given action, and saves it into this buffer's action history.
     * @param action The action to perform on the buffer
     */
    public void executeAndStore(Action action){
        changeHistory.executeAndAddAction(action);
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

    public void addTextListener(TextListener listener) {
        this.text.addListener(listener);
    }

    public void removeTextListener(TextListener listener) {
        this.text.removeListener(listener);
    }

    public int getReferenceCount() {
        return this.text.getListenerCount();
    }
}
