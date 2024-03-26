package com.textr;

import com.textr.input.InputHandlerRepo;
import com.textr.view.ViewTreeRepo;
import com.textr.view.ViewService;

public class Main {

    public static void main(String[] args){
        final ViewTreeRepo viewRepo = new ViewTreeRepo();
        final ViewService viewService = new ViewService(viewRepo);

        Initialiser.initialise(viewService, args);
        while(Settings.RUNNING){
            InputHandlerRepo.handleInput();
        }
    }
}