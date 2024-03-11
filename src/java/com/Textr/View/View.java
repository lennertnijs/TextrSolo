package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;

/**
 * Class to represent a view.
 */
public final class View {

    private final FileBuffer buffer;
    private Point position;
    private Dimension2D dimensions;
    private final Point anchor;


    private View(FileBuffer buffer, Point position, Dimension2D dimensions, Point anchor){
        this.buffer = buffer;
        this.position = position;
        this.dimensions = dimensions;
        this.anchor = anchor;

    }

    public static View createFromFilePath(String url, Point position, Dimension2D dimensions){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the View in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the View cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
        return new View(b, position.copy(), dimensions.copy(), Point.create(0,0));
    }

    public FileBuffer getBuffer(){
        return this.buffer;
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
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor;
    }

    /**
     * Set this views Position
     * @param position = the new position of the view
     */
    public void setPosition(Point position){
        Validator.notNull(position, "Error: Cannot set the new Position of a View to null.");
        this.position = position;
    }

    /**
     * Set this views Dimensions
     * @param dimensions = the new dimensions of the view
     */
    public void setDimensions(Dimension2D dimensions){
        Validator.notNull(position, "Error: Cannot set the new Dimension of a View to null.");
        this.dimensions = dimensions;
    }


    /**
     * Compares this view to the given object and returns True if they're equal. Returns False otherwise.
     * @param o The other object
     *
     * @return True if they're equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof View view)){
            return false;
        }
        return this.buffer.equals(view.buffer) &&
                this.dimensions.equals(view.dimensions) &&
                this.position.equals(view.position) &&
                this.anchor.equals(view.anchor);
    }

    /**
     * Generates and returns a hash code for this view.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        int result = buffer.hashCode();
        result = result * 31 + dimensions.hashCode();
        result = result * 31 + position.hashCode();
        result = result * 31 + anchor.hashCode();
        return result;
    }


    /**
     * Creates and returns a {@link String} representation of this view.
     *
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("View[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                buffer, position, dimensions, anchor);
    }

    public View copy(){
        return new View(this.buffer.copy(), this.position.copy(), this.dimensions.copy(), this.anchor.copy());
    }

    public boolean leftOff(View next){
        return this.getPosition().getX() < next.getPosition().getX();
    }
}
