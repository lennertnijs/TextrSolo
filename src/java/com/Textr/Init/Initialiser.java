package com.Textr.Init;

import com.Textr.File.File;
import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

public class Initialiser {
    public static void initialise(FileService fs, FileBufferService fbs, ViewService vs, String[] args){
        String[] files = ArgumentHandler.handleArguments(args);
        InputHandlerRepo.initialiseInputHandlers(fbs, vs);

        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();

        for(String file: files){
            fs.initialiseFile(file);
        }
        for(File file : fs.getAllFiles()){
            fbs.initialisePassiveFileBuffer(file);
        }
        fbs.setActiveFileBuffer(0);
        vs.initialiseViewsVertical();
        vs.drawAllViews();
        vs.drawCursor();
    }
}
