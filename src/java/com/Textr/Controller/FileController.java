package com.Textr.Controller;

import com.Textr.FileModel.FileService;

import java.util.Objects;

public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    public void loadFiles(String[] files){
        for(String filePath: files){
            Objects.requireNonNull(filePath, "Cannot create an internal file for a null file");
            fileService.createAndStoreFile(filePath);
        }
    }



    private int getAsciiFromString(String str){
        if(str.length() != 1){
            throw new IllegalArgumentException("Cannot happen dawg");
        }
        return str.charAt(0);
    }
}
