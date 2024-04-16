package com.textr.filebuffer;

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
        return new Cursor(0, Point.create(0, 0));
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
        return insertPoint;
    }

    /**
     * Sets the 1D index of this {@link Cursor} to the given index.
     * Updates the 2D Point after wards.
     * @param index The new index.
     * @param skeleton The skeleton used to update the 2D Point.
     */
    public void setInsertIndex(int index, ITextSkeleton skeleton){
        this.insertIndex = index;
        updateInsertPoint(skeleton);
    }

    private void updateInsertPoint(ITextSkeleton skeleton){
        if(insertIndex < 0 || insertIndex > skeleton.getCharAmount())
            throw new IllegalArgumentException("The integer falls outside the text.");
        int count = 0;
        int row = -1;
        for(int i = 0; i < skeleton.getLineAmount() ; i++){
            if(insertIndex < count + skeleton.getLineLength(i)) {
                row = i;
                break;
            }
            count += skeleton.getLineLength(i);
        }
        int col = insertIndex - count;
        this.insertPoint = Point.create(col, row);
    }

    private void updateInsertIndex(ITextSkeleton skeleton){
        int count = 0;
        for(int i = 0; i < insertPoint.getY(); i++){
            count += skeleton.getLineLength(i);
        }
        count += insertPoint.getX();
        this.insertIndex = count;
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
            updateInsertPoint(skeleton);
        }
    }

    private void moveLeft(ITextSkeleton skeleton){
        int decrementedIndex = insertIndex - 1;
        if(decrementedIndex >= 0) {
            this.insertIndex = decrementedIndex;
            updateInsertPoint(skeleton);
        }
    }

    private void moveUp(ITextSkeleton skeleton){
        if(insertPoint.getY() == 0)
            return;
        insertPoint.decrementY();
        int newLineLength = Math.max(skeleton.getLineLength(insertPoint.getY()) - 1, 0);
        int newX = Math.min(newLineLength, insertPoint.getX());
        insertPoint.setX(newX);
        updateInsertIndex(skeleton);
    }

    private void moveDown(ITextSkeleton skeleton){
        if(insertPoint.getY() == skeleton.getLineAmount() - 1)
            return;
        insertPoint.incrementY();
        int newLineLength = Math.max(skeleton.getLineLength(insertPoint.getY()) - 1, 0);
        int newX = Math.min(newLineLength, insertPoint.getX());
        insertPoint.setX(newX);
        updateInsertIndex(skeleton);
    }
}
