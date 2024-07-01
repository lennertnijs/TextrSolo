package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

import java.util.List;
import java.util.Objects;

/**
 * Represents the board on which the game of snake is being played.
 * More precisely, a game board has the following:
 * - The snake
 * - The locations of the food
 * - Dimensions
 * - A score
 */
public final class GameBoard implements IGameBoard{

    private Dimension2D dimensions;
    private final Snake snake;
    private final FoodManager foodManager;
    private int score;

    /**
     * Creates a new {@link GameBoard}.
     * @param dimensions The dimensions of this {@link GameBoard}. Cannot be null.
     * @param snake The snake of the {@link GameBoard}. Cannot be null.
     * @param foodManager The food manager of the {@link GameBoard}. Cannot be null.
     * @param score The starting score of the {@link GameBoard}.
     */
    private GameBoard(Dimension2D dimensions, Snake snake, FoodManager foodManager, int score){
        this.dimensions = dimensions;
        this.snake = snake;
        this.foodManager = foodManager;
        this.score = score;
    }

    /**
     * Creates and returns a new {@link GameBoard} with the given parameters.
     * A new {@link GameBoard} means that the score is initiated at 0.
     * @param dimensions The dimensions of the {@link GameBoard}. Cannot be null.
     * @param snake The snake. Cannot be null. Cannot contain segments outside the dimensions.
     * @param foodManager The food manager. Cannot be null. Cannot contain foods outside the dimensions.
     *
     * @return The newly created {@link GameBoard}.
     * @throws IllegalArgumentException If the given Snake or FoodManager contain GamePoints outside the board.
     */
    public static GameBoard createNew(Dimension2D dimensions, Snake snake, FoodManager foodManager){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        Objects.requireNonNull(snake, "SnakeManager is null.");
        for(GamePoint p : snake.getBody())
            if(p.x() < 0 || p.y() < 0 || p.x() >= dimensions.getWidth() || p.y() >= dimensions.getHeight())
                throw new IllegalArgumentException("Snake has segments outside the GameBoard.");
        Objects.requireNonNull(foodManager, "FoodManager is null.");
        for(GamePoint p : foodManager.getFoods())
            if(p.x() < 0 || p.y() < 0 || p.x() >= dimensions.getWidth() || p.y() >= dimensions.getHeight())
                throw new IllegalArgumentException("FoodManager has food outside the GameBoard.");
        return new GameBoard(dimensions, snake.copy(), foodManager.copy(), 0);
    }

    /**
     * @return The dimension of this {@link GameBoard}.
     */
    public Dimension2D getDimensions(){
        return dimensions;
    }

    /**
     * @return The List of {@link GamePoint}s representing the snake.
     */
    public List<GamePoint> getSnakePoints(){
        return snake.getBody();
    }

    /**
     * @return The List of {@link GamePoint}s representing the foods.
     */
    public List<GamePoint> getFoods(){
        return foodManager.getFoods();
    }

    /**
     * @return The score.
     */
    public int getScore(){
        return score;
    }

    /**
     * Moves the snake in its direction by one unit, if this is legal.
     * More precisely, if the next {@link GamePoint} is not outside the board, and it is not a segment of the snake,
     * moves by one unit.
     * If the next {@link GamePoint} to where the snake moves contains a piece of food, it will eat it, increasing
     * its own length by one.
     * If a piece of food was eaten, a new one will spawn randomly, as long as there is space for this.
     */
    public void moveSnake(){
        GamePoint nextHeadPosition = snake.getNextHeadPosition();
        if(isOutOfBounds(nextHeadPosition) || (snake.isSnake(nextHeadPosition) && !snake.getTail().equals(nextHeadPosition)))
            throw new IllegalStateException("Snake ate itself or went outside game borders.");
        boolean snakeWillEat = foodManager.isFood(nextHeadPosition);
        if(snakeWillEat) {
            foodManager.remove(nextHeadPosition);
            score += 10;
            spawnFood();
        }else{
            score += 1;
        }
        snake.move(snakeWillEat);
    }

    /**
     * @return True if the snake will eat on the next move.
     */
    public boolean willEatOnMove(){
        return foodManager.isFood(snake.getNextHeadPosition());
    }

    /**
     * Generates a random {@link GamePoint} within this {@link GameBoard}'s dimensions that is empty.
     *
     * @return The {@link GamePoint}.
     */
    private GamePoint generateRandomEmptyPoint(){
        GamePoint random = generateRandomPoint();
        while(foodManager.isFood(random) || snake.isSnake(random))
            random = generateRandomPoint();
        return random;
    }

    /**
     * Generates a random {@link GamePoint} within this {@link GameBoard}'s dimensions.
     *
     * @return The {@link GamePoint}.
     */
    private GamePoint generateRandomPoint(){
        int randomX = (int) (Math.random() * dimensions.getWidth());
        int randomY = (int) (Math.random() * dimensions.getHeight());
        return new GamePoint(randomX, randomY);
    }

    /**
     * Checks whether the given {@link GamePoint} is within the {@link GameBoard}'s constraints.
     * @param p The {@link GamePoint}. Cannot be null.
     *
     * @return True if within boundaries. False otherwise.
     */
    private boolean isOutOfBounds(GamePoint p){
        return p.x() < 0 || p.y() < 0 || p.x() >= dimensions.getWidth() || p.y() >= dimensions.getHeight();
    }

    /**
     * Resizes the {@link GameBoard} to the given dimensions.
     * More precisely, the part of the game field that is retained is chosen such that the snake head always ends up as
     * close to the center of the {@link GameBoard} as possible, without introducing new tiles.
     * @param dimensions The new dimensions. Cannot be null.
     */
    public void resize(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Dimensions is null.");
        boolean isHeightChange = dimensions.getHeight() != this.dimensions.getHeight();
        boolean isWidthChange = dimensions.getWidth() != this.dimensions.getWidth();
        if(isHeightChange)
            handleHeightChange(dimensions.getHeight());
        if(isWidthChange)
            handleWidthChange(dimensions.getWidth());
    }

