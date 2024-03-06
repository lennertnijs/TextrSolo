package com.Textr.FileBuffer;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;

public interface IFileBufferRepo {

    boolean hasActiveBuffer();
    int getSize();
    FileBuffer getBuffer(int id);
    void addBuffer(FileBuffer fileBuffer);
    int getActiveBufferId();
    FileBuffer getActiveBuffer();
    void setActiveBuffer(FileBuffer fileBuffer);
    void removeBuffer(int id);
    void removeActiveBuffer();
    void removeAllBuffers();
    List<FileBuffer> getAllBuffers();
    void setActiveToNext();
    void setActiveToPrevious();
}
