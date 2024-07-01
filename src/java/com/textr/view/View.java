package com.textr.view;

import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.Objects;

/**
 * An abstract class holding some of the shared view-related fields and methods.
 */
public abstract class View {

    /**
     * The position of the view.
     */
    protected Point position;
    /**
     * The dimensions of the view.
     */
    protected Dimension2D dimensions;

    public View(Point position, Dimension2D dimensions) {
        this.position = Objects.requireNonNull(position, "Position is null.");
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
    }

    /**
     * @return The 0-based position.
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * @return The dimensions.
     */
    public Dimension2D getDimensions() {return this.dimensions;}

    /**
     * Sets this view's position to the given point.
     * @param position The new position. Cannot be null.
     */
    public void setPosition(Point position){
        this.position = Objects.requireNonNull(position, "Position is null.");
    }

    /**
     * Sets this view's dimensions to the given dimensions.
     * @param dimensions The new dimensions. Cannot be null.
     */
    public void setDimensions(Dimension2D dimensions){
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
    }

    /**
     * Handle view-specific input.
     * @param input The input. Cannot be null.
     */
    public abstract void handleInput(Input input);

    /**
     * @return True if an update happened, in a constantly drawing tick-base view. False, otherwise.
     */
    public boolean wasUpdated() {
        return false;
    }

    /**
     * @return True if the view can be closed. False, if not currently closeable.
     */
    public boolean canBeClosed() {
        return true;
    }

    /**
     * Generates the status bar to be shown at the bottom of the view.
     */
    public abstract String generateStatusBar();

    /**
     * @return A duplicate version of this view, if supported.
     * @throws UnsupportedOperationException If duplication isn't supported by this view.
     */
    public View duplicate() {
        throw new UnsupportedOperationException();
    }
}
