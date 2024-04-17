package com.textr.snake;

public final class Clock {

    private float lastMove;
    private float timeBetweenMoves;

    public Clock(float timeBetweenMoves){
        lastMove = System.currentTimeMillis();
        this.timeBetweenMoves = timeBetweenMoves;
    }


    public boolean shouldMove(){
        return System.currentTimeMillis() - lastMove > timeBetweenMoves;
    }

    public void reset(){
        lastMove = System.currentTimeMillis();
    }

    public void decreaseTimeBetweenMoves(){
        timeBetweenMoves *= 0.8;
    }
}
