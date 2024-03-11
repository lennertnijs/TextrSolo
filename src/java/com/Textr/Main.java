package com.Textr;

import com.Textr.Input.InputHandlerRepo;
import com.Textr.View.ViewTreeRepo;
import com.Textr.View.ViewService;

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