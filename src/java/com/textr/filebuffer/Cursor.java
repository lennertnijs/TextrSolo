package com.textr.filebuffer;

import com.textr.util.Point;

import java.util.Objects;

public final class Cursor implements ICursor{

    private int insertIndex;
    private Point insertPoint;

    private Cursor(int insertIndex, Point insertPoint){
        this.insertIndex = insertIndex;
        this.insertPoint = insertPoint;
    }

    public static Cursor createNew(){
        return new Cursor(0, Point.create(0, 0));
    }

    public int getInsertIndex(){
        return insertIndex;
    }

    public Point getInsertPoint(){
        return insertPoint;
    }

    public void setInsertIndex(int insertIndex, ITextSkeleton skeleton){
        this.insertIndex = insertIndex;
        updateInsertPoint(skeleton);
    }

    private void updateInsertPoint(ITextSkeleton skeleton){
        if(insertIndex < 0 || insertIndex > skeleton.getCharacterCount())
            throw new IllegalArgumentException("The integer falls outside the text.");
        int count = 0;
        int row = -1;
        for(int i = 0; i < skeleton.getAmountOfLines() ; i++){
            if(count + skeleton.getLineLength(i) + 1 > insertIndex) {
                row = i;
                break;
            }
            count += skeleton.getLineLength(i) + 1;
        }
        int col = insertIndex - count;
        this.insertPoint = Point.create(col, row);
    }

    public void setInsertPoint(Point insertPoint, ITextSkeleton skeleton){
        this.insertPoint = insertPoint;
        updateInsertIndex(skeleton);
    }

    private void updateInsertIndex(ITextSkeleton skeleton){
        Objects.requireNonNull(insertPoint, "Point is null.");
        if(insertPoint.getX() >= skeleton.getAmountOfLines())
            throw new IllegalArgumentException("Y value of the Point is outside valid values.");
        if(insertPoint.getY() > skeleton.getLineLength(insertPoint.getX()))
            throw new IllegalArgumentException("X Value of the Point is outside valid values.");
        int count = 0;
        for(int i = 0; i < insertPoint.getX(); i++){
            count += skeleton.getLineLength(i) + 1;
        }
        count += insertPoint.getY();
        this.insertIndex = count;
    }

    public void moveRight(ITextSkeleton skeleton){
        if(insertIndex == skeleton.getCharacterCount())
            return;
        insertIndex++;
        updateInsertPoint(skeleton);
    }

    public void moveLeft(ITextSkeleton skeleton){
        if(insertIndex == 0)
            return;
        insertIndex--;
        updateInsertPoint(skeleton);
    }
}
