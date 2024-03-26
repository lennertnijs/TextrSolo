package com.textr.input;

import com.textr.terminal.TerminalService;
import com.textr.util.Direction;
import com.textr.view.ViewService;

public final class InputHandler implements IInputHandler {
    private final ViewService viewService;

    public InputHandler(ViewService viewService){
        this.viewService = viewService;
    }

    @Override
    public void handleInput(){
        int b = TerminalService.readByte();
        TerminalService.clearScreen();
        Input input = InputTranslator.translateBytes(b);
        switch(input){
            case REGULAR_INPUT -> viewService.insertCharacter((char) b);
            case F4 -> {
                viewService.attemptDeleteView();
                return;
            }
            case ENTER -> viewService.createNewline();
            case CTRL_P -> viewService.setActiveViewToPrevious();
            case CTRL_N -> viewService.setActiveViewToNext();
            case CTRL_S -> viewService.saveBuffer();
            case CTRL_R -> viewService.rotateView(false);
            case CTRL_T -> viewService.rotateView(true);
            case ARROW_UP -> viewService.moveCursor(Direction.UP);
            case ARROW_RIGHT -> viewService.moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> viewService.moveCursor(Direction.DOWN);
            case ARROW_LEFT -> viewService.moveCursor(Direction.LEFT);
            case DELETE -> viewService.deleteNextChar();
            case BACKSPACE -> viewService.deletePrevChar();
        }
        viewService.drawAll();
    }
}
