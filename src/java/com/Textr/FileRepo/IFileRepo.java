package com.Textr.FileRepo;

import com.Textr.File.File;

import java.util.List;

public interface IFileRepo {
    boolean contains(int id);
    int getSize();
    File get(int id);
    List<File> getAll();
    void add(File file);
    void remove(int id);
}
