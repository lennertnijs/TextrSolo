package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

import java.util.Objects;

/**
 * Represents an entire snake game.
 */
public final class SnakeGame {

    private final IGameBoard board;
    private final IClock clock;


    /**
     * Creates a new {@link SnakeGame}.
     * @param board The game board. Cannot be null.
     * @param clock The clock. Cannot be null.
     */
    public SnakeGame(IGameBoard board, IClock clock){
        Objects.requireNonNull(board, "GameBoard is null.");
        Objects.requireNonNull(clock, "Clock is null.");
        this.board = board.copy();
        this.clock = clock.copy();
    }

    /**
     * @return A copy of the game board.
     */
    public IGameBoard getBoard(){
        return board.copy();
    }

    /**
     * @return Whether the game is active.
     */
    public boolean isRunning(){
        return clock.isRunning();
    }

    /**
     * Resumes the game.
     */
    public void start(){
        clock.start();
    }

    /**
     * Pauses the game.
     */
    public void pause(){
        clock.stop();
    }

    /**
     * Changes the direction of the snake to the given {@link Direction}, if appropriate.
     * @param direction The direction. Cannot be null.
     */
    public void changeSnakeDirection(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        board.changeSnakeDirection(direction);
    }

    /**
     * Resizes the {@link IGameBoard} to the given {@link Dimension2D}.
     * @param dimensions The new dimensions. Cannot be null.
     */
    public void resizeBoard(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        board.resize(dimensions);
    }

    /**
     * Updates the snake game.
     * If the snake moved at all during the update, returns True. Returns False otherwise.
     * @param delta The delta (in milliseconds) to add to the clock. Cannot be negative.
     *
     * @return True if the snake moved. False otherwise.
     */
    public boolean update(int delta){
        if(delta < 0)
            throw new IllegalArgumentException("Millis is negative.");
        if(!clock.isRunning())
            return false;
        clock.increase(delta);
        if(!clock.shouldMove())
            return false;
        handleMove();
        return true;
    }

    /**
     * Handles the movement of the snake.
     * Appropriately updates the clock.
     */
    private void handleMove(){
        clock.reset();
        if(board.willEatOnMove())
            clock.changeSecondsBetweenMove(0.9f);
        try{
            board.moveSnake();
        }catch(IllegalStateException e){
            clock.stop();
        }
    }
}
