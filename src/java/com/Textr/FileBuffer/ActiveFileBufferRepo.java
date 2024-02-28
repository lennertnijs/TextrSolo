package com.Textr.FileBuffer;

import java.util.Objects;

public class ActiveFileBufferRepo {

    private static FileBuffer activeFileBuffer;

    /**
     * Unreachable constructor. This class should only be used statically.
     */
    private ActiveFileBufferRepo(){
    }

    /**
     * @return The id of the active {@link FileBuffer}
     */
    protected static int getBufferId(){
        return activeFileBuffer.getId();
    }

    /**
     * @return The active {@link FileBuffer}
     */
    protected static FileBuffer getBuffer(){
        return activeFileBuffer;
    }

    /**
     * Sets the active {@link FileBuffer} to the given {@link FileBuffer}.
     * @param fileBuffer The new active {@link FileBuffer}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link FileBuffer} is null.
     */
    protected static void setBuffer(FileBuffer fileBuffer){
        try{
            Objects.requireNonNull(fileBuffer);
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Cannot set the active FileBuffer to null.");
        }
        activeFileBuffer = fileBuffer;
    }
}
