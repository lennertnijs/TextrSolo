package com.Textr.Terminal;

import com.Textr.Util.Point;
import com.Textr.Util.Validator;
import com.Textr.View.Dimension2D;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public final class TerminalService {

    /**
     * Private constructor. No use.
     */
    private TerminalService(){
    }

    /**
     * Reads the size of the terminal and returns it.
     * @return The dimensions of the terminal.
     */
    public static Dimension2D getTerminalArea(){
        Terminal.reportTextAreaSize();
        try{
            int b = Terminal.readByte();
            while (b != ';'){
                b = Terminal.readByte();
            }
            b = Terminal.readByte();
            int height = 0;
            while(b != ';'){
                if ('0' <= b && b <= '9') {
                    height *= 10;
                    height += b - '0';
                }
                b = Terminal.readByte();
            }

            int width = 0;
            while(b != 't'){
                b = Terminal.readByte();
                if ('0' <= b && b <= '9') {
                    width *= 10;
                    width += b - '0';
                }
            }
            return Dimension2D.create(width, height);
        }catch(IOException e){
            throw new IllegalStateException("Something went wrong while reading the terminal dimensions.");
        }
    }

    /**
     * Puts the terminal in the raw input mode. (input is immediately reported)
     */
    public static void enterRawInputMode(){
        Terminal.enterRawInputMode();
    }

    /**
     * Puts the terminal in the non-raw input mode. (input is reported when enter is pressed)
     */
    public static void leaveRawInputMode(){
        Terminal.leaveRawInputMode();
    }

    /**
     * Moves the cursor to the given row and column indices (0-based).
     * Translates these 0-based indices into 1-based indices for terminal use.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     */
    public static void moveCursor(int x, int y){
        Terminal.moveCursor(y + 1, x + 1, "kek");
    }

    /**
     * Clears the terminal.
     */
    public static void clearScreen(){
        Terminal.clearScreen();
    }

    /**
     * Prints the given text starting at the given position in the terminal.
     * @param point The position (x, y), 0-based.
     * @param text The text.
     *
     * @throws IllegalArgumentException If either parameters are null.
     */
    public static void printText(Point point, String text){
        Validator.notNull(point, "Cannot print to a null point in the terminal.");
        Validator.notNull(text, "Cannot print a null text to the terminal.");
        Terminal.printText(point.getY() + 1, point.getX() + 1, text);
    }

    /**
     * Prints the given text starting at the given position in the terminal.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     * @param text The text. Cannot be null.
     *
     * @throws IllegalArgumentException If text is null.
     */
    public static void printText(int x, int y, String text){
        Terminal.printText(y + 1, x + 1 , text);
    }

    /**
     * Reads a byte from the stream and returns it.
     *
     * @return The byte.
     */
    public static int readByte(){
        try{
            return Terminal.readByte();
        }catch(IOException e){
            return -1;
        }
    }
}
