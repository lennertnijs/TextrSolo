package com.Textr.InputHandler;

import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.Position;
import com.Textr.View.ViewService;

import static com.Textr.Inputs.*;
import static com.Textr.Inputs.CTRL_N;

public class RawInputHandler implements InputHandler{
    private final ViewService viewService;
    private final FileBufferService fileBufferService;

    public RawInputHandler(ViewService viewService, FileBufferService fileBufferService){
        this.viewService = viewService;
        this.fileBufferService = fileBufferService;
    }
    @Override
    public void handleInput(int input){
        if(input == -1){
            // exception occurred during reading of the byte
            return;
        }
        TerminalService.clearScreen();
        switch(input){
            case ARROW_RIGHT:
                break;
            case CTRL_S:
                break;
            case CTRL_P:
                fileBufferService.moveActiveBufferToPrev();
                break;
            case CTRL_N:
                fileBufferService.moveActiveBufferToNext();
                break;
        }
        viewService.drawAllViewsVertical();
        Position position = Position.builder().x(5).y(5).build();
        TerminalService.printText(position, input + "");
    }
}
