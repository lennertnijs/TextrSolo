package com.textr.terminal;

import com.textr.util.Dimension2D;
import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class TermiosTerminalService implements TerminalService {

    /**
     * Constructs a new TerminalService instance.
     */
    public TermiosTerminalService() {
    }

    /**
     * Reads the size of the terminal and returns it.
     * @return The dimensions of the terminal.
     */
    public Dimension2D getTerminalArea(){
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
            return new Dimension2D(width, height);
        }catch(IOException e){
            throw new IllegalStateException("Something went wrong while reading the terminal dimensions.");
        }
    }

    /**
     * Puts the terminal in the raw input mode. (input is immediately reported)
     */
    public void enterRawInputMode(){ Terminal.enterRawInputMode();}

    /**
     * Puts the terminal in the non-raw input mode. (input is reported when enter is pressed)
     */
    public void leaveRawInputMode(){
        Terminal.leaveRawInputMode();
    }

    /**
     * Moves the cursor to the given row and column indices (0-based).
     * Translates these 0-based indices into 1-based indices for terminal use.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     */
    public void moveCursor(int x, int y){
        Terminal.moveCursor(y + 1, x + 1);
    }

    /**
     * Clears the terminal.
     */
    public void clearScreen(){
        Terminal.clearScreen();
    }

    /**
     * Prints the given text starting at the given position in the terminal.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     * @param text The text. Cannot be null.
     *
     * @throws IllegalArgumentException If text is null.
     */
    public void printText(int x, int y, String text){
        Terminal.printText(y + 1, x + 1 , text);
    }

    /**
     * Reads a byte from the stream and returns it.
     *
     * @return The byte.
     */
    public int readByte(){
        try{
            return Terminal.readByte(System.currentTimeMillis()+10);
        }catch(IOException e){
            return -1;
        } catch (TimeoutException e) {
            return -2;
        }
    }
}
