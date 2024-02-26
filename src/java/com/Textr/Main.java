package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.View.ViewService;

public class Main {

    public static void main(String[] args){
        final FileService fileService = new FileService();
        final FileBufferService fileBufferService = new FileBufferService();
        final ViewService viewService = new ViewService(fileBufferService);
        final FileController fileController = new FileController(fileService, fileBufferService, viewService);
        fileController.loadFiles(args);
        while(true){
            //
        }
    }
}