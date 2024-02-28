package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.Optional;

interface IActiveFileBufferRepo {

    int getBufferId();
    Optional<FileBuffer> getBuffer();
    void setBuffer(FileBuffer fileBuffer);
}
