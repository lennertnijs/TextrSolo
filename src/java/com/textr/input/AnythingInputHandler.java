package com.textr.input;

import com.textr.terminal.TerminalService;

public final class AnythingInputHandler implements IInputHandler {

    private final TerminalService terminal;

    public AnythingInputHandler(TerminalService terminal){
        // TODO: Replace this class with a MessageSender object
        this.terminal = terminal;
    }

    @Override
    public void handleInput() {
        terminal.readByte();
        InputHandlerRepo.setStandardInputHandler();
    }
}
