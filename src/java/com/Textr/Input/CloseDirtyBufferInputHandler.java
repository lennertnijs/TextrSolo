package com.Textr.Input;

import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

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
