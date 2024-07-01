package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeGameTest {

    private IClock clock;
    private Dimension2D dimensions;
    private Snake snake;
    private FoodManager foodManager;
    private IGameBoard board;
    private SnakeGame snakeGame;

    @BeforeEach
    public void initialise(){
        clock = new GameClock(1.0f);

        dimensions = new Dimension2D(2, 2);
        snake = new Snake(Direction.RIGHT);
        snake.add(new GamePoint(0, 0));
        snake.add(new GamePoint(0, 1));

        foodManager = new FoodManager();
        foodManager.add(new GamePoint(1, 0));

        board = GameBoard.createNew(dimensions, snake, foodManager);

        snakeGame = new SnakeGame(board, clock);
    }

    @Test
    public void testConstructorWithNull(){
        assertThrows(NullPointerException.class, () -> new SnakeGame(null, clock));
        assertThrows(NullPointerException.class, () -> new SnakeGame(board, null));
    }

    @Test
    public void testGetBoard(){
        assertEquals(snakeGame.getBoard(), board);
    }

    @Test
    public void testIsActive(){
        assertFalse(snakeGame.isRunning());
        snakeGame.start();
        assertTrue(snakeGame.isRunning());
        snakeGame.pause();
        assertFalse(snakeGame.isRunning());
    }


    @Test
    public void testChangeSnakeDirection(){
        snakeGame.changeSnakeDirection(Direction.RIGHT);
        // cannot test this further, the method just passes the parameter.
    }

    @Test
    public void testChangeSnakeDirectionWithNull(){
        assertThrows(NullPointerException.class, () -> snakeGame.changeSnakeDirection(null));
    }

    @Test
    public void testResizeBoard(){
        snakeGame.resizeBoard(new Dimension2D(2, 2));
        // cannot test this further, the method just passes the parameter.
    }

    @Test
    public void testResizeBoardWithNull(){
        assertThrows(NullPointerException.class, () -> snakeGame.resizeBoard(null));
    }

    @Test
    public void testUpdateWithNegativeValue(){
        assertThrows(IllegalArgumentException.class, () -> snakeGame.update(-1));
    }
    @Test
    public void testUpdateClockNotRunning(){
        assertFalse(snakeGame.update(1000));
    }

    @Test
    public void testUpdate(){
        snakeGame.start();
        assertFalse(snakeGame.update(750));
        assertTrue(snakeGame.update(1000));
        assertTrue(snakeGame.update(300));
        assertFalse(snakeGame.isRunning());
    }

}
