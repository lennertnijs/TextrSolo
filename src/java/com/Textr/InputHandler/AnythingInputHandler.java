package com.Textr.InputHandler;

import com.Textr.Main;
import com.Textr.Settings;
import com.Textr.Terminal.TerminalService;

public final class AnythingInputHandler implements InputHandler{


    public AnythingInputHandler(){
    }

    @Override
    public void handleInput() {
        int b = TerminalService.readByte();
        Settings.inputHandler = Main.inputHandler;
    }
}
