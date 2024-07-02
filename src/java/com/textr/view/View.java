package com.textr.view;

import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

public interface View {

    Point getPosition();
    void setPosition(Point position);
    Dimension2D getDimensions();
    void setDimensions(Dimension2D dimensions);
    boolean handleInput(Input input);
    boolean canClose();
    void prepareToClose();
    String getStatusBar();
    View duplicate();
}
