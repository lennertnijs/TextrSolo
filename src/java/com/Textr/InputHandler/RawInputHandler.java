package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
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
            saveInputToBuffer(input);
            drawAll();
            return;
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
    //

    private void saveInputToBuffer(int input){
        Point point = fileBufferService.getActiveBuffer().getInsertionPosition();
        fileBufferService.getActiveBuffer().getBufferText().addCharacter((char) input, point.getY(), point.getX());
        viewService.moveInsertionPoint(Direction.RIGHT);
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
                case ARROW_RIGHT -> viewService.moveInsertionPoint(Direction.RIGHT);
                case ARROW_LEFT -> viewService.moveInsertionPoint(Direction.LEFT);
                case ARROW_DOWN -> viewService.moveInsertionPoint(Direction.DOWN);
                case ARROW_UP -> viewService.moveInsertionPoint(Direction.UP);
            }
        }
    }
}
