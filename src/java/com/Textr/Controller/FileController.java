package com.Textr.Controller;

import com.Textr.File.File;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.InputHandler.InputHandler;
import com.Textr.InputHandler.RawInputHandler;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import java.util.Objects;

public class FileController {

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

    public void loadFiles(String[] files){
        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();
        for(String filePath: files){
            Objects.requireNonNull(filePath, "Cannot create an internal file for a null file");
            fileService.initialiseFile(filePath);
        }
        for(int i = 0 ; i < fileService.getAllFiles().size(); i++){
            File file = fileService.getAllFiles().get(i);
            if(i == 0){
                fileBufferService.initialiseActiveFileBuffer(file);
            }else{
                fileBufferService.initialisePassiveFileBuffer(file);
            }
        }
        // Create TerminalViews
        viewService.initialiseViewsVertical();
        viewService.drawAllViews();
        viewService.drawCursor();
        TerminalService.leaveRawInputMode();
    }

    public void handleInput(){
        inputHandler.handleInput();
    }
}
