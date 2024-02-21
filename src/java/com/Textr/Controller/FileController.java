package com.Textr.Controller;

import com.Textr.Model.FileService;

public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    public void loadFiles(String[] files){
        for(String filePath: files){
            fileService.createAndStoreFile(filePath);
        }
    }
}
