package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;

import java.util.List;
import java.util.Optional;

interface IAllFileBufferRepo {

    int getSize();

    Optional<FileBuffer> get(int id);

    void add(FileBuffer fileBuffer);

    void remove(int id);

    FileBuffer next(int id);

    FileBuffer prev(int id);

    List<FileBuffer> getAll();
}
