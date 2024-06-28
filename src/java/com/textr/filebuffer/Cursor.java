package com.textr.filebuffer;

import com.textr.filebufferV2.IText;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.Objects;

public final class Cursor implements ICursor{

    private int insertIndex;
    private Point insertPoint;

    private Cursor(int insertIndex, Point insertPoint){
        this.insertIndex = insertIndex;
        this.insertPoint = insertPoint;
    }

    /**
     * Creates a new {@link Cursor} that starts at index 0.
     *
     * @return The cursor.
     */
    public static Cursor createNew(){
        return new Cursor(0, new Point(0, 0));
    }

    /**
     * @return The 1-dimensional index of this {@link Cursor}.
     */
    public int getInsertIndex(){
        return insertIndex;
    }

    /**
     * @return The 2-dimensional index of this {@link Cursor}.
     */
    public Point getInsertPoint(){
        return Point.create(insertPoint.getX(), insertPoint.getY());
    }

    /**
     * Sets the location of this {@link Cursor} to the given 1D index, using the given skeleton structure.
     * @param index The new index.
     */
    public void setInsertIndex(int index, IText text){
        this.insertIndex = index;
        this.insertPoint = convertToPoint(text);
    }

    /**
     * Sets the 2D point of this {@link Cursor} to the given point.
     * Updates the insert index afterwards.
     * @param point A Point object holding the new insertion location.
     */
    public void setInsertPoint(Point point, IText text) {
        if (point == null)
            throw new NullPointerException("Given insertion point cannot be null");
        this.insertPoint = new Point(point.getX(), point.getY());
        this.insertIndex = convertToIndex(text);
    }

    /**
     * Moves this {@link Cursor} in the given direction.
     * Also updates the 2D representation appropriately.
     * @param direction The direction.
     */
    public void move(Direction direction, IText text){
        Objects.requireNonNull(direction, "Direction is null.");
        switch(direction){
            case UP -> moveUp(text);
            case RIGHT -> moveRight(text);
            case DOWN -> moveDown(text);
            case LEFT -> moveLeft(text);
        }
    }

    private void moveRight(IText text){
        int incrementedIndex = insertIndex + 1;
        if(incrementedIndex < text.getCharAmount()) {
            this.insertIndex = incrementedIndex;
        }
        this.insertPoint = convertToPoint(text);
    }

    private void moveLeft(IText text){
        int decrementedIndex = insertIndex - 1;
        if(decrementedIndex >= 0) {
            this.insertIndex = decrementedIndex;
        }
        this.insertPoint = convertToPoint(text);
    }

    private void moveUp(IText text){
        if(insertPoint.getY() == 0)
            return;
        int newY = insertPoint.getY() - 1;
        int newLineLength = text.getLineLength(newY);
        int maxX = Math.max(0, newLineLength - 1);
        int newX = Math.min(maxX, this.insertPoint.getX());
        Point newPoint = new Point(newX, newY);
        this.insertIndex = convertToIndex(text);
        this.insertPoint = newPoint;
    }

    private void moveDown(IText text){
        if(this.insertPoint.getY() == text.getLineAmount() - 1)
            return;
        int newY = this.insertPoint.getY() + 1;
        int newLineLength = text.getLineLength(newY);
        int maxX = Math.max(0, newLineLength - 1);
        int newX = Math.min(maxX, this.insertPoint.getX());
        Point newPoint = new Point(newX, newY);
        this.insertIndex = convertToIndex(text);
        this.insertPoint = newPoint;
    }

    /**
     * Converts the given index to a {@link Point} object based on this text skeleton. Newlines are considered
     * to be at the end of the line, and they count as one character on that line.
     * @return The 2D Point representing the same location as the given index in the text skeleton
     */
    public Point convertToPoint(IText text) {
        if (insertIndex < 0 || insertIndex > text.getCharAmount()) {
            throw new IndexOutOfBoundsException("Index is outside text bounds.");
        }
        int count = 0;
        int row = -1;
        for(int i = 0; i < text.getLineAmount(); i++){
            if(insertIndex < count + text.getLineLength(i)) {
                row = i;
                break;
            }
            count += text.getLineLength(i) + 1;
        }
        int col = insertIndex - count;
        return new Point(col, row);
    }

    /**
     * Converts the given point into an index representing the same location in the text skeleton. Points at the end of
     * a line (i.e. with X at the line length) are considered to be on the newline character.
     * @return The 1D index in the text structure representing the same position as the given 2D point
     * @throws IllegalArgumentException if the given point is not a valid point in the text structure
     */
    public int convertToIndex(IText text) {
        if (text.getLineAmount() <= insertPoint.getY() || text.getLineLength(insertPoint.getY()) < insertPoint.getX())
            throw new IllegalArgumentException("Given point does not hold a valid location in this TextSkeleton");
        int count = 0;
        for(int i = 0; i < insertPoint.getY(); i++){
            count += text.getLineLength(i);
        }
        count += insertPoint.getX();
        return count;
    }
}
