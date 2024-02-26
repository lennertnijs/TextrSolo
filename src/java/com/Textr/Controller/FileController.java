package com.Textr.Controller;

import com.Textr.File.File;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.Terminal.TerminalService;
import com.Textr.Terminal.ViewService;

import java.util.Objects;

import static com.Textr.Inputs.*;

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
        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();
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
        TerminalService.leaveRawInputMode();
    }

    public void handleInput(){
        int b = TerminalService.readByte();
        switch(b){
            case CTRL_S:
                // do something
            case CTRL_P:
                // do something
        }
    }
}
