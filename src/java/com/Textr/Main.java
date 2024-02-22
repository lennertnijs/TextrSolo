package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileModel.FileService;
import com.Textr.TerminalModel.Rectangle;
import com.Textr.TerminalModel.TerminalService;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final FileService fileService = new FileService();
        final FileController fileController = new FileController(fileService);

        final TerminalService terminalService = new TerminalService();

        fileController.loadFiles(args);
        Terminal.enterRawInputMode();
        Terminal.clearScreen();
        Rectangle rect = terminalService.getTerminalArea().get();
        Terminal.printText(15, 15, String.format("%s x %s", rect.getWidth(), rect.getHeight()));
        Terminal.leaveRawInputMode();
    }
}