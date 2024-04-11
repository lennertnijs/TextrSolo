package com.textr.input;

import com.textr.util.Direction;
import com.textr.view.ViewService;

import java.util.Objects;

public final class InputHandler implements IInputHandler {
    private final ViewService viewService;

    public InputHandler(ViewService viewService){
        this.viewService = viewService;
    }

    @Override
    public void handleInput(){
        Input input = InputTranslator.getNextInput();
        InputType inputType = input.getType();
        if (Objects.requireNonNull(inputType) == InputType.F4) {
            viewService.attemptDeleteView();
        } else {
            viewService.handleInput(input);
        }
    }
}
