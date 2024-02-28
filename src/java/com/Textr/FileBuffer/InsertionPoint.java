package com.Textr.FileBuffer;

public class InsertionPoint {

    private final int x;
    private final int y;

    private InsertionPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static InsertionPoint create(int x, int y){
        if(x < 0 || y < 0){
            throw  new IllegalArgumentException("Cannot create an InsertionPoint with a negative coordinate.");
        }
        return new InsertionPoint(x, y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof InsertionPoint point)){
            return false;
        }
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode(){
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString(){
        return String.format("InsertionPoint[x = %d, y = %d]", x, y);
    }
}
