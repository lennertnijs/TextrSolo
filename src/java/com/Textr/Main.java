package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileModel.File;
import com.Textr.FileModel.FileBufferService;
import com.Textr.FileModel.FileService;
import com.Textr.TerminalModel.Dimension2D;
import com.Textr.TerminalModel.TerminalService;
import com.Textr.TerminalModel.TerminalViewService;
import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        final FileService fileService = new FileService();
        final FileBufferService fileBufferService = new FileBufferService();
        final TerminalService terminalService = new TerminalService();
        final TerminalViewService terminalViewService = new TerminalViewService(fileBufferService);
        final FileController fileController = new FileController(fileService, fileBufferService, terminalViewService, terminalService);

        fileController.loadFiles(args);
        terminalService.enterRawInputMode();
        terminalService.clearScreen();
        Dimension2D rect = terminalService.getTerminalArea().get();
        List<File> files = fileService.getAllFiles();
        Terminal.printText(5, 5, String.format("%s x %s", rect.getWidth(), rect.getHeight()));
        Terminal.printText(15, 15,String.valueOf(files.get(0).getText()));
        Terminal.leaveRawInputMode();
        while(true){

        }
    }
}