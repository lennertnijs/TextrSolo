package com.textr.input;

import com.textr.terminal.TerminalService;

public final class AnythingInputHandler implements IInputHandler {


    public AnythingInputHandler(){
    }

    @Override
    public void handleInput() {
        TerminalService.readByte();
        InputHandlerRepo.setStandardInputHandler();
    }
}
