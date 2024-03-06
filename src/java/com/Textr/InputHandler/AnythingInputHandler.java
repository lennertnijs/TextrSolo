package com.Textr.InputHandler;

import com.Textr.Init.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;

public final class AnythingInputHandler implements IInputHandler {


    public AnythingInputHandler(){
    }

    @Override
    public void handleInput() {
        TerminalService.readByte();
        InputHandlerRepo.setStandardInputHandler();
    }
}
