package com.textr.snake;

import java.util.Objects;

/**
 * Represents the clock for a game.
 */
public final class GameClock {

    private final TimeProvider timeProvider;
    private long lastMove;
    private float secondsBetweenMove;

    /**
     * Creates a new {@link GameClock}.
     * @param timeProvider The {@link TimeProvider}. Cannot be null.
     * @param secondsBetweenMove The seconds between each move. Cannot be negative or 0.
     */
    public GameClock(TimeProvider timeProvider, float secondsBetweenMove){
        Objects.requireNonNull(timeProvider, "TimeProvider is null.");
        if(secondsBetweenMove <= 0)
            throw new IllegalArgumentException("Seconds between move is negative or 0.");
        this.timeProvider = timeProvider;
        lastMove = timeProvider.getTimeInMillis();
        this.secondsBetweenMove = secondsBetweenMove;
    }

    /**
     * Checks whether a move should happen.
     * @return True if a move should happen. False otherwise.
     */
    public boolean shouldMove(){
        return (float) (timeProvider.getTimeInMillis() - lastMove) / 1000 > secondsBetweenMove;
    }

    /**
     * Resets the clock.
     * Use when a move is executed.
     */
    public void reset(){
        lastMove = timeProvider.getTimeInMillis();
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
}
