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
     * Checks whether there is an active {@link FileBuffer}. Returns True if so, False otherwise.
     * @return True if there's an active {@link FileBuffer}, False otherwise.
     */
    @Override
    public boolean isEmpty(){
        return activeFileBuffer == null;
    }


    /**
     * Returns the id of the active {@link FileBuffer}.
     *
     * @return The id
     * @throws NullPointerException If the active {@link FileBuffer} is null.
     */
    @Override
    public int getBufferId(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the id of the active FileBuffer because it's null.");
        return activeFileBuffer.getId();
    }


    /**
     * Returns the active {@link FileBuffer}, if one is set. Throws an Exception otherwise.
     *
     * @return The active {@link FileBuffer}.
     * @throws NullPointerException If the active {@link FileBuffer} is null.
     */
    @Override
    public FileBuffer getBuffer(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the active FileBuffer because it is null.");
        return activeFileBuffer;
    }


    /**
     * Overwrite the active {@link FileBuffer} with the given {@link FileBuffer}.
     * @param fileBuffer The new active {@link FileBuffer}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link FileBuffer} is null.
     */
    @Override
    public void setBuffer(FileBuffer fileBuffer){
        Validator.notNull(fileBuffer, "Cannot set the active FileBuffer to null. (use deleteBuffer() to do so)");
        activeFileBuffer = fileBuffer;
    }


    /**
     * Overwrites the active {@link FileBuffer} with null.
     * USE WITH CARE
     */
    @Override
    public void deleteBuffer(){
        activeFileBuffer = null;
    }
}
