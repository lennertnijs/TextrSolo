package com.textr.view;

import com.textr.snake.*;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;

public final class SnakeGameInitializer {

    private final int snakeLength;
    private final int foodAmount;

    public SnakeGameInitializer(int snakeLength, int foodAmount){
        this.snakeLength = snakeLength;
        this.foodAmount = foodAmount;
    }

    public SnakeGame initialise(Dimension2D dimensions){
            Snake snake = new Snake(Direction.RIGHT);
            for(int i = 0; i < snakeLength; i++) {
                GamePoint snakeSegment = new GamePoint(dimensions.width() / 2 - i, dimensions.height() / 2);
                if(isWithinBoundaries(snakeSegment, dimensions))
                    snake.add(snakeSegment);
            }

            FoodManager foodManager = new FoodManager();
            GameBoard board = GameBoard.createNew(dimensions, snake, foodManager);
            for(int i = 0; i < foodAmount; i++) {
                board.spawnFood();
            }
            IClock clock = new GameClock(1f);
            return new SnakeGame(board, clock);
    }

    private boolean isWithinBoundaries(GamePoint p, Dimension2D dimensions){
        return p.x() >= 0 && p.y() >= 0 && p.x() < dimensions.width()-1 && p.y() < dimensions.height()-1;
    }
}
