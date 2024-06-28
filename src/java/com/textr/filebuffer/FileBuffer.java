package com.textr.filebuffer;

import com.textr.file.FileReader;
import com.textr.file.FileWriter;
import com.textr.filebufferV2.IText;
import com.textr.filebufferV2.LineText;
import com.textr.util.Direction;
import com.textr.util.Validator;

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
    private final Cursor cursor;
    private BufferState state;
    private final Set<TextListener> listeners;

    private FileBuffer(File file, IText text, Cursor cursor, BufferState state){
        this.file = file;
        this.text = text;
        this.state = state;
        this.cursor = cursor;
        this.listeners = new HashSet<>();
    }

    public static FileBuffer createFromFilePath(String url){
        Objects.requireNonNull(url, "Url is null.");
        File file = new File(url);
        LineText text = new LineText(FileReader.readContents(file));
        return new FileBuffer(file, text, Cursor.createNew(), BufferState.CLEAN);
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

    public Cursor getCursor(){
        return cursor;
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

    public void moveCursor(Direction direction){
        cursor.move(direction, text);
    }

    /**
     * Writes this {@link FileBuffer}'s {@link IText} content's to its {@link File}.
     * @throws IOException if something went wrong when writing to the file
     */
    public void writeToDisk() throws IOException {
        FileWriter.write(text.getContent(), file);
        this.setState(BufferState.CLEAN);
    }

    public void updateAfterInsert(char c, int index){
        TextUpdateType t = c == '\n' ? TextUpdateType.LINE_UPDATE : TextUpdateType.CHAR_UPDATE;
        for (TextListener listener: listeners)
            listener.update(new TextUpdateReference(index, true, t), text);
    }

    public void updateAfterRemove(char c, int index){
        TextUpdateType t = c == '\n' ? TextUpdateType.LINE_UPDATE : TextUpdateType.CHAR_UPDATE;
        for (TextListener listener: listeners)
            listener.update(new TextUpdateReference(index, false, t), text);
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

    public void addTextListener(TextListener listener) {
        listeners.add(listener);
    }

    public void removeTextListener(TextListener listener) {
        listeners.remove(listener);
    }

    public int getReferenceCount() {
        return listeners.size();
    }
}
