package com.Textr.Controller;

import com.Textr.File.File;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.Settings;
import com.Textr.View.ViewService;


public final class FileController {

    private final FileService fileService;
    private final FileBufferService fileBufferService;
    private final ViewService viewService;

    public FileController(FileService fileService, FileBufferService fileBufferService, ViewService viewService){
        this.fileService = fileService;
        this.fileBufferService = fileBufferService;
        this.viewService = viewService;
    }

    public void loadFiles(String[] args){
        String[] files = ArgumentHandler.handleArguments(args);
        for(String file: files){
            fileService.initialiseFile(file);
        }
        for(File file : fileService.getAllFiles()){
            fileBufferService.initialisePassiveFileBuffer(file);
        }
        fileBufferService.setActiveFileBuffer(0);
        viewService.initialiseViewsVertical();
        viewService.drawAllViews();
        viewService.drawCursor();
    }

    public void handleInput(){
        Settings.inputHandler.handleInput();
    }
}
