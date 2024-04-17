package com.textr.snake;

public final class GameClock {

    private final TimeProvider timeProvider;
    private long lastMove;
    private float secondsBetweenMove;

    public GameClock(TimeProvider timeProvider, float secondsBetweenMove){
        this.timeProvider = timeProvider;
        lastMove = timeProvider.getTimeInMillis();
        this.secondsBetweenMove = secondsBetweenMove;
    }


    public boolean shouldMove(){
        return (float) (timeProvider.getTimeInMillis() - lastMove) / 1000 > secondsBetweenMove;
    }

    public void reset(){
        lastMove = timeProvider.getTimeInMillis();
    }

    public void changeSecondsBetweenMove(float f){
        secondsBetweenMove *= f;
    }
}
