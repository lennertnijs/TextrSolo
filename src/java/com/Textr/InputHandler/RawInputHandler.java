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
    public void handleInput(int input){
        switch (input) {
            case ESCAPE -> {
                f = false;
                handleEscapeInput();
            }
            case CTRL_P -> {
                fileBufferService.moveActiveBufferToPrev();
                f = false;
            }
            case CTRL_N -> {
                fileBufferService.moveActiveBufferToNext();
                f = false;
            }
        }
        if(f){
            fileBufferService.getActiveBuffer().addCharacterToBufferText((char) input);
            fileBufferService.moveInsertionPointRight();
            // temporary to type lmao
            // letters are between 65 (A) and z (122)
            // space is 32
            // backspace is een combinatie according to google
        }
        f = true;
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
