package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.Objects;

public final class GameBoard {

    private final Dimension2D dimensions;
    private final SnakeManager snakeManager;
    private final FoodManager foodManager;
    private int score;

    private GameBoard(Dimension2D dimensions, SnakeManager snakeManager, FoodManager foodManager, int score){
        this.snakeManager = snakeManager;
        this.foodManager = foodManager;
        this.score = score;
        this.dimensions = dimensions;
    }

    public static GameBoard createWithZeroScore(Dimension2D dimensions, SnakeManager snakeManager, FoodManager foodManager){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        Objects.requireNonNull(snakeManager, "SnakeManager is null.");
        Objects.requireNonNull(foodManager, "FoodManager is null.");
        return new GameBoard(dimensions, snakeManager, foodManager, 0);
    }

    public void moveSnake(){
        Point next = snakeManager.getNextHeadPosition();
        if(isOutOfBounds(next) || snakeManager.isSnake(next))
            throw new IllegalStateException();
        if(foodManager.isFood(next)) {
            foodManager.remove(next);
            foodManager.add(generateRandomEmptyPoint());
            score += 1;
        }
        snakeManager.move();
    }

    private Point generateRandomEmptyPoint(){
        Point random = generateRandomPoint();
        while(foodManager.isFood(random) || snakeManager.isSnake(random))
            random = generateRandomPoint();
        return random;
    }

    private Point generateRandomPoint(){
        int randomX = (int) (Math.random() * dimensions.getWidth());
        int randomY = (int) (Math.random() * dimensions.getHeight());
        return Point.create(randomX, randomY);
    }

    private boolean isOutOfBounds(Point p){
        return p.getX() < 0 || p.getY() < 0 || p.getX() >= dimensions.getWidth() || p.getY() >= dimensions.getHeight();
    }

    public void resizeBoard(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
    }
}
