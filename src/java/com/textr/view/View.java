package com.textr.view;

import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.Objects;

public abstract class View {

    private Point position;
    private Dimension2D dimensions;

    public View(Point position, Dimension2D dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    /**
     * @return This view's position. (0-based)
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * @return This view's dimensions.
     */
    public Dimension2D getDimensions() {return this.dimensions;}

    /**
     * Sets this view's Position
     * @param position = the new position of the view
     */
    public void setPosition(Point position){
        this.position = Objects.requireNonNull(position, "Cannot set the new Position of a View to null");
    }

    /**
     * Sets this view's Dimensions
     * @param dimensions = the new dimensions of the view
     */
    public void setDimensions(Dimension2D dimensions){
        this.dimensions = Objects.requireNonNull(dimensions, "Cannot set the new Dimension of a View to null");
    }

    /**
     * Handle view-specific input.
     * @param input
     */
    public abstract void handleInput(Input input);

    /**
     * Increments the timer of the view if relevant, and returns whether that changed something requiring a redraw.
     */
    public boolean incrementTimer() {
        return false;
    }

    /**
     * Resizes this view to the new dimensions.
     * @param dimensions the new dimensions of the view.
     */
    public void resize(Dimension2D dimensions) {
        setDimensions(dimensions);
    }

    /**
     * generate the statusbar of this view
     * @return
     */
    public String generateStatusBar() {
        return "";
    }

    /**
     * Marks this View for deletion, and allows the internal data of the view to adapt if necessary.
     * @return true if the view was successfully marked for deletion, false if the internal data prevents deletion.
     */
    public boolean markForDeletion() {
        return true;
    }
}
