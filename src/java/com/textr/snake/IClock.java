package com.textr.snake;

public interface IClock {

    boolean shouldMove();
    void reset();
    void decreaseTimeBetweenMoves();
}
