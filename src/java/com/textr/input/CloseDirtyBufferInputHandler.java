package com.textr.input;

import com.textr.terminal.TerminalService;
import com.textr.view.ViewService;

public final class CloseDirtyBufferInputHandler implements IInputHandler {

    private final ViewService viewService;
    private final TerminalService terminal;
    public CloseDirtyBufferInputHandler(ViewService viewService, TerminalService terminal){
        this.viewService = viewService;
        this.terminal = terminal;
    }
    @Override
    public void handleInput() {
        int b = terminal.readByte();
        switch (b) {
            case 'Y' -> {
                viewService.deleteView();
                InputHandlerRepo.setStandardInputHandler();
                viewService.drawAll();
            }
            case 'N' -> {
                InputHandlerRepo.setStandardInputHandler();
                viewService.drawAll();
            }
        }
    }
}
