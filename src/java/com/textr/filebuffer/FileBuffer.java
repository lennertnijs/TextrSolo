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

    public int getInsertIndex(){
        return text.getInsertIndex();
    }

    public Point getInsertPoint(){
        return text.getInsertPoint();
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
        this.state = Objects.requireNonNull(state, "State is null.");
    }

    public void moveCursor(Direction direction){
        text.move(direction);
    }

    /**
     * Writes this {@link FileBuffer}'s {@link IText} content's to its {@link File}.
     * @throws IOException if something went wrong when writing to the file
     */
    public void writeToDisk() throws IOException {
        FileWriter.write(text.getContent(), file);
        setState(BufferState.CLEAN);
    }

    public void insert(char c, int index){
        text.insert(index, c);
        for(TextListener listener : listeners){
            listener.doUpdate(new TextUpdate(getInsertPoint(), OperationType.INSERT_CHARACTER));
        }
    }


    public void updateAfterInsert(char c){
        for (TextListener listener: listeners)
            listener.doUpdate(new TextUpdate(getInsertPoint(), OperationType.INSERT_CHARACTER));
    }

    public void updateAfterRemove(char c){
        for (TextListener listener: listeners)
            listener.doUpdate(new TextUpdate(getInsertPoint(), OperationType.INSERT_CHARACTER));
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
     * Compares this file buffer to the given object and returns True if they're equal.
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof FileBuffer fileBuffer))
            return false;
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
}
