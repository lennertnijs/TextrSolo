package com.textr;

import com.textr.drawer.ViewDrawer;
import com.textr.input.InputHandler;
import com.textr.input.InputTranslator;
import com.textr.terminal.Communicator;
import com.textr.terminal.TerminalCommunicator;
import com.textr.terminal.TermiosTerminalService;
import com.textr.view.ViewTreeRepo;
import com.textr.view.ViewService;

public class Main {

    public static void main(String[] args){
        final ViewTreeRepo viewRepo = new ViewTreeRepo();
        final TermiosTerminalService terminal = new TermiosTerminalService();
        final ViewDrawer viewDrawer = new ViewDrawer(terminal);
        final Communicator communicator = new TerminalCommunicator(terminal);
        final ViewService viewService = new ViewService(viewRepo, viewDrawer, terminal, communicator);
        final InputHandler handler = new InputHandler(viewService, new InputTranslator(terminal));

        Initialiser.initialise(viewService, args, terminal);
        while(Settings.RUNNING){
            handler.handleInput();
        }
        terminal.leaveRawInputMode();
        terminal.clearScreen();
        terminal.moveCursor(0, 0);
    }
}