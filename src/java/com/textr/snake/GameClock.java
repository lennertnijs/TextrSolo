package com.textr.snake;

import java.util.Locale;

/**
 * Represents the clock for a game.
 */
public final class GameClock implements IClock{

    /**
     * The elapsed time. (in seconds)
     */
    private float elapsedTimeInSeconds;
    /**
     * The threshold (in seconds) over which the elapsedTime has to go for an update to happen.
     */
    private float threshHoldInSeconds;
    /**
     * Indicates whether the {@link GameClock} is active or not.
     */
    private boolean running;

    /**
     * Creates a new {@link GameClock}.
     * @param threshHoldInSeconds The threshold (in seconds). Cannot be negative or 0.
     */
    public GameClock(float threshHoldInSeconds){
        elapsedTimeInSeconds = 0;
        if(threshHoldInSeconds <= 0)
            throw new IllegalArgumentException("Threshold has to be strictly positive.");
        this.threshHoldInSeconds = threshHoldInSeconds;
        running = false;
    }

    /**
     * Creates a new {@link GameClock}.
     * @param elapsedTimeInSeconds The elapsed time (in seconds).
     * @param threshHoldInSeconds The threshold (in seconds). Cannot be negative or 0.
     * @param running Indicates whether the clock is active or not.
     */
    private GameClock(float elapsedTimeInSeconds, float threshHoldInSeconds, boolean running){
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.threshHoldInSeconds = threshHoldInSeconds;
        this.running = running;
    }

    /**
     * @return Whether the {@link GameClock} is active or not.
     */
    public boolean isActive(){
        return running;
    }

    /**
     * Sets the {@link GameClock} to active.
     */
    public void start(){
        this.running = true;
    }

    /**
     * Sets the {@link GameClock} to inactive.
     */
    public void stop(){
        this.running = false;
    }

    /**
     * Checks whether a move should happen.
     * @return True if a move should happen. False otherwise.
     */
    public boolean shouldUpdate(){
        return elapsedTimeInSeconds > threshHoldInSeconds;
    }

    /**
     * Increases the elapsed time with the given time (in millis).
     * @param increaseInMillis The time to add (in millis). Cannot be negative.
     */
    public void increaseTime(int increaseInMillis){
        if(increaseInMillis < 0)
            throw new IllegalArgumentException("Increase is negative.");
        elapsedTimeInSeconds += ((float) increaseInMillis / 1000L);
    }

    /**
     * Subtracts the threshold from the elapsed time.
     * Use when an update happened.
     */
    public void subtractThreshHold(){
        elapsedTimeInSeconds -= threshHoldInSeconds;
    }

    /**
     * Changes the threshold by multiplication with the given factor.
     * @param factor The factor. Has to be 0 < factor < 1.
     */
    public void decreaseThreshold(float factor){
        if(factor <= 0 || factor >= 1)
            throw new IllegalArgumentException("Factor has to be strictly positive, and smaller than 1.");
        threshHoldInSeconds *= factor;
    }

    /**
     * @return A copy of this {@link GameClock}.
     */
    public GameClock copy(){
        return new GameClock(elapsedTimeInSeconds, threshHoldInSeconds, running);
    }

    /**
     * @return True if the two objects are equal. False otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof GameClock clock))
            return false;
        return elapsedTimeInSeconds == clock.elapsedTimeInSeconds &&
                threshHoldInSeconds == clock.threshHoldInSeconds &&
                running == clock.running;
    }

    /**
     * @return The hash code for this {@link GameClock}.
     */
    @Override
    public int hashCode(){
        int result = Float.hashCode(elapsedTimeInSeconds);
        result = result * 31 + Float.hashCode(threshHoldInSeconds);
        result = result * 31 + Boolean.hashCode(running);
        return result;
    }

    /**
     * @return A string representation of this {@link GameClock}.
     */
    @Override
    public String toString(){
        // Locale.US to ensure decimal separator is '.'
        // Tests fail on my machine, even if I check for the default Locale's decimal separator, for some reason...
        return String.format(Locale.US, "GameClock[elapsedTimeInSeconds=%f, threshHoldInSeconds=%f, running=%b]",
                elapsedTimeInSeconds,threshHoldInSeconds, running);
    }
}
