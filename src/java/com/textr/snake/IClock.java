package com.textr.snake;

public interface IClock {

    void increase(int increaseInMillis);
    boolean shouldMove();
    void reset();
    void changeSecondsBetweenMove(float f);
}
