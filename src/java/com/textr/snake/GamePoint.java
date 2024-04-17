package com.textr.snake;

import java.util.Objects;

public final class GamePoint {

    private final int x;
    private final int y;

    public GamePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public GamePoint translate(Vector vector){
        Objects.requireNonNull(vector, "Vector is null.");
        return new GamePoint(x + vector.getX(), y + vector.getY());
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof GamePoint p))
            return false;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode(){
        int result = x;
        result = result * 31 + y;
        return result;
    }

    @Override
    public String toString(){
        return String.format("GamePoint[x=%d, y=%d]", x, y);
    }
}
