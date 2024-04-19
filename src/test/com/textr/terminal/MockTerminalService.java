package com.textr.terminal;

import com.textr.util.Dimension2D;
import com.textr.util.FixedPoint;

import java.util.LinkedList;
import java.util.Queue;

public class MockTerminalService implements TerminalService {

    private final Queue<Byte> bytes = new LinkedList<>();

    private int cursorX = 0;
    private int cursorY = 0;
    private boolean usingRawInput = false;

    @Override
    public Dimension2D getTerminalArea() {
        return null;
    }

    @Override
    public void enterRawInputMode() {
        usingRawInput = true;
    }

    @Override
    public void leaveRawInputMode() {
        usingRawInput = false;
    }

    @Override
    public void moveCursor(int x, int y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException(String.format(
                    "Cursor cannot have negative index: (%d, %d)", x, y));
        this.cursorX = x;
        this.cursorY = y;
    }

    public FixedPoint getCursorLocation() {
        return new FixedPoint(cursorX, cursorY);
    }

    @Override
    public void clearScreen() {
        throw new UnsupportedOperationException("W.I.P."); // TODO
    }

    @Override
    public void printText(int x, int y, String text) {
        throw new UnsupportedOperationException("W.I.P."); // TODO
    }

    public void addInput(byte... newBytes) {
        for (byte newByte: newBytes) {
            this.bytes.add(newByte);
        }
    }

    public void clearInput() {
        this.bytes.clear();
    }

    public boolean hasNoMoreInput() {
        return this.bytes.isEmpty();
    }

    @Override
    public int readByte() {
        return this.bytes.remove();
    }
}
