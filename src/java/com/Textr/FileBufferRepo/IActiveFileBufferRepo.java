package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

interface IActiveFileBufferRepo {

    int getBufferId();
    FileBuffer getBuffer();
    void setBuffer(FileBuffer fileBuffer);
}
