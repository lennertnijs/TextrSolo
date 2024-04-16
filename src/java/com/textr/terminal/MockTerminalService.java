package com.textr.terminal;

import com.textr.util.Dimension2D;

// TODO: Implement and use in tests
public class MockTerminalService implements TerminalService {

    @Override
    public Dimension2D getTerminalArea() {
        return null;
    }

    @Override
    public void enterRawInputMode() {

    }

    @Override
    public void leaveRawInputMode() {

    }

    @Override
    public void moveCursor(int x, int y) {

    }

    @Override
    public void clearScreen() {

    }

    @Override
    public void printText(int x, int y, String text) {

    }

    @Override
    public int readByte() {
        return 0;
    }
}
