package com.textr.view;

import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MockView extends View {

    private final List<Input> receivedInput = new ArrayList<>();

    public MockView(Point position, Dimension2D dimensions) {
        super(position, dimensions);
    }

    @Override
    public void handleInput(Input input) {
        receivedInput.add(input);
    }

    public List<Input> getReceivedInputs() {
        return receivedInput;
    }

    public void clearInputs() {
        receivedInput.clear();
    }
}
