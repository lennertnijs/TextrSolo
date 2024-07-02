package com.textr.view;

import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MockView implements View {

    private Point position;
    private Dimension2D dimensions;
    private final List<Input> receivedInput = new ArrayList<>();

    public MockView(Point position, Dimension2D dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public void setPosition(Point position) {

    }

    @Override
    public Dimension2D getDimensions() {
        return null;
    }

    @Override
    public void setDimensions(Dimension2D dimensions) {

    }

    @Override
    public boolean handleInput(Input input) {
        receivedInput.add(input);
        return true;
    }

    @Override
    public boolean canClose() {
        return false;
    }

    @Override
    public void prepareToClose() {

    }

    @Override
    public String getStatusBar() {
        return null;
    }

    @Override
    public View duplicate() {
        return null;
    }

    public List<Input> getReceivedInputs() {
        return receivedInput;
    }

    public void clearInputs() {
        receivedInput.clear();
    }
}
