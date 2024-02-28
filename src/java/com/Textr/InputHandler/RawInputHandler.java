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
        switch (input) {
            case ESCAPE -> {
                int b = TerminalService.readByte();
                switch (b) {
                    case '[':
                        b = TerminalService.readByte();
                        switch(b){
                            case ARROW_RIGHT:
                                fileBufferService.moveInsertionPointRight();
                                break;
                            case ARROW_LEFT:
                                fileBufferService.moveInsertionPointLeft();
                                break;
                            case ARROW_DOWN:
                                fileBufferService.moveInsertionPointDown();
                                break;
                            case ARROW_UP:
                                fileBufferService.moveInsertionPointUp();
                                break;
                        }
                }
            }
            case CTRL_P -> fileBufferService.moveActiveBufferToPrev();
            case CTRL_N -> fileBufferService.moveActiveBufferToNext();
        }
        viewService.drawAllViewsVertical();
        viewService.drawCursor();
    }
}
