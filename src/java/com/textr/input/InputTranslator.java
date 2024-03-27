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
     * Translates the byte or bytes from the input into a single {@link InputType}.
     * @param b The first byte.
     *
     * @return The {@link InputType} enum representing an input.
     */
    public static InputType translateBytes(int b){
        if(b >= 32 && b <= 126){
            return InputType.CHARACTER;
        }
        switch (b) {
            case 13 -> {
                return InputType.ENTER;
            }
            case 14 -> {
                return InputType.CTRL_N;
            }
            case 16 -> {
                return InputType.CTRL_P;
            }
            case 18 -> {
                return InputType.CTRL_R;
            }
            case 19 -> {
                return InputType.CTRL_S;
            }
            case 20 -> {
                return InputType.CTRL_T;
            }
            case 27 -> {
                return translateEscapeByteCode();
            }
            case 127 -> {
                return InputType.BACKSPACE;
            }
        }
        return InputType.NOT_MAPPED;
    }

    /**
     * Translates the second byte of an escape sequence. (The first character was ESCAPE).
     *
     * @return The translated {@link InputType}
     */
    private static InputType translateEscapeByteCode(){
        int b = TerminalService.readByte();
        switch (b) {
            case '[' -> {
                return translateBracketByteCode();
            }
            case 'O' -> {
                return translateOByteCode();
            }
        }
        return InputType.NOT_MAPPED;
    }

    /**
     * Translates the third byte of an escape sequence, if the second was the right bracket. ([)
     *
     * @return The translated {@link InputType}.
     */
    private static InputType translateBracketByteCode(){
        int b = TerminalService.readByte();
        switch(b){
            case 'A' -> {
                return InputType.ARROW_UP;
            }
            case 'C' -> {
                return InputType.ARROW_RIGHT;
            }
            case 'B' -> {
                return InputType.ARROW_DOWN;
            }
            case 'D' -> {
                return InputType.ARROW_LEFT;
            }
            case '3' -> {
                return translate3ByteCode();
            }
        }
        return InputType.NOT_MAPPED;
    }


    /**
     * Translates the third byte of an escape sequence, if the second was the capital O.
     *
     * @return The translated {@link InputType}.
     */
    private static InputType translateOByteCode(){
        int b = TerminalService.readByte();
        if(b == 'S'){
            return InputType.F4;
        }
        return InputType.NOT_MAPPED;
    }

    /**
     * Translates the fourth byte of an escape sequence, if the third was the number 3.
     *
     * @return The translated {@link InputType}.
     */
    private static InputType translate3ByteCode(){
        int b = TerminalService.readByte();
        if(b == '~'){
            return InputType.DELETE;
        }
        return InputType.NOT_MAPPED;
    }
}
