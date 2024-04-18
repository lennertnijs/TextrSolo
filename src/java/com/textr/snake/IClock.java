package com.textr.snake;

public interface IClock {

    boolean isActive();
    void start();
    void stop();
    boolean shouldUpdate();
    void increaseTime(int increaseInMillis);
    void subtractThreshHold();
    void decreaseThreshold(float f);
    IClock copy();
}
