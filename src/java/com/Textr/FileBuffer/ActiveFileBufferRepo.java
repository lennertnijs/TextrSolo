package com.Textr.FileBuffer;

import java.util.Objects;

public class ActiveFileBufferRepo {

    private static FileBuffer activeFileBuffer;

    private ActiveFileBufferRepo(){
    }

    protected static int getBufferId(){
        return activeFileBuffer.getId();
    }

    protected static FileBuffer getBuffer(){
        return activeFileBuffer;
    }
    protected static void setBuffer(FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer);
        activeFileBuffer = fileBuffer;
    }
}
