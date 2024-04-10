package com.textr.view;

import com.textr.filebuffer.FileBuffer;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.util.Validator;

/**
 * Class to represent a view.
 */
public final class BufferView extends View {

    private final FileBuffer buffer;
    private final Point anchor;


    private BufferView(FileBuffer buffer, Point position, Dimension2D dimensions, Point anchor){
        super(position, dimensions);
        this.buffer = buffer;
        this.anchor = anchor;

    }

    public static BufferView createFromFilePath(String url, Point position, Dimension2D dimensions){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
        return new BufferView(b, position.copy(), dimensions.copy(), Point.create(0,0));
    }

    public FileBuffer getBuffer(){
        return this.buffer;
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor;
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
        if(!(o instanceof BufferView view)){
            return false;
        }
        return this.buffer.equals(view.buffer) &&
                this.getDimensions().equals(view.getDimensions()) &&
                this.getPosition().equals(view.getPosition()) &&
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
        result = result * 31 + getDimensions().hashCode();
        result = result * 31 + getPosition().hashCode();
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
        return String.format("BufferView[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                buffer, getPosition(), getDimensions(), anchor);
    }

    public BufferView copy(){
        return new BufferView(this.buffer.copy(), getPosition().copy(), getDimensions().copy(), this.anchor.copy());
    }
}
