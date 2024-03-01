package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.Direction;
import com.Textr.View.ViewService;

import static com.Textr.Inputs.*;

public final class RawInputHandler implements InputHandler{
    private final ViewService viewService;
    private final FileBufferService fileBufferService;

    public RawInputHandler(ViewService viewService, FileBufferService fileBufferService){
        this.viewService = viewService;
        this.fileBufferService = fileBufferService;
    }
    @Override
    public void handleInput(){
        int input = TerminalService.readByte();
        TerminalService.clearScreen();
        boolean isRegularInput = input >= 65 && input <= 122 || input == SPACE;
        if(isRegularInput){
            viewService.insertCharacter((char) input);
        }
        switch (input) {
            case BACKSPACE -> viewService.deleteChar();
            case ENTER -> viewService.createNewline();
            case ESCAPE -> handleEscapeInput();
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
        }
        drawAll();
    }

    private void drawAll(){
        viewService.drawAllViews();
        viewService.drawCursor();
    }

    private void handleEscapeInput(){
        int b = TerminalService.readByte();
        if (b == '[') {
            b = TerminalService.readByte();
            switch (b) {
                case ARROW_RIGHT -> viewService.moveCursor(Direction.RIGHT);
                case ARROW_LEFT -> viewService.moveCursor(Direction.LEFT);
                case ARROW_DOWN -> viewService.moveCursor(Direction.DOWN);
                case ARROW_UP -> viewService.moveCursor(Direction.UP);
            }
        }
    }
}
