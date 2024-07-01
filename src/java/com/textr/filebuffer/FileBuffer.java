package com.textr.filebuffer;

import com.textr.file.FileReader;
import com.textr.file.FileWriter;
import com.textr.filebufferV2.BufferState;
import com.textr.filebufferV2.IText;
import com.textr.filebufferV2.LineText;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a buffer.
 */
public final class FileBuffer {

    private final File file;
    private final IText text;
    private BufferState state;
    private final Set<TextListener> listeners;

    private FileBuffer(File file, IText text, BufferState state){
        this.file = file;
        this.text = text;
        this.state = state;
        this.listeners = new HashSet<>();
    }

    public static FileBuffer createFromFilePath(String url){
        File file = new File(Objects.requireNonNull(url, "Url is null."));
        String fileContents = FileReader.readContents(file);
        LineText text = new LineText(fileContents);
        return new FileBuffer(file, text, BufferState.CLEAN);
    }

    /**
     * @return The {@link File}.
     */
    public File getFile(){
        return file;
    }

    /**
     * @return A copy of the {@link IText}.
     */
    public IText getText(){
        return text.copy();
    }

    /**
     * Creates and returns the respective 2-dimensional insert point for the given 1-dimensional insert index.
     * @param index The index. Cannot be negative. Cannot be bigger than the internal text's length.
     *
     * @return The insert point.
     */
    public Point getInsertPoint(int index){
        return text.getInsertPoint(index);
    }

    /**
     * @return The state of the buffer.
     */
    public BufferState getState(){
        return state;
    }

    /**
     * Sets the state of this file buffer to the given state.
     * @param state The new state. Cannot be null.
     */
    public void setState(BufferState state) {
        this.state = Objects.requireNonNull(state, "State is null.");
    }

    /**
     * Moves the 1-dimensional insert index 1 unit in the given direction, within the context of the internal text.
     * @param index The index. Cannot be negative. Cannot be larger than the length of the internal text.
     * @param direction The direction. Cannot be null.
     *
     * @return The updated insert index.
     */
    public int moveCursor(int index, Direction direction){
        return text.move(direction, index);
    }

    public int insert(char c, int index){
        text.insert(c, index);
        for(TextListener listener : listeners){
            listener.doUpdate(new TextUpdate(getInsertPoint(index), OperationType.INSERT_CHARACTER));
        }
        return index + 1;
    }

    public void delete(int index){
        text.delete(index);
    }

    public void addTextListener(TextListener listener) {
        listeners.add(listener);
    }

    public void removeTextListener(TextListener listener) {
        listeners.remove(listener);
    }

    public int getReferenceCount() {
        return listeners.size();
    }

    /**
     * Writes this {@link FileBuffer}'s {@link IText} content's to its {@link File}.
     * @throws IOException if something went wrong when writing to the file
     */
    public void writeToDisk() throws IOException {
        FileWriter.write(text.getContent(), file);
        this.state = BufferState.CLEAN;
    }

    /**
     * Compares this file buffer to the given object and returns True if they're equal.
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof FileBuffer fileBuffer))
            return false;
        return file.equals(fileBuffer.file) &&
                text.equals(fileBuffer.text) &&
                state == fileBuffer.state;
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
}
