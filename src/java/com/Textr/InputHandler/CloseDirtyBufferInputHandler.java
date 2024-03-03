package com.Textr.InputHandler;

import com.Textr.Terminal.TerminalService;

public class CloseDirtyBufferInputHandler implements InputHandler{

    public CloseDirtyBufferInputHandler(){

    }
    @Override
    public void handleInput() {
        int b = TerminalService.readByte();
        switch(b){
            // handle Y, N and do nothing for the others.
        }
    }
}
