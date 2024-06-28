package com.textr.util;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public boolean isOpposite(Direction other){
        Objects.requireNonNull(other, "Direction is null.");
        switch(this){
            case UP -> {return other == Direction.DOWN;}
            case RIGHT -> {return other == Direction.LEFT;}
            case DOWN -> {return other == Direction.UP;}
            case LEFT -> {return other == Direction.RIGHT;}
        }
        throw new NoSuchElementException("No such Direction exists.");
    }
}
