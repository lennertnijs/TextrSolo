package com.Textr.InputUtil;

import com.Textr.Terminal.TerminalService;

import java.util.InputMismatchException;

/**
 * Used to translate a byte or stream of bytes into single input.
 */
public class InputTranslator {

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
     * @throws InputMismatchException If no translation was found for the given input byte(s).
     */
    public static Input translateBytes(int b){
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
            case CTRL_T -> {
                return Input.CTRL_T;
            }
            case 18 -> {
                return Input.CTRL_R;
            }
            case 32 -> {
                return Input.SPACE;
            }
            case 127 -> {
                return Input.BACKSPACE;
            }
            case 27 -> {
                return translateEscapeByteCode();
            }
            case 19 -> {
                return Input.CTRL_S;
            }
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    /**
     * Translates the second byte of an escape sequence. (The first character was ESCAPE).
     *
     * @return The translated {@link Input}
     * @throws InputMismatchException If no translation was found for the given second input byte.
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
        throw new InputMismatchException("Could not translate the input.");
    }

    /**
     * Translates the third byte of an escape sequence, if the second was the right bracket. ([)
     *
     * @return The translated {@link Input}.
     * @throws InputMismatchException If no translation was found for the given third input byte.
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
        throw new InputMismatchException("Could not translate the input.");
    }


    /**
     * Translates the third byte of an escape sequence, if the second was the capital O.
     *
     * @return The translated {@link Input}.
     * @throws InputMismatchException If no translation was found for the given third input byte.
     */
    private static Input translateOByteCode(){
        int b = TerminalService.readByte();
        if(b == 'S'){
            return Input.F4;
        }
        throw new InputMismatchException("Could not translate the input.");
    }

    /**
     * Translates the fourth byte of an escape sequence, if the third was the number 3.
     *
     * @return The translated {@link Input}.
     * @throws InputMismatchException If no translation was found for the given fourth input byte.
     */
    private static Input translate3ByteCode(){
        int b = TerminalService.readByte();
        if(b == '~'){
            return Input.DELETE;
        }
        throw new InputMismatchException("Could not translate the input.");
    }
}
