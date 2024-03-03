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

    public void loadFiles(String[] files){
        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();
        String[] f = Arrays.copyOfRange(files, 0, files.length);
        if(files[0].contains("--")){
            loadSettings(files[0]);
            f = Arrays.copyOfRange(files, 1, files.length);
        }
        for(String filePath: f){
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

    private void loadSettings(String input){
        String str = input.replace("--", "");
        switch(str){
            case "lf":
                Settings.defaultLineSeparator = DefaultLineSeparator.LF;
                break;
            case "cr":
                Settings.defaultLineSeparator = DefaultLineSeparator.CR;
                break;
            case "crlf":
                Settings.defaultLineSeparator = DefaultLineSeparator.CRLF;
                break;
        }
    }

    public void handleInput(){
        inputHandler.handleInput();
    }
}
