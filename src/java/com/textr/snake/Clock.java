package com.textr.snake;

public final class Clock {

    private final TimeProvider timeProvider;
    private long lastMove;
    private float timeBetweenMoves;

    public Clock(TimeProvider timeProvider, float timeBetweenMoves){
        this.timeProvider = timeProvider;
        lastMove = timeProvider.getTimeInMillis();
        this.timeBetweenMoves = timeBetweenMoves;
    }


    public boolean shouldMove(){
        return timeProvider.getTimeInMillis() - lastMove > timeBetweenMoves;
    }

    public void reset(){
        lastMove = timeProvider.getTimeInMillis();
    }

    public void decreaseTimeBetweenMoves(){
        timeBetweenMoves *= 0.8;
    }
}
