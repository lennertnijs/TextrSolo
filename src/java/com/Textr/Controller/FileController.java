package com.Textr.Controller;

import com.Textr.DefaultLineSeparator;
import com.Textr.File.File;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.InputHandler.InputHandler;
import com.Textr.InputHandler.RawInputHandler;
import com.Textr.Settings;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import java.util.Arrays;

public final class FileController {

    private final FileService fileService;
    private final FileBufferService fileBufferService;
    private final ViewService viewService;
    private InputHandler inputHandler;

    public FileController(FileService fileService, FileBufferService fileBufferService, ViewService viewService){
        this.fileService = fileService;
        this.fileBufferService = fileBufferService;
        this.viewService = viewService;
        this.inputHandler = new RawInputHandler(viewService, fileBufferService);
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
        inputHandler.handleInput();
    }
}
