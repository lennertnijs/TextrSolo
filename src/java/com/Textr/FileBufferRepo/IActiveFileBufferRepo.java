package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

interface IActiveFileBufferRepo {
    boolean isEmpty();
    int getBufferId();
    FileBuffer getBuffer();
    void setBuffer(FileBuffer fileBuffer);
    void deleteBuffer();
}
