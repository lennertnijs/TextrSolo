package com.textr.view;

import com.textr.filebuffer.FileBuffer;
import com.textr.input.Input;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.util.Validator;

public class View {
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
     * Set this views Position
     * @param position = the new position of the view
     */
    public void setPosition(Point position){
        Validator.notNull(position, "Error: Cannot set the new Position of a BufferView to null.");
        this.position = position;
    }

    /**
     * Set this views Dimensions
     * @param dimensions = the new dimensions of the view
     */
    public void setDimensions(Dimension2D dimensions){
        Validator.notNull(dimensions, "Error: Cannot set the new Dimension of a BufferView to null.");
        this.dimensions = dimensions;
    }

    /**
     * Handle view-specific input.
     * @param input
     */
    public void handleInput(Input input) {
    }

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
