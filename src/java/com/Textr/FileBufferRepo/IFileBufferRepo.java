package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;
import java.util.Optional;

public interface IFileBufferRepo {

    int getSize();
    Optional<FileBuffer> getBuffer(int id);
    void addBuffer(FileBuffer fileBuffer);
    int getActiveBufferId();
    FileBuffer getActiveBuffer();
    void setActiveBuffer(FileBuffer fileBuffer);
    void removeBuffer(int id);
    List<FileBuffer> getAllBuffers();
    FileBuffer nextBuffer(int id);
    FileBuffer prevBuffer(int id);
}
