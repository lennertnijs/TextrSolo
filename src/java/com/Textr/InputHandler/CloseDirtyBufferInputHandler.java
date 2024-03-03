package com.Textr.InputHandler;

import com.Textr.Main;
import com.Textr.Settings;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

public class CloseDirtyBufferInputHandler implements InputHandler{

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
                Settings.inputHandler = Main.inputHandler;
                viewService.drawAllViews();
            }
            case 'N' -> {
                Settings.inputHandler = Main.inputHandler;
                viewService.drawAllViews();
            }
        }
    }
}
