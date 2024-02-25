package com.Textr.FileModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBufferRepo {

    private final List<FileBuffer> fileBufferList = new ArrayList<>();

    protected FileBufferRepo(){
    }

    protected void addFileBuffer(FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer, "Cannot store a null FileBuffer.");
        fileBufferList.add(fileBuffer);
    }

    protected List<FileBuffer> getAllFileBuffers(){
        return this.fileBufferList;
    }
}
