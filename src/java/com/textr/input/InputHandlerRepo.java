package com.textr.input;

import com.textr.terminal.TermiosTerminalService;
import com.textr.util.Validator;
import com.textr.view.ViewService;

public final class InputHandlerRepo {

    private static AnythingInputHandler anythingInputHandler;
    private static CloseDirtyBufferInputHandler closeDirtyBufferInputHandler;
    private static InputHandler inputHandler;
    private static IInputHandler activeInputHandler;

    private InputHandlerRepo(){
    }

    public static void initialiseInputHandlers(ViewService viewService){
        Validator.notNull(viewService, "Cannot create input handlers with a null view service.");
        // FIXME: Creating TerminalServices and InputTranslators shouldn't happen here
        anythingInputHandler = new AnythingInputHandler(new TermiosTerminalService());
        closeDirtyBufferInputHandler = new CloseDirtyBufferInputHandler(viewService, new TermiosTerminalService());
        inputHandler = new InputHandler(viewService, new InputTranslator(new TermiosTerminalService()));
        setStandardInputHandler();
    }

    public static void handleInput(){
        activeInputHandler.handleInput();
    }

    public static void setCloseDirtyBufferInputHandler(){
        activeInputHandler = closeDirtyBufferInputHandler;
    }

    public static void setAnythingInputHandler(){
        activeInputHandler = anythingInputHandler;
    }

    public static void setStandardInputHandler(){
        activeInputHandler = inputHandler;
    }
}
