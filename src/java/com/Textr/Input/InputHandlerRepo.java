package com.Textr.Input;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Util.Validator;
import com.Textr.View.ViewService;

public final class InputHandlerRepo {

    private static AnythingInputHandler anythingInputHandler;
    private static CloseDirtyBufferInputHandler closeDirtyBufferInputHandler;
    private static InputHandler inputHandler;
    private static IInputHandler activeInputHandler;

    private InputHandlerRepo(){
    }

    public static void initialiseInputHandlers(FileBufferService bufferService, ViewService viewService){
        Validator.notNull(bufferService, "Cannot create input handlers with a null file buffer service.");
        Validator.notNull(viewService, "Cannot create input handlers with a null view service.");
        anythingInputHandler = new AnythingInputHandler();
        closeDirtyBufferInputHandler = new CloseDirtyBufferInputHandler(viewService);
        inputHandler = new InputHandler(viewService, bufferService);
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
