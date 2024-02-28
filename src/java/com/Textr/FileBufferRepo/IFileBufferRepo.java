package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;

public interface IFileBufferRepo {

    int getSize();
    FileBuffer getBuffer(int id);
    void addBuffer(FileBuffer fileBuffer);
    int getActiveBufferId();
    FileBuffer getActiveBuffer();
    void setActiveBuffer(FileBuffer fileBuffer);
    void removeBuffer(int id);
    void removeActiveBuffer();
    List<FileBuffer> getAllBuffers();
    void setActiveToNext();
    void setActiveToPrevious();
}
