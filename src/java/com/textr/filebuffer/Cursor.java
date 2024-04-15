package com.textr.filebuffer;

import com.textr.util.Point;

import java.util.List;
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

    public void setInsertIndex(int insertIndex, List<Integer> lengths){
        this.insertIndex = insertIndex;
        updateInsertPoint(lengths);
    }

    private void updateInsertPoint(List<Integer> lengths){
        if(insertIndex < 0 || insertIndex > lengths.size())
            throw new IllegalArgumentException("The integer falls outside the text.");
        int count = 0;
        int row = -1;
        for(int i = 0; i < lengths.size() ; i++){
            if(count + lengths.get(i) + 1 > insertIndex) {
                row = i;
                break;
            }
            count += lengths.get(i) + 1;
        }
        int col = insertIndex - count;
        this.insertPoint = Point.create(row, col);
    }

    public void setInsertPoint(Point insertPoint, List<Integer> lengths){
        this.insertPoint = insertPoint;
        updateInsertIndex(lengths);
    }

    private void updateInsertIndex(List<Integer> lengths){
        Objects.requireNonNull(insertPoint, "Point is null.");
        if(insertPoint.getX() >= lengths.size())
            throw new IllegalArgumentException("Y value of the Point is outside valid values.");
        if(insertPoint.getY() > lengths.get(insertPoint.getX()))
            throw new IllegalArgumentException("X Value of the Point is outside valid values.");
        int count = 0;
        for(int i = 0; i < insertPoint.getX(); i++){
            count += lengths.get(i) + 1;
        }
        count += insertPoint.getY();
        this.insertIndex = count;
    }
}
