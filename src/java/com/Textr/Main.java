package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileModel.FileBufferService;
import com.Textr.FileModel.FileService;
import com.Textr.TerminalModel.TerminalService;
import com.Textr.TerminalModel.ViewService;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final FileService fileService = new FileService();
        final FileBufferService fileBufferService = new FileBufferService();
        final TerminalService terminalService = new TerminalService();
        final ViewService viewService = new ViewService(fileBufferService, terminalService);
        final FileController fileController = new FileController(fileService, fileBufferService, viewService);

        terminalService.enterRawInputMode();
        terminalService.clearScreen();
        fileController.loadFiles(args);
//        Dimension2D rect = terminalService.getTerminalArea().get();
//        List<File> files = fileService.getAllFiles();
//        Terminal.printText(5, 5, String.format("%s x %s", rect.getWidth(), rect.getHeight()));
//        Terminal.printText(15, 15,String.valueOf(files.get(0).getText()));
        Terminal.leaveRawInputMode();
        while(true){

        }
    }
}