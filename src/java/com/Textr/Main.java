package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.FileRepo.FileRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;
import com.Textr.ViewRepo.ViewRepo;

public class Main {

    public static void main(String[] args){
        final FileRepo fileRepo = new FileRepo();
        final FileService fileService = new FileService(fileRepo);
        final FileBufferService fileBufferService = new FileBufferService(fileService);

        final ViewRepo viewRepo = new ViewRepo();
        final ViewService viewService = new ViewService(fileBufferService, viewRepo);
        final FileController fileController = new FileController(fileService, fileBufferService, viewService);
        Settings.loadDefaultLineSeparator();
        fileController.loadFiles(args);
        TerminalService.enterRawInputMode();
        while(true){
            fileController.handleInput();
        }
    }
}