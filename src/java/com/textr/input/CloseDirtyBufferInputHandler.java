package com.textr.input;

import com.textr.terminal.TerminalService;
import com.textr.view.ViewService;

public final class CloseDirtyBufferInputHandler implements IInputHandler {

    private final ViewService viewService;
    public CloseDirtyBufferInputHandler(ViewService viewService){
        this.viewService = viewService;
    }
    @Override
    public void handleInput() {
        int b = TerminalService.readByte();
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
