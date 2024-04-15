package com.textr.input;

import com.textr.terminal.ITerminalService;

public final class AnythingInputHandler implements IInputHandler {

    private final ITerminalService terminal;

    public AnythingInputHandler(ITerminalService terminal){
        // TODO: Replace this class with a MessageSender object
        this.terminal = terminal;
    }

    @Override
    public void handleInput() {
        terminal.readByte();
        InputHandlerRepo.setStandardInputHandler();
    }
}
