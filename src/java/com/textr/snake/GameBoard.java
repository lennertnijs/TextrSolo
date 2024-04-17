package com.textr.snake;

import com.textr.util.Dimension2D;

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

    public int getScore(){
        return score;
    }

    public void moveSnake(){
        GamePoint next = snakeManager.getNextHeadPosition();
        if(isOutOfBounds(next) || snakeManager.isSnake(next))
            throw new IllegalStateException();
        if(foodManager.isFood(next)) {
            foodManager.remove(next);
            foodManager.add(generateRandomEmptyPoint());
            score += 1;
        }
        snakeManager.move();
    }

    private GamePoint generateRandomEmptyPoint(){
        GamePoint random = generateRandomPoint();
        while(foodManager.isFood(random) || snakeManager.isSnake(random))
            random = generateRandomPoint();
        return random;
    }

    private GamePoint generateRandomPoint(){
        int randomX = (int) (Math.random() * dimensions.getWidth());
        int randomY = (int) (Math.random() * dimensions.getHeight());
        return new GamePoint(randomX, randomY);
    }

    private boolean isOutOfBounds(GamePoint p){
        return p.getX() < 0 || p.getY() < 0 || p.getX() >= dimensions.getWidth() || p.getY() >= dimensions.getHeight();
    }

    public void resizeBoard(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        GamePoint snakeHead = snakeManager.getHead();
        GamePoint newMiddle = findMiddle(dimensions);
        Vector translationVector = new Vector(Math.abs(snakeHead.getX() - newMiddle.getX()), Math.abs(snakeHead.getY() - newMiddle.getY()));
        for(GamePoint p : snakeManager.getSnake()){
            GamePoint gamePoint = p.translate(translationVector);
            if(isOutOfBounds(gamePoint)){
                snakeManager.cut(p);
                break;
            }
            snakeManager.replace(p, gamePoint);
        }
        for(GamePoint p : foodManager.getFoods()){
            GamePoint gamePoint = p.translate(translationVector);
            if(isOutOfBounds(gamePoint))
                foodManager.remove(p);
            else
                foodManager.replace(p, gamePoint);
        }

    }

    private GamePoint findMiddle(Dimension2D dimensions){
        int middleX = dimensions.getWidth()/2;
        int middleY = dimensions.getHeight()/2;
        return new GamePoint(middleX, middleY);
    }
}
