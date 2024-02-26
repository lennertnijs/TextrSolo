package com.Textr.FileBuffer;

import com.Textr.FileBuffer.FileBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    protected Optional<FileBuffer> get(int id){
        return fileBufferList.stream().filter(e -> e.getId() == id).findFirst();
    }
}
