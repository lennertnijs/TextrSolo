package com.textr.input;

import com.textr.view.ViewService;

public final class InputHandler implements IInputHandler {
    private final ViewService viewService;

    public InputHandler(ViewService viewService){
        this.viewService = viewService;
    }

    @Override
    public void handleInput(){
        Input input = InputTranslator.getNextInput();
        viewService.handleInput(input);
    }

}
