package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.Terminal.ViewService;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final FileService fileService = new FileService();
        final FileBufferService fileBufferService = new FileBufferService();
        final ViewService viewService = new ViewService(fileBufferService);
        final FileController fileController = new FileController(fileService, fileBufferService, viewService);
        //fileController.loadFiles(args);
        Terminal.clearScreen();
        Terminal.enterRawInputMode();
        while(true){
            int b = Terminal.readByte();
            if(b != -1){
                System.out.println(b);
            }
        }

    }
}