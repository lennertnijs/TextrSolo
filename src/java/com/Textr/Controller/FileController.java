package com.Textr.Controller;

import com.Textr.FileModel.File;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.FileModel.FileService;
import com.Textr.TerminalModel.ViewService;

import java.util.Objects;

/**
 * Neemt input (args, keypresses) en callt de correcte methodes
 */
public class FileController {

    private final FileService fileService;
    private final FileBufferService fileBufferService;
    private final ViewService viewService;

    public FileController(FileService fileService, FileBufferService fileBufferService, ViewService viewService){
        this.fileService = fileService;
        this.fileBufferService = fileBufferService;
        this.viewService = viewService;
    }

    public void loadFiles(String[] files){
        // create behind the scene Files
        for(String filePath: files){
            Objects.requireNonNull(filePath, "Cannot create an internal file for a null file");
            fileService.initialiseFile(filePath);
        }
        // create a FileBuffer for each File
        for(File file: fileService.getAllFiles()){
            fileBufferService.initialiseFileBuffer(file);
        }
        // Create TerminalViews
        viewService.initialiseTerminalViewsVertical();
        viewService.drawAllViews();
    }


    /**
     * Process all the inputs. Probably usefull to use STATIC to name the important keys:
     * E.g. static int S = (ascii code of S)
     * Needs a way to remember a CTRL press. Havent thought about this.
     *
     * While it is important to handle all inputs here, it might be a good idea to split it up into move functions?
     */
}
