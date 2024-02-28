package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;

interface IAllFileBuffersRepo {

    int getSize();
    FileBuffer get(int id);
    List<FileBuffer> getAll();
    void add(FileBuffer fileBuffer);
    void remove(int id);
    FileBuffer getNext(int id);
    FileBuffer getPrevious(int id);
}
