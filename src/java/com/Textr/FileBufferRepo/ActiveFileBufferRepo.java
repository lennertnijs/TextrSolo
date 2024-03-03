package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Validator.Validator;

import java.util.Objects;

public final class ActiveFileBufferRepo implements IActiveFileBufferRepo {

    private FileBuffer activeFileBuffer;

    public ActiveFileBufferRepo(){
        activeFileBuffer = null;
    }


    /**
     * Checks whether the active file buffer is empty (no active buffer). Returns True if so, False otherwise.
     * @return True if no active file buffer is set. False if one has been set.
     */
    @Override
    public boolean isEmpty(){
        return activeFileBuffer == null;
    }


    /**
     * Returns the id of the active file buffer.
     *
     * @return The id
     * @throws NullPointerException If the active file buffer is null.
     */
    @Override
    public int getBufferId(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the id of the active FileBuffer because it's null.");
        return activeFileBuffer.getId();
    }


    /**
     * Returns the active file buffer if one is set. Throws an Exception otherwise.
     *
     * @return The active file buffer.
     * @throws NullPointerException If the active file buffer is null.
     */
    @Override
    public FileBuffer getBuffer(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the active FileBuffer because it is null.");
        return activeFileBuffer;
    }


    /**
     * Overwrite the active file buffer with the given file buffer.
     * @param fileBuffer The new active file buffer. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed file buffer is null.
     */
    @Override
    public void setBuffer(FileBuffer fileBuffer){
        Validator.notNull(fileBuffer, "Cannot set the active FileBuffer to null. (use deleteBuffer() to do so)");
        activeFileBuffer = fileBuffer;
    }


    /**
     * Empties the active file buffer.
     * USE WITH CARE
     */
    @Override
    public void deleteBuffer(){
        activeFileBuffer = null;
    }
}
