package com.Textr;

import com.Textr.File.FileRepo;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBufferRepo;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.View.ViewService;
import com.Textr.ViewRepo.ViewRepo2;

public class Main {

    public static void main(String[] args){
        final FileRepo fileRepo = new FileRepo();
        final FileService fileService = new FileService(fileRepo);

        final FileBufferRepo fileBufferRepo = new FileBufferRepo();
        final FileBufferService fileBufferService = new FileBufferService(fileService, fileBufferRepo);

        final ViewRepo2 viewRepo = new ViewRepo2();

        final ViewService viewService = new ViewService(fileBufferService, viewRepo);

        Initialiser.initialise(fileService, fileBufferService, viewService, args);
        while(true){
            InputHandlerRepo.handleInput();
        }
    }
}