package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.FixedPoint;
import com.textr.util.Point;

import java.util.Objects;

public final class Cursor implements ICursor{

    private int insertIndex;
    private FixedPoint insertPoint;

    private Cursor(int insertIndex, FixedPoint insertPoint){
        this.insertIndex = insertIndex;
        this.insertPoint = insertPoint;
    }

    /**
     * Creates a new {@link Cursor} that starts at index 0.
     *
     * @return The cursor.
     */
    public static Cursor createNew(){
        return new Cursor(0, new FixedPoint(0, 0));
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
     * @param skeleton The skeleton used to update the 2D Point.
     */
    public void setInsertIndex(int index, ITextSkeleton skeleton){
        this.insertIndex = index;
        this.insertPoint = skeleton.convertToPoint(index);
    }

    /**
     * Sets the 2D point of this {@link Cursor} to the given point.
     * Updates the insert index afterwards.
     * @param point A Point object holding the new insertion location.
     * @param skeleton The current text skeleton
     */
    public void setInsertPoint(Point point, ITextSkeleton skeleton) {
        if (point == null)
            throw new NullPointerException("Given insertion point cannot be null");
        this.insertPoint = new FixedPoint(point.getX(), point.getY());
        this.insertIndex = skeleton.convertToIndex(new FixedPoint(point.getX(), point.getY()));
    }

    /**
     * Moves this {@link Cursor} in the given direction.
     * Also updates the 2D representation appropriately.
     * @param direction The direction.
     * @param skeleton The skeleton to update the 2D.
     */
    public void move(Direction direction, ITextSkeleton skeleton){
        Objects.requireNonNull(direction, "Direction is null.");
        switch(direction){
            case UP -> moveUp(skeleton);
            case RIGHT -> moveRight(skeleton);
            case DOWN -> moveDown(skeleton);
            case LEFT -> moveLeft(skeleton);
        }
    }

    private void moveRight(ITextSkeleton skeleton){
        int incrementedIndex = insertIndex + 1;
        if(incrementedIndex < skeleton.getCharAmount()) {
            this.insertIndex = incrementedIndex;
        }
        this.insertPoint = skeleton.convertToPoint(this.insertIndex);
    }

    private void moveLeft(ITextSkeleton skeleton){
        int decrementedIndex = insertIndex - 1;
        if(decrementedIndex >= 0) {
            this.insertIndex = decrementedIndex;
        }
        this.insertPoint = skeleton.convertToPoint(this.insertIndex);
    }

    private void moveUp(ITextSkeleton skeleton){
        if(this.insertPoint.getY() == 0)
            return;
        int newY = this.insertPoint.getY() - 1;
        int newLineLength = skeleton.getLineLength(newY);
        int maxX = Math.max(0, newLineLength - 1);
        int newX = Math.min(maxX, this.insertPoint.getX());
        FixedPoint newPoint = new FixedPoint(newX, newY);
        this.insertIndex = skeleton.convertToIndex(newPoint);
        this.insertPoint = newPoint;
    }

    private void moveDown(ITextSkeleton skeleton){
        if(this.insertPoint.getY() == skeleton.getLineAmount() - 1)
            return;
        int newY = this.insertPoint.getY() + 1;
        int newLineLength = skeleton.getLineLength(newY);
        int maxX = Math.max(0, newLineLength - 1);
        int newX = Math.min(maxX, this.insertPoint.getX());
        FixedPoint newPoint = new FixedPoint(newX, newY);
        this.insertIndex = skeleton.convertToIndex(newPoint);
        this.insertPoint = newPoint;
    }
}
