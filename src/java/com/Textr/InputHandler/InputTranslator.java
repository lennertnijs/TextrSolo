package com.Textr.InputHandler;

import com.Textr.Terminal.TerminalService;

import java.util.InputMismatchException;

import static com.Textr.InputHandler.Inputs.*;

public class InputTranslator {

    private InputTranslator(){
    }

    public static Input translateBytes(int b){
        switch(b){
            case ENTER:
                return Input.ENTER;
            case CTRL_N:
                return Input.CTRL_N;
            case CTRL_P:
                return Input.CTRL_P;
            case SPACE:
                return Input.SPACE;
            case BACKSPACE:
                return Input.BACKSPACE;
            case ESCAPE:
                return translateEscapeByteCode();
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    private static Input translateEscapeByteCode(){
        int b = TerminalService.readByte();
        switch(b){
            case '[':
                return translateBracketByteCode();
            case 'O':
                return translateOByteCode();
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    private static Input translateBracketByteCode(){
        int b = TerminalService.readByte();
        switch(b){
            case ARROW_UP -> {
                return Input.ARROW_UP;
            }
            case ARROW_RIGHT -> {
                return Input.ARROW_RIGHT;
            }
            case ARROW_DOWN -> {
                return Input.ARROW_DOWN;
            }
            case ARROW_LEFT -> {
                return Input.ARROW_LEFT;
            }
            case '3' -> {
                return translate3ByteCode();
            }
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    private static Input translate3ByteCode(){
        int b = TerminalService.readByte();
        if(b == DELETE){
            return Input.DELETE;
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    private static Input translateOByteCode(){
        int b = TerminalService.readByte();
        if(b == 'S'){
            return Input.F4;
        }
        throw new InputMismatchException("Could not translate the input.");
    }
}
