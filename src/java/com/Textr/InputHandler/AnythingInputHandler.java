package com.Textr.InputHandler;

import com.Textr.Terminal.TerminalService;

public final class AnythingInputHandler implements InputHandler{


    public AnythingInputHandler(){

    }

    @Override
    public void handleInput() {
        int b = TerminalService.readByte();
        // swap back to old input handler
    }
}
