package com.Textr;

import com.Textr.File.FileRepo;
import com.Textr.File.FileService;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Tree.ViewTreeRepo;
import com.Textr.View.ViewService;

public class Main {

    public static void main(String[] args){
        final FileRepo fileRepo = new FileRepo();
        final FileService fileService = new FileService(fileRepo);

        final ViewTreeRepo viewRepo = new ViewTreeRepo();
        final ViewService viewService = new ViewService(fileService, viewRepo);

        Initialiser.initialise(fileService, viewService, args);
        while(true){
            InputHandlerRepo.handleInput();
        }
    }
}