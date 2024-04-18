package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

import java.util.List;
import java.util.Objects;

public final class GameBoard implements IGameBoard{

    private Dimension2D dimensions;
    private final Snake snake;
    private final FoodManager foodManager;
    private int score;

    private GameBoard(Dimension2D dimensions, Snake snake, FoodManager foodManager, int score){
        this.snake = snake;
        this.foodManager = foodManager;
        this.score = score;
        this.dimensions = dimensions;
    }

    public static GameBoard createNew(Dimension2D dimensions, Snake snake, FoodManager foodManager){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        Objects.requireNonNull(snake, "SnakeManager is null.");
        Objects.requireNonNull(foodManager, "FoodManager is null.");
        return new GameBoard(dimensions, snake, foodManager, 0);
    }

    public Dimension2D getDimensions(){
        return dimensions;
    }

    public List<GamePoint> getSnakePoints(){
        return snake.getBody();
    }

    public List<GamePoint> getFoods(){
        return foodManager.getFoods();
    }

    public int getScore(){
        return score;
    }

    public void moveSnake(){
        GamePoint next = snake.getNextHeadPosition();
        if(isOutOfBounds(next) || snake.isSnake(next))
            throw new IllegalStateException("Snake ate itself or went outside game borders.");
        if(foodManager.isFood(next)) {
            foodManager.remove(next);
            score += 1;
            boolean snakeFillsEntireBoard = snake.getLength() == dimensions.getHeight() * dimensions.getWidth();
            if(!snakeFillsEntireBoard)
                foodManager.add(generateRandomEmptyPoint());
        }
        snake.move();
    }

    private GamePoint generateRandomEmptyPoint(){
        GamePoint random = generateRandomPoint();
        while(foodManager.isFood(random) || snake.isSnake(random))
            random = generateRandomPoint();
        return random;
    }

    private GamePoint generateRandomPoint(){
        int randomX = (int) (Math.random() * dimensions.getWidth());
        int randomY = (int) (Math.random() * dimensions.getHeight());
        return new GamePoint(randomX, randomY);
    }

    private boolean isOutOfBounds(GamePoint p){
        return p.x() < 0 || p.y() < 0 || p.x() >= dimensions.getWidth() || p.y() >= dimensions.getHeight();
    }

    public void resize(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        boolean isHeightChange = dimensions.getHeight() != this.dimensions.getHeight();
        boolean isWidthChange = dimensions.getWidth() != this.dimensions.getWidth();
        if(isHeightChange && isWidthChange)
            throw new IllegalStateException("Both sides were changed. Not Supported.");
        if(isHeightChange)
            handleHeightChange(dimensions);
        if(isWidthChange)
            handleWidthChange(dimensions);
    }

    private void handleHeightChange(Dimension2D dimensions){
        boolean isShrink = dimensions.getHeight() < this.dimensions.getHeight();
        Vector2D translationVector;
        if(isShrink){
            int adjustedForEdge = Math.max(snake.getHead().y() - dimensions.getHeight() / 2, 0);
            int adjustedForBothEdges =  Math.min(adjustedForEdge, this.dimensions.getHeight() - dimensions.getHeight());
            translationVector = new Vector2D(0, -adjustedForBothEdges);
        }else{
            int middle = dimensions.getHeight() / 2;
            int adjustForTopEdge =  Math.min(middle, dimensions.getHeight() - this.dimensions.getHeight());
            translationVector = new Vector2D(0, adjustForTopEdge);
        }
        this.dimensions = dimensions;
        translateSnake(translationVector);
        translateFoods(translationVector);
    }

    private void handleWidthChange(Dimension2D dimensions){
        boolean isShrink = dimensions.getWidth() < this.dimensions.getWidth();
        Vector2D translationVector;
        if(isShrink){
            int adjustedForEdge = Math.max(snake.getHead().x() - dimensions.getWidth() / 2, 0);
            int adjustedForBothEdges =  Math.min(adjustedForEdge, this.dimensions.getWidth() - dimensions.getWidth());
            translationVector = new Vector2D(-adjustedForBothEdges, 0);
        }else{
            int middle = dimensions.getWidth() / 2;
            int adjustedForRightEdge =  Math.min(middle, dimensions.getWidth() - this.dimensions.getWidth());
            translationVector = new Vector2D(adjustedForRightEdge, 0);
        }
        this.dimensions = dimensions;
        translateSnake(translationVector);
        translateFoods(translationVector);
    }

    private void translateSnake(Vector2D vector){
        List<GamePoint> oldSnake = snake.getBody();
        snake.clear();
        for(GamePoint p : oldSnake){
            GamePoint gamePoint = p.translate(vector);
            if(isOutOfBounds(gamePoint))
                break;
            snake.add(gamePoint);
        }
    }

    private void translateFoods(Vector2D vector){
        List<GamePoint> oldFoods = foodManager.getFoods();
        foodManager.clearFoods();
        for(GamePoint p : oldFoods){
            GamePoint gamePoint = p.translate(vector);
            if(!isOutOfBounds(gamePoint))
                foodManager.add(gamePoint);
            else
                foodManager.add(generateRandomEmptyPoint());
        }
    }

    public void changeDirection(Direction direction){
        snake.changeDirection(direction);
    }
}
