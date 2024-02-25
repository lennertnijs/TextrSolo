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

    /**
     * Process all the inputs. Probably usefull to use STATIC to name the important keys:
     * E.g. static int S = (ascii code of S)
     * Needs a way to remember a CTRL press. Havent thought about this.
     *
     * While it is important to handle all inputs here, it might be a good idea to split it up into move functions?
     */
}
