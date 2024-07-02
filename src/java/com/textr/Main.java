package com.textr;

import com.textr.service.ViewDrawer;
import com.textr.input.InputTranslator;
import com.textr.terminal.TermiosTerminalService;
import com.textr.service.LayoutGenerator;
import com.textr.view.ViewTreeRepo;
import com.textr.service.ViewService;

public class Main {

    public static void main(String[] args){
        ViewTreeRepo viewRepo = new ViewTreeRepo();
        TermiosTerminalService terminal = new TermiosTerminalService();
        ViewDrawer viewDrawer = new ViewDrawer(terminal);
        terminal.enterRawInputMode();
        ViewService viewService = new ViewService(viewRepo, viewDrawer, terminal.getTerminalArea(),
                new LayoutGenerator(viewRepo));
        InputTranslator translator = new InputTranslator(terminal);

        Initialiser.initialise(viewService, args, terminal);
        while(Settings.RUNNING){
            viewService.input(translator.getNextInput());
        }
        terminal.leaveRawInputMode();
        terminal.clearScreen();
        terminal.moveCursor(0, 0);
    }
}