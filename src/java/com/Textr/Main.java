package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.Model.File;
import com.Textr.Model.FileService;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class Main {

    protected static final FileService fileService = new FileService();
    protected static final FileController fileController = new FileController(fileService);

    public static void main(String[] args) throws IOException {
        fileController.loadFiles(args);
        while(true){
            File file = fileService.getAllFiles().get(0);
            Terminal.reportTextAreaSize();
            fileService.getBufferedReader(file).close();
            //System.out.println("true");
        }
    }
}