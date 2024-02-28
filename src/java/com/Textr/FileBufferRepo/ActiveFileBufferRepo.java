package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.Objects;
import java.util.Optional;

public final class ActiveFileBufferRepo implements IActiveFileBufferRepo {

    private static FileBuffer activeFileBuffer = null;

    public ActiveFileBufferRepo(){
    }

    /**
     * @return The id of the active {@link FileBuffer}
     * @throws NullPointerException If the active {@link FileBuffer} is null.
     */
    @Override
    public int getBufferId(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the id of the active FileBuffer because it is null.");
        return activeFileBuffer.getId();
    }

    /**
     * @return An {@link Optional} of the active {@link FileBuffer}, if one exists. An empty {@link Optional} otherwise.
     */
    @Override
    public FileBuffer getBuffer(){
        Objects.requireNonNull(activeFileBuffer, "Cannot retrieve the active FileBuffer because it is null.");
        return activeFileBuffer;
    }

    /**
     * Sets the active {@link FileBuffer} to the given {@link FileBuffer}.
     * @param fileBuffer The new active {@link FileBuffer}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link FileBuffer} is null.
     */
    @Override
    public void setBuffer(FileBuffer fileBuffer){
        try{
            Objects.requireNonNull(fileBuffer);
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Cannot set the active FileBuffer to null.");
        }
        activeFileBuffer = fileBuffer;
    }
}
