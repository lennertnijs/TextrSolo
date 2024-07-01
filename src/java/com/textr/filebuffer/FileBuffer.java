package com.textr.filebuffer;

import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
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
    public static IFileReader fileReader;
    private static IFileWriter fileWriter;

    public FileBuffer(File file){
        this.file = Objects.requireNonNull(file, "File is null.");
        this.text = new LineText(fileReader.read(file));
        this.state = BufferState.CLEAN;
        this.listeners = new HashSet<>();
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
    public int move(int index, Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        return text.move(direction, index);
    }

    /**
     * Inserts the given character at the given index in the text. DOES NOT MOVE THE INSERT INDEX
     * After deletion. notifies all listeners of the update.
     * @param c The character.
     * @param index The index. Cannot be negative. Cannot be larger than the length of the internal text.
     */
    public void insert(char c, int index){
        text.insert(c, index);
        notifyListeners(new TextUpdate(getInsertPoint(index), OperationType.INSERT_CHARACTER));
    }

    /**
     * Deletes the character at the given index from the text. DOES NOT MOVE THE INSERT INDEX.
     * After deletion. notifies all listeners of the update.
     * @param index The index. Cannot be negative. Cannot be equal/larger than the length of the internal text.
     */
    public void delete(int index){
        text.delete(index);
        notifyListeners(new TextUpdate(getInsertPoint(index), OperationType.DELETE_CHARACTER));
    }

    /**
     * Notifies all the listeners with the given update.
     * @param textUpdate The text update. Cannot be null.
     */
    private void notifyListeners(TextUpdate textUpdate){
        for(TextListener listener : listeners){
            listener.doUpdate(textUpdate);
        }
    }

    /**
     * Adds the given listener to the list of listeners.
     * @param listener The listener. Cannot be null.
     */
    public void addTextListener(TextListener listener) {
        listeners.add(Objects.requireNonNull(listener, "Listener is null."));
    }

    /**
     * Removes the given listener from the list of listeners.
     * @param listener The listener. Cannot be null.
     */
    public void removeTextListener(TextListener listener) {
        listeners.remove(Objects.requireNonNull(listener, "Listener is null."));
    }

    /**
     * @return The amount of distinct listeners.
     */
    public int getListenerCount() {
        return listeners.size();
    }

    /**
     * Writes the text's content to this file buffer's file.
     * Also sets the buffer state to CLEAN.
     *
     * @throws IOException If something went wrong when writing to the file.
     */
    public void writeToDisk() throws IOException {
        fileWriter.write(text.getContent(), file);
        this.state = BufferState.CLEAN;
    }

    /**
     * Sets the FileBuffer class' {@link IFileReader} to the given file reader.
     * @param fr The file reader. Cannot be null
     */
    public static void setFileReader(IFileReader fr){
        fileReader = Objects.requireNonNull(fr, "File reader is null.");
    }

    /**
     * Sets the FileBuffer class' {@link IFileWriter} to the given file writer.
     * @param fw The file writer. Cannot be null
     */
    public static void setFileWriter(IFileWriter fw){
        fileWriter = Objects.requireNonNull(fw, "File writer is null.");
    }

    /**
     * @return The string representation
     */
    @Override
    public String toString(){
        return String.format("FileBuffer[file=%s, text=%s, state=%s]", file, text, state);
    }
}
