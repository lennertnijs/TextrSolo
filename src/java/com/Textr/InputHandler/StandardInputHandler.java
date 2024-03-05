package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.InputUtil.Input;
import com.Textr.InputUtil.InputTranslator;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.Direction;
import com.Textr.View.ViewService;
import io.github.btj.termios.Terminal;

import static com.Textr.InputUtil.Inputs.*;

public final class StandardInputHandler implements IInputHandler {
    private final ViewService viewService;
    private final FileBufferService fileBufferService;

    public StandardInputHandler(ViewService viewService, FileBufferService fileBufferService){
        this.viewService = viewService;
        this.fileBufferService = fileBufferService;
    }
    @Override
    public void handleInput(){
        int b = TerminalService.readByte();
        TerminalService.clearScreen();
        boolean isRegularInput = b >= 65 && b <= 122 || b == SPACE;
        boolean draw = true;
        if(isRegularInput){
            viewService.insertCharacter((char) b);
        }else{
            draw = handleSpecialInput(b);
        }
        if(draw){
            drawAll();
        }
    }

    private boolean handleSpecialInput(int b){
        Input input = InputTranslator.translateBytes(b);
        switch(input){
            case F4 -> {
                if(!viewService.attemptDeleteView()){
                    Terminal.clearScreen();
                    TerminalService.printText(1, 1, "The buffer is dirty. Are you sure you want to delete it?");
                    return false;
                }
            }
            case ENTER -> viewService.createNewline();
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
            case CTRL_S -> fileBufferService.saveActiveBuffer();
            case CTRL_R -> viewService.rotateClockWise();
            case ARROW_UP -> viewService.moveCursor(Direction.UP);
            case ARROW_RIGHT -> viewService.moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> viewService.moveCursor(Direction.DOWN);
            case ARROW_LEFT -> viewService.moveCursor(Direction.LEFT);
            case DELETE -> viewService.deleteNextChar();
            case BACKSPACE -> viewService.deletePrevChar();

        }
        return true;
    }
    private void drawAll(){
        viewService.drawAllViews();
        viewService.drawCursor();
    }
}
