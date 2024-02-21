package com.Textr.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileRepository {

    private final List<File> files;

    protected FileRepository(){
        this.files = new ArrayList<>();
    }

    protected void addFile(File file){
        files.add(Objects.requireNonNull(file, "Cannot add a null file to the repo"));
    }

    protected List<File> getFiles(){
        return files;
    }
}
