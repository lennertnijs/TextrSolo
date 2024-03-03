package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.Direction;
import com.Textr.View.ViewService;

import static com.Textr.InputHandler.Inputs.*;

public final class RawInputHandler implements InputHandler{
    private final ViewService viewService;
    private final FileBufferService fileBufferService;

    public RawInputHandler(ViewService viewService, FileBufferService fileBufferService){
        this.viewService = viewService;
        this.fileBufferService = fileBufferService;
    }
    @Override
    public void handleInput(){
        int b = TerminalService.readByte();
        TerminalService.clearScreen();
        boolean isRegularInput = b >= 65 && b <= 122 || b == SPACE;
        if(isRegularInput){
            viewService.insertCharacter((char) b);
        }else{
            handleSpecialInput(b);
        }

        drawAll();
    }

    private void handleSpecialInput(int b){
        Input input = InputTranslator.translateBytes(b);
        switch(input){
            case ENTER -> viewService.createNewline();
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
            case ARROW_UP -> viewService.moveCursor(Direction.UP);
            case ARROW_RIGHT -> viewService.moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> viewService.moveCursor(Direction.DOWN);
            case ARROW_LEFT -> viewService.moveCursor(Direction.LEFT);
            case DELETE -> viewService.deleteNextChar();
            case BACKSPACE -> viewService.deletePrevChar();
        }
    }
    private void drawAll(){
        viewService.drawAllViews();
        viewService.drawCursor();
    }
}
