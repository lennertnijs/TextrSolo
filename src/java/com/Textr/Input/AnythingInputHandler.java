package com.Textr.Input;

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
