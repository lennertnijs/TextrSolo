package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameBoardTest {

    private GamePoint snakeHead;
    private GamePoint snakeMiddle;
    private GamePoint snakeTail;
    private GamePoint food;
    private Dimension2D dimensions;
    private Snake snake;
    private FoodManager foodManager;
    private GameBoard board;

    @BeforeEach
    public void initialise(){
        snakeHead = new GamePoint(0, 0);
        snakeMiddle = new GamePoint(1, 0);
        snakeTail = new GamePoint(2, 0);
        food = new GamePoint(0, 2);
        dimensions = Dimension2D.create(20, 10);
        snake = new Snake(Direction.UP);
        foodManager = new FoodManager();

        snake.add(snakeHead);
        snake.add(snakeMiddle);
        snake.add(snakeTail);
        foodManager.add(food);
        board = GameBoard.createNew(dimensions, snake, foodManager);
    }

    @Test
    public void testGetDimensions(){
        assertEquals(board.getDimensions(), dimensions);
    }

    @Test
    public void testGetSnakePoints(){
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testGetFoods(){
        assertEquals(board.getFoods(), new ArrayList<>(List.of(food)));
    }

    @Test
    public void testGetScore(){
        assertEquals(board.getScore(), 0);
    }

    @Test
    public void testCreateNewWithNull(){
        assertThrows(NullPointerException.class, () -> GameBoard.createNew(null, snake, foodManager));
        assertThrows(NullPointerException.class, () -> GameBoard.createNew(dimensions, null, foodManager));
        assertThrows(NullPointerException.class, () -> GameBoard.createNew(dimensions, snake, null));
    }

    @Test
    public void testMoveSnakeNoFood(){
        board.moveSnake();
        GamePoint newHead = new GamePoint(0, 1);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(newHead, snakeHead, snakeMiddle)));
        assertEquals(board.getFoods(), new ArrayList<>(List.of(food)));
    }

    @Test
    public void testMoveSnakeEatsFood(){
        board.moveSnake();
        board.moveSnake();
        GamePoint newHead = new GamePoint(0, 2);
        GamePoint newMiddle = new GamePoint(0, 1);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(newHead, newMiddle, snakeHead)));
        assertEquals(board.getFoods().size(), 1);
        assertEquals(board.getScore(), 1);
        assertTrue(board.getFoods().get(0).x() >= 0);
        assertTrue(board.getFoods().get(0).y() >= 0);
        assertTrue(board.getFoods().get(0).x() < board.getDimensions().getWidth());
        assertTrue(board.getFoods().get(0).y() < board.getDimensions().getHeight());
    }

    @Test
    public void testMoveSnakeOutsideDimensions(){
        board.changeDirection(Direction.LEFT);
        assertThrows(IllegalStateException.class, () -> board.moveSnake());
    }
}
