package com.textr.input;

import com.textr.terminal.TerminalService;

/**
 * Used to translate a byte or stream of bytes into single input.
 */
public final class InputTranslator {

    /**
     * Private constructor. No use.
     */
    private InputTranslator(){
    }

    /**
     * Translates the byte or bytes from the input into a single {@link Input}.
     * @param b The first byte.
     *
     * @return The {@link Input} enum representing an input.
     */
    public static Input translateBytes(int b){
        if(b >= 32 && b <= 126){
            return Input.REGULAR_INPUT;
        }
        switch (b) {
            case 13 -> {
                return Input.ENTER;
            }
            case 14 -> {
                return Input.CTRL_N;
            }
            case 16 -> {
                return Input.CTRL_P;
            }
            case 18 -> {
                return Input.CTRL_R;
            }
            case 19 -> {
                return Input.CTRL_S;
            }
            case 20 -> {
                return Input.CTRL_T;
            }
            case 27 -> {
                return translateEscapeByteCode();
            }
            case 127 -> {
                return Input.BACKSPACE;
            }
        }
        return Input.NOT_MAPPED;
    }

    /**
     * Translates the second byte of an escape sequence. (The first character was ESCAPE).
     *
     * @return The translated {@link Input}
     */
    private static Input translateEscapeByteCode(){
        int b = TerminalService.readByte();
        switch (b) {
            case '[' -> {
                return translateBracketByteCode();
            }
            case 'O' -> {
                return translateOByteCode();
            }
        }
        return Input.NOT_MAPPED;
    }

    /**
     * Translates the third byte of an escape sequence, if the second was the right bracket. ([)
     *
     * @return The translated {@link Input}.
     */
    private static Input translateBracketByteCode(){
        int b = TerminalService.readByte();
        switch(b){
            case 'A' -> {
                return Input.ARROW_UP;
            }
            case 'C' -> {
                return Input.ARROW_RIGHT;
            }
            case 'B' -> {
                return Input.ARROW_DOWN;
            }
            case 'D' -> {
                return Input.ARROW_LEFT;
            }
            case '3' -> {
                return translate3ByteCode();
            }
        }
        return Input.NOT_MAPPED;
    }


    /**
     * Translates the third byte of an escape sequence, if the second was the capital O.
     *
     * @return The translated {@link Input}.
     */
    private static Input translateOByteCode(){
        int b = TerminalService.readByte();
        if(b == 'S'){
            return Input.F4;
        }
        return Input.NOT_MAPPED;
    }

    /**
     * Translates the fourth byte of an escape sequence, if the third was the number 3.
     *
     * @return The translated {@link Input}.
     */
    private static Input translate3ByteCode(){
        int b = TerminalService.readByte();
        if(b == '~'){
            return Input.DELETE;
        }
        return Input.NOT_MAPPED;
    }
}
