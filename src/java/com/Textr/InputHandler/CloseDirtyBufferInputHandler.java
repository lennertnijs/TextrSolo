package com.Textr.InputHandler;

import com.Textr.Init.InputHandlerRepo;
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
                viewService.drawAllViews();
            }
            case 'N' -> {
                InputHandlerRepo.setStandardInputHandler();
                viewService.drawAllViews();
            }
        }
    }
}
