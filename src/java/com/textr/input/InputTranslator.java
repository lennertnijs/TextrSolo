package com.textr.input;

import com.textr.terminal.ITerminalService;

/**
 * Used to translate a byte or stream of bytes into single input.
 */
public final class InputTranslator {

    private final ITerminalService terminal;

    /**
     * Private constructor. No use.
     */
    public InputTranslator(ITerminalService terminal){
        this.terminal = terminal;
    }

    /**
     * Reads bytes from the {@link ITerminalService} and returns an {@link Input} instance.
     *
     * @return the {@link Input} instance representing the inputted command
     */
    public Input getNextInput() {
        int b = terminal.readByte();
        return translateBytes(b);
    }

    /**
     * Translates the byte or bytes from the input into a single {@link Input}.
     * @param b The first byte.
     *
     * @return The {@link Input} enum representing an input.
     */
    public Input translateBytes(int b){
        if(b >= 32 && b <= 126){
            return Input.createCharacterInput((char) b);
        }
        switch (b) {
            case 7 -> {
                return Input.createSpecialInput(InputType.CTRL_G);
            }
            case 13 -> {
                return Input.createSpecialInput(InputType.ENTER);
            }
            case 14 -> {
                return Input.createSpecialInput(InputType.CTRL_N);
            }
            case 16 -> {
                return Input.createSpecialInput(InputType.CTRL_P);
            }
            case 18 -> {
                return Input.createSpecialInput(InputType.CTRL_R);
            }
            case 19 -> {
                return Input.createSpecialInput(InputType.CTRL_S);
            }
            case 20 -> {
                return Input.createSpecialInput(InputType.CTRL_T);
            }
            case 27 -> {
                return translateEscapeByteCode();
            }
            case 127 -> {
                return Input.createSpecialInput(InputType.BACKSPACE);
            }
            case -2 -> {
                return Input.createSpecialInput(InputType.TICK);
            }
        }
        return Input.createSpecialInput(InputType.NOT_MAPPED);
    }

    /**
     * Translates the second byte of an escape sequence. (The first character was ESCAPE).
     *
     * @return The translated {@link Input}
     */
    private Input translateEscapeByteCode(){
        int b = terminal.readByte();
        switch (b) {
            case '[' -> {
                return translateBracketByteCode();
            }
            case 'O' -> {
                return translateOByteCode();
            }
        }
        return Input.createSpecialInput(InputType.NOT_MAPPED);
    }

    /**
     * Translates the third byte of an escape sequence, if the second was the right bracket. ([)
     *
     * @return The translated {@link Input}.
     */
    private Input translateBracketByteCode(){
        int b = terminal.readByte();
        switch(b){
            case 'A' -> {
                return Input.createSpecialInput(InputType.ARROW_UP);
            }
            case 'C' -> {
                return Input.createSpecialInput(InputType.ARROW_RIGHT);
            }
            case 'B' -> {
                return Input.createSpecialInput(InputType.ARROW_DOWN);
            }
            case 'D' -> {
                return Input.createSpecialInput(InputType.ARROW_LEFT);
            }
            case '3' -> {
                return translate3ByteCode();
            }
        }
        return Input.createSpecialInput(InputType.NOT_MAPPED);
    }


    /**
     * Translates the third byte of an escape sequence, if the second was the capital O.
     *
     * @return The translated {@link Input}.
     */
    private Input translateOByteCode(){
        int b = terminal.readByte();
        if(b == 'S'){
            return Input.createSpecialInput(InputType.F4);
        }
        return Input.createSpecialInput(InputType.NOT_MAPPED);
    }

    /**
     * Translates the fourth byte of an escape sequence, if the third was the number 3.
     *
     * @return The translated {@link Input}.
     */
    private Input translate3ByteCode(){
        int b = terminal.readByte();
        if(b == '~'){
            return Input.createSpecialInput(InputType.DELETE);
        }
        return Input.createSpecialInput(InputType.NOT_MAPPED);
    }
}
