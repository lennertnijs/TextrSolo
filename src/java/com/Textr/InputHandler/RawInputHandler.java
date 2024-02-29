package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
import com.Textr.Terminal.TerminalService;
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
        boolean isRegularInput = input >= 65 && input <= 122 || input == SPACE || input == BACKSPACE;
        if(isRegularInput){
            saveInputToBuffer(input);
            drawAll();
            return;
        }
        switch (input) {
            case ESCAPE -> handleEscapeInput();
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
        }
        drawAll();
    }

    private void saveInputToBuffer(int input){
        Point point = fileBufferService.getActiveBuffer().getInsertionPosition();
        fileBufferService.getActiveBuffer().getBufferText().addCharacter((char) input, point.getY(), point.getX());
        fileBufferService.moveInsertionPointRight();
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
                case ARROW_RIGHT -> fileBufferService.moveInsertionPointRight();
                case ARROW_LEFT -> fileBufferService.moveInsertionPointLeft();
                case ARROW_DOWN -> fileBufferService.moveInsertionPointDown();
                case ARROW_UP -> fileBufferService.moveInsertionPointUp();
            }
        }
    }
}
