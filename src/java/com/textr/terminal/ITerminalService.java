package com.textr.terminal;

import com.textr.util.Dimension2D;
import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface ITerminalService {

    /**
     * Reads the size of the terminal and returns it.
     * @return The dimensions of the terminal.
     */
    Dimension2D getTerminalArea();

    /**
     * Puts the terminal in the raw input mode. (Typically, input is immediately reported)
     */
    void enterRawInputMode();

    /**
     * Puts the terminal in the non-raw input mode. (Typically, input is reported when enter is pressed)
     */
    void leaveRawInputMode();

    /**
     * Moves the cursor to the given row and column indices (0-based).
     * Translates these 0-based indices into 1-based indices for terminal use.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     */
    void moveCursor(int x, int y);

    /**
     * Clears the terminal.
     */
    void clearScreen();

    /**
     * Prints the given text starting at the given position in the terminal.
     * @param x The x coordinate. (0-based) Cannot be negative or bigger than the terminal width - 1.
     * @param y The y coordinate. (0-based) Cannot be negative or bigger than the terminal height - 1.
     * @param text The text. Cannot be null.
     *
     * @throws IllegalArgumentException If text is null.
     */
    void printText(int x, int y, String text);

    /**
     * Reads a byte from the stream and returns it.
     *
     * @return The byte.
     */
    int readByte();
}
