package com.Textr.InputHandler;

import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import static com.Textr.Inputs.*;

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
            case ESCAPE:
                TerminalService.printText(5, 5, "TEXT");
                int b = TerminalService.readByte();
                switch(b){
                    case ARROW_RIGHT:
                        TerminalService.printText(7, 7, "TET");
                        fileBufferService.moveInsertionPointRight();
                        break;
                }
                break;
            case CTRL_P:
                fileBufferService.moveActiveBufferToPrev();
                break;
            case CTRL_N:
                fileBufferService.moveActiveBufferToNext();
                break;
        }
        viewService.drawAllViewsVertical();
        viewService.drawCursor();
    }
}
