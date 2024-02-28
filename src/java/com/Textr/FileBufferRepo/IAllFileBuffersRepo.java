package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;
import java.util.Optional;

interface IAllFileBuffersRepo {

    int getSize();

    Optional<FileBuffer> get(int id);

    void add(FileBuffer fileBuffer);

    void remove(int id);

    FileBuffer next(int id);

    FileBuffer prev(int id);

    List<FileBuffer> getAll();
}
