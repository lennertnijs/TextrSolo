package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.snake.*;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.Objects;

/**
 * Represents a view in which one can play a snake game.
 */
public final class SnakeView implements View {

    private Point position;
    private Dimension2D dimensions;
    /**
     * The snake game.
     */
    private SnakeGame snakeGame;
    /**
     * The snake game initializer. Is used to initialise new games, holding the settings.
     */
    private final SnakeGameInitializer initializer;

    /**
     * Creates a MUTABLE {@link SnakeView} in which a snake game is played.
     * Does NOT start the snake game.
     * @param position The position. Cannot be null.
     * @param dimensions The dimensions. Cannot be null.
     * @param initializer The snake game initializer. Cannot be null.
     */
    public SnakeView(Point position, Dimension2D dimensions, SnakeGameInitializer initializer){
        this.position = Objects.requireNonNull(position, "Position is null.");
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
        this.initializer = Objects.requireNonNull(initializer, "Initializer is null.");
        this.snakeGame = initializer.initialise(dimensions);
        snakeGame.start();
    }

    @Override
    public Point getPosition(){
        return position.copy();
    }

    @Override
    public void setPosition(Point position){
        this.position = Objects.requireNonNull(position, "Position is null.");
    }

    @Override
    public Dimension2D getDimensions(){
        return dimensions;
    }

    @Override
    public void setDimensions(Dimension2D dimensions){
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
    }

    /**
     * Handles snake view specific input, if mapped.
     * - Enter: if the game is not running, restart the game
     * - Arrow key: change the snake's direction (if allowed)
     */
    @Override
    public boolean handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case ENTER -> restartGame();
            case ARROW_UP -> snakeGame.changeSnakeDirection(Direction.UP);
            case ARROW_RIGHT -> snakeGame.changeSnakeDirection(Direction.RIGHT);
            case ARROW_DOWN -> snakeGame.changeSnakeDirection(Direction.DOWN);
            case ARROW_LEFT -> snakeGame.changeSnakeDirection(Direction.LEFT);
            case TICK -> {return snakeGame.update(10);}
            default -> {return false;}
        }
        return true;
    }

    @Override
    public boolean canClose(){
        return true;
    }

    @Override
    public void prepareToClose(){
    }

    @Override
    public String getStatusBar(){
        return String.format("Score: %d", snakeGame.getBoard().getScore());
    }

    @Override
    public View duplicate(){
        throw new UnsupportedOperationException("Cannot duplicate a snake game.");
    }

    /**
     * @return True if the game is running. False otherwise.
     */
    public boolean gameIsRunning(){
        return snakeGame.isRunning();
    }

    /**
     * @return The snake game's game board.
     */
    public IGameBoard getGameBoard(){
        return snakeGame.getBoard();
    }

    /**
     * Restarts the snake game.
     */
    public void restartGame(){
        this.snakeGame = initializer.initialise(dimensions);
        snakeGame.start();
    }
}
