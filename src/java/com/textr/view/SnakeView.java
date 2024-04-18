package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.snake.*;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.List;

public class SnakeView extends View {


    private SnakeGame snakeGame;

    public SnakeView(Point pos, Dimension2D dimensions){
        super(pos, dimensions);
        snakeGame = new SnakeGame(GameBoard.createNew(getDimensions(), new Snake(Direction.UP), new FoodManager()), new GameClock(5f));
    }

    public boolean gameIsRunning(){
        return snakeGame.isRunning();
    }

    public Direction getSnakeDirection(){
        return snakeGame.getDirection();
    }

    public List<GamePoint> getSnake(){
        return snakeGame.getSnake();
    }

    public List<GamePoint> getFoods(){
        return snakeGame.getFoods();
    }

    @Override
    public void resize(Dimension2D dimensions){
        this.setDimensions(dimensions);
    }

    @Override
    public boolean incrementTimer(){
        return hasChanged();
    }


    public void startGame(){
        snakeGame.start();
    }



    public void initializeGame(){
        Snake snake = new Snake(Direction.RIGHT);
        for(int i = 0; i < 4; i++) {
            GamePoint snakeSegment = new GamePoint(getDimensions().getWidth() / 2 - i, getDimensions().getHeight() / 2);
            if(isWithinBoundaries(snakeSegment))
                snake.add(snakeSegment);
        }
        FoodManager foodManager = new FoodManager();
        GameBoard board = GameBoard.createNew(getDimensions(), snake, foodManager);
        for(int i = 0; i < 50; i++)
            board.spawnFood();
        IClock clock = new GameClock(0.3f);
        this.snakeGame = new SnakeGame(board, clock);
    }

    private boolean isWithinBoundaries(GamePoint p){
        return p.x() >= 0 && p.y() >= 0 && p.x() < getDimensions().getWidth() && p.y() < getDimensions().getHeight();
    }

    public boolean hasChanged(){
        return snakeGame.update(10);
    }



    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case ENTER -> {
                if(!snakeGame.isRunning()) {
                    initializeGame();
                    snakeGame.start();
                }
            }
            case ARROW_UP -> snakeGame.changeSnakeDirection(Direction.UP);
            case ARROW_RIGHT -> snakeGame.changeSnakeDirection(Direction.RIGHT);
            case ARROW_DOWN -> snakeGame.changeSnakeDirection(Direction.DOWN);
            case ARROW_LEFT -> snakeGame.changeSnakeDirection(Direction.LEFT);
        }
    }
}
