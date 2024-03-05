package com.Textr;

import com.Textr.File.FileRepo;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.File.FileService;
import com.Textr.FileBufferRepo.FileBufferRepo;
import com.Textr.Init.Initialiser;
import com.Textr.Init.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.Tree.ViewTreeRepo;
import com.Textr.View.ViewService;
import com.Textr.ViewRepo.ViewRepo;

public class Main {

    public static void main(String[] args){
        final FileRepo fileRepo = new FileRepo();
        final FileService fileService = new FileService(fileRepo);

        final FileBufferRepo fileBufferRepo = new FileBufferRepo();
        final FileBufferService fileBufferService = new FileBufferService(fileService, fileBufferRepo);

        final ViewTreeRepo viewRepo = new ViewTreeRepo();
        final ViewService viewService = new ViewService(fileBufferService, viewRepo);

        Initialiser.initialise(fileService, fileBufferService, viewService, args);
        while(true){
            InputHandlerRepo.handleInput();
        }
    }
}