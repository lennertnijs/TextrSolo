package com.textr.input;

import com.textr.view.ViewService;

public final class InputHandler implements IInputHandler {
    private final ViewService viewService;
    private final InputTranslator translator;

    public InputHandler(ViewService viewService, InputTranslator translator){
        this.viewService = viewService;
        this.translator = translator;
    }

    @Override
    public void handleInput(){
        Input input = translator.getNextInput();
        InputType inputType = input.getType();
        viewService.handleInput(input);
    }

}
