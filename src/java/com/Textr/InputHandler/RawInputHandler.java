package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import static com.Textr.Inputs.*;

public class RawInputHandler implements InputHandler{
    private final ViewService viewService;
    private final FileBufferService fileBufferService;
    boolean f = true;

    public RawInputHandler(ViewService viewService, FileBufferService fileBufferService){
        this.viewService = viewService;
        this.fileBufferService = fileBufferService;
    }
    @Override
    public void handleInput(){
        int input = TerminalService.readByte();
        boolean isRegularInput = input >= 65 && input <= 122 || input == SPACE || input == BACKSPACE;
        if(isRegularInput){
            fileBufferService.getActiveBuffer().addCharacterToBufferText((char) input);
            fileBufferService.moveInsertionPointRight();
            return;
        }
        switch (input) {
            case ESCAPE -> handleEscapeInput();
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
        }
        viewService.drawAllViewsVertical();
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
