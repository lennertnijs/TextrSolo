package com.Textr.Controller;

import com.Textr.FileModel.File;
import com.Textr.FileModel.FileBufferService;
import com.Textr.FileModel.FileService;
import com.Textr.TerminalModel.TerminalService;
import com.Textr.TerminalModel.TerminalView;
import com.Textr.TerminalModel.TerminalViewService;

import java.util.Objects;

/**
 * Neemt input (args, keypresses) en callt de correcte methodes
 */
public class FileController {

    private final FileService fileService;
    private final FileBufferService fileBufferService;
    private final TerminalViewService terminalViewService;
    private final TerminalService terminalService;

    public FileController(FileService fileService, FileBufferService fileBufferService,TerminalViewService terminalViewService, TerminalService terminalService){
        this.fileService = fileService;
        this.fileBufferService = fileBufferService;
        this.terminalViewService = terminalViewService;
        this.terminalService = terminalService;
    }

    public void loadFiles(String[] files){
        for(String filePath: files){
            Objects.requireNonNull(filePath, "Cannot create an internal file for a null file");
            fileService.initialiseFile(filePath);
        }
        for(File file: fileService.getAllFiles()){
            fileBufferService.initialiseFileBuffer(file);
        }
        terminalViewService.initialiseTerminalViewsVertical(terminalService.getTerminalArea().get());
        int i = 0;
        for(TerminalView terminalView : terminalViewService.getAllTerminalViews()){
            terminalService.printText(terminalView.getPosition(), fileService.getAllFiles().get(i).getText());
            i++;
        }
    }


    /**
     * Process all the inputs. Probably usefull to use STATIC to name the important keys:
     * E.g. static int S = (ascii code of S)
     * Needs a way to remember a CTRL press. Havent thought about this.
     *
     * While it is important to handle all inputs here, it might be a good idea to split it up into move functions?
     */
}
