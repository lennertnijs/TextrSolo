package com.textr;

import com.textr.drawer.ViewDrawer;
import com.textr.input.InputHandlerRepo;
import com.textr.terminal.TerminalService;
import com.textr.view.ViewTreeRepo;
import com.textr.view.ViewService;

public class Main {

    public static void main(String[] args){
        final ViewTreeRepo viewRepo = new ViewTreeRepo();
        final TerminalService terminal = new TerminalService();
        final ViewDrawer viewDrawer = new ViewDrawer(terminal);
        final ViewService viewService = new ViewService(viewRepo, viewDrawer, terminal);

        Initialiser.initialise(viewService, args, terminal);
        while(Settings.RUNNING){
            InputHandlerRepo.handleInput();
        }
    }
}