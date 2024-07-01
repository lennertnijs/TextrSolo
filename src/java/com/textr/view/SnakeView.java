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
public final class SnakeView extends View {

    /**
     * The snake game.
     */
    private SnakeGame snakeGame;

    /**
     * Creates a MUTABLE {@link SnakeView} in which a snake game is played.
     * @param position The position. Cannot be null.
     * @param dimensions The dimensions. Cannot be null.
     */
    public SnakeView(Point position, Dimension2D dimensions){
        super(position, dimensions);
        snakeGame = initializeGame();
    }

    public boolean gameIsRunning(){
        return snakeGame.isRunning();
    }


    public IGameBoard getGameBoard(){
        return snakeGame.getBoard();

    }

    public void restartGame(){
        this.snakeGame = initializeGame();
        snakeGame.start();
    }

    public SnakeGame initializeGame(){
        Snake snake = new Snake(Direction.RIGHT);
        for(int i = 0; i < 6; i++) {
            GamePoint snakeSegment = new GamePoint(getDimensions().width() / 2 - i, getDimensions().height() / 2);
            if(isWithinBoundaries(snakeSegment))
                snake.add(snakeSegment);
        }
        FoodManager foodManager = new FoodManager();
        Dimension2D dimensions = new Dimension2D(getDimensions().width() - 1, getDimensions().height() - 1);
        GameBoard board = GameBoard.createNew(dimensions, snake, foodManager);
        for(int i = 0; i < 12; i++)
            board.spawnFood();
        IClock clock = new GameClock(1f);
        return new SnakeGame(board, clock);
    }

    private boolean isWithinBoundaries(GamePoint p){
        return p.x() >= 0 && p.y() >= 0 && p.x() < getDimensions().width()-1 && p.y() < getDimensions().height()-1;
    }

    @Override
    public void setDimensions(Dimension2D dimensions){
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
        snakeGame.resizeBoard(new Dimension2D(dimensions.width()-1, dimensions.height()-1));
    }



    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case ENTER -> {if(!snakeGame.isRunning()) restartGame();}
            case ARROW_UP -> snakeGame.changeSnakeDirection(Direction.UP);
            case ARROW_RIGHT -> snakeGame.changeSnakeDirection(Direction.RIGHT);
            case ARROW_DOWN -> snakeGame.changeSnakeDirection(Direction.DOWN);
            case ARROW_LEFT -> snakeGame.changeSnakeDirection(Direction.LEFT);
        }
    }

    @Override
    public String generateStatusBar(){
        return String.format("Score: %d", snakeGame.getBoard().getScore());
    }

    @Override
    public boolean wasUpdated(){
        return snakeGame.update(10);
    }

}
