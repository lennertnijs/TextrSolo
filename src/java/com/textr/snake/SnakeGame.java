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
        this.board = board;
        this.clock = clock;
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
    public boolean isActive(){
        return clock.isRunning();
    }

    /**
     * Resizes the {@link IGameBoard} to the given {@link Dimension2D}.
     * @param dimensions The new dimensions. Cannot be null.
     */
    public void resize(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        board.resize(dimensions);
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
     * Updates the snake game.
     * If the snake moved at all during the update, returns True. Returns False otherwise.
     *
     * @return True if the snake moved. False otherwise.
     */
    public boolean update(){
        if(!clock.isRunning())
            return false;
        if(!clock.shouldMove()){
            clock.increase(10);
            return false;
        }

        if(board.willEatOnMove())
            clock.changeSecondsBetweenMove(0.9f);
        clock.reset();
        try{
            board.moveSnake();
        }catch(IllegalStateException e){
            clock.stop();
        }
        clock.increase(10);
        return true;
    }
}
