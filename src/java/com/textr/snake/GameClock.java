package com.textr.snake;

/**
 * Represents the clock for a game.
 */
public final class GameClock implements IClock{

    private float secondsBetweenMove;
    private long lastMove;
    private boolean running;

    /**
     * Creates a new {@link GameClock}
     * @param secondsBetweenMove The seconds between each move. Cannot be negative or 0.
     */
    public GameClock(float secondsBetweenMove){
        if(secondsBetweenMove <= 0)
            throw new IllegalArgumentException("Seconds between move is negative or 0.");
        this.secondsBetweenMove = secondsBetweenMove;
        lastMove = 0;
        running = false;
    }



    public void increase(int increaseInMillis){
        if(increaseInMillis < 0)
            throw new IllegalArgumentException("Increase is negative.");
        lastMove += increaseInMillis;
    }

    /**
     * Checks whether a move should happen.
     * @return True if a move should happen. False otherwise.
     */
    public boolean shouldMove(){
        return (float) lastMove / 1000 > secondsBetweenMove;
    }

    /**
     * Resets the clock.
     * Use when a move is executed.
     */
    public void reset(){
        lastMove = 0;
    }

    /**
     * Changes the time between moves (in seconds).
     * @param f The factor of change.
     */
    public void changeSecondsBetweenMove(float f){
        if(f <= 0)
            throw new IllegalArgumentException("Factor has to be strictly positive.");
        secondsBetweenMove *= f;
    }
    public boolean isRunning(){
        return running;
    }

    public void start(){
        this.running = true;
    }

    public void stop(){
        this.running = false;
    }

}
