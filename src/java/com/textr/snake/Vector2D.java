package com.textr.snake;

public final class Vector2D {

    private final int x;
    private final int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Vector2D vector))
            return false;
        return x == vector.x && y == vector.y;
    }

    @Override
    public int hashCode(){
        int result = x;
        result = result * 31 + y;
        return result;
    }

    @Override
    public String toString(){
        return String.format("Vector2D[x=%d, y=%d]", x, y);
    }
}