    /**
     * Handles a height change to the given height.
     * After handling the change, translates the snake and the foods & adjust the {@link GameBoard}'s dimensions.
     * *
     * For shrinking, holds the new rectangle over the snake's head,
     * and moves this to fit within the old fields boundaries.
     * *
     * For expanding, holds the old rectangle over the middle of the new & bigger rectangle,
     * and moves it to fit within its boundaries.
     * *
     * Note that both operations work the same way, just in the opposite direction.
     * @param newHeight The new height. Cannot be negative.
     */
    private void handleHeightChange(int newHeight){
        int oldHeight = this.dimensions.getHeight();
        boolean isShrink = newHeight < oldHeight;
        Vector2D translationVector;
        if(isShrink){
            int adjustedForEdge = Math.max(snake.getHead().y() - newHeight / 2, 0);
            int adjustedForBothEdges =  Math.min(adjustedForEdge, oldHeight - newHeight);
            translationVector = new Vector2D(0, -adjustedForBothEdges);
        }else{
            int adjustForTopEdge =  Math.min(newHeight / 2, newHeight - oldHeight);
            translationVector = new Vector2D(0, adjustForTopEdge - snake.getHead().y());
        }
        this.dimensions = new Dimension2D(this.dimensions.getWidth(), newHeight);
        translateSnake(translationVector);
        translateFoods(translationVector);
    }

    /**
     * Handles a width change to the given width.
     * After handling the change, translates the snake and the foods & adjust the {@link GameBoard}'s dimensions.
     * *
     * For shrinking, holds the new rectangle over the snake's head,
     * and moves this to fit within the old fields boundaries.
     * *
     * For expanding, holds the old rectangle over the middle of the new & bigger rectangle,
     * and moves it to fit within its boundaries.
     * *
     * Note that both operations work the same way, just in the opposite direction.
     * @param newWidth The new width. Cannot be negative.
     */
    private void handleWidthChange(int newWidth){
        int oldWidth = this.dimensions.getWidth();
        boolean isShrink = newWidth < oldWidth;
        Vector2D translationVector;
        if(isShrink){
            int adjustedForEdge = Math.max(snake.getHead().x() - newWidth / 2, 0);
            int adjustedForBothEdges =  Math.min(adjustedForEdge, oldWidth - newWidth);
            translationVector = new Vector2D(-adjustedForBothEdges, 0);
        }else{
            int adjustedForRightEdge =  Math.min(newWidth / 2, newWidth - oldWidth);
            translationVector = new Vector2D(adjustedForRightEdge - snake.getHead().x(), 0);
        }
        this.dimensions = new Dimension2D(newWidth, this.dimensions.getHeight());
        translateSnake(translationVector);
        translateFoods(translationVector);
    }

    /**
     * Translates the snake by the given {@link Vector2D}.
     * *
     * More precisely, deletes the old snake, and creates a new one with the translated points of the old snake.
     * If at any point a translated segment falls outside the game's dimensions, it will cut off the snake there.
     * @param vector The translation vector. Cannot be null.
     */
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

    /**
     * Translates the foods by the given {@link Vector2D}.
     * *
     * More precisely, deletes the old foods, and creates new ones with the translation vector.
     * If at any point a piece of food falls outside the game's dimensions, it will respawn a new one and delete the old one.
     * @param vector The vector. Cannot be null.
     */
    private void translateFoods(Vector2D vector){
        List<GamePoint> oldFoods = foodManager.getFoods();
        foodManager.clearFoods();
        for(GamePoint p : oldFoods){
            GamePoint gamePoint = p.translate(vector);
            if(!isOutOfBounds(gamePoint))
                foodManager.add(gamePoint);
        }
    }

    /**
     * Changes the direction of the snake to the given {@link Direction}.
     * @param direction The direction. Cannot be null.
     */
    public void changeSnakeDirection(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        snake.changeDirection(direction);
    }

    /**
     * Spawns a new food, if there is space for it.
     */
    public void spawnFood(){
        boolean entireBoardFilled = snake.getLength() + foodManager.getFoodCount() == dimensions.getHeight() * dimensions.getWidth();
        if(!entireBoardFilled)
            foodManager.add(generateRandomEmptyPoint());
    }

    /**
     * @return The {@link Direction} the snake is moving in.
     */
    public Direction getSnakeDirection(){
        return snake.getDirection();
    }

    /**
     * @return A deep copy of this {@link GameBoard}.
     */
    public IGameBoard copy(){
        return new GameBoard(dimensions, snake.copy(), foodManager.copy(), score);
    }

    /**
     * @return True if the two objects are equal. False otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof GameBoard gameBoard))
            return false;
        return dimensions.equals(gameBoard.dimensions) &&
                snake.equals(gameBoard.snake) &&
                foodManager.equals(gameBoard.foodManager) &&
                score == gameBoard.score;
    }

    /**
     * @return The hash code of this {@link GameBoard}.
     */
    @Override
    public int hashCode(){
        int result = dimensions.hashCode();
        result = result * 31 + snake.hashCode();
        result = result * 31 + foodManager.hashCode();
        result = result * 31 + score;
        return result;
    }

    /**
     * @return The string representation of this {@link GameBoard}.
     */
    @Override
    public String toString(){
        return String.format("GameBoard[dimensions=%s, snake=%s, foodManager=%s, score=%d]",
                dimensions, snake, foodManager, score);
    }
}
