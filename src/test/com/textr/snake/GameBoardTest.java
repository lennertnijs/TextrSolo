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
    private GameBoard board2;
    private GameBoard board3;

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
        board2 = GameBoard.createNew(Dimension2D.create(200, 200), snake, foodManager);
        board3 = GameBoard.createNew(dimensions, snake, foodManager);
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
    public void testGetSnakeDirection(){
        assertEquals(board.getSnakeDirection(), Direction.UP);
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
    public void testCreateWithSnakePointsOutsideBoard(){
        Snake snake1 = new Snake(Direction.UP);
        snake1.add(new GamePoint(55, 55));
        assertThrows(IllegalArgumentException.class, () -> GameBoard.createNew(dimensions, snake1, foodManager));
    }

    @Test
    public void testCreateWithFoodOutsideBoard(){
        foodManager.add(new GamePoint(55, 55));
        assertThrows(IllegalArgumentException.class, () -> GameBoard.createNew(dimensions, snake, foodManager));
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
        board.changeSnakeDirection(Direction.LEFT);
        assertThrows(IllegalStateException.class, () -> board.moveSnake());
    }

    @Test
    public void testCutHeightSnakeBottom(){
        board.resize(Dimension2D.create(20, 5));
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }


    @Test
    public void testCutHeightSnakeTop(){
        for(int i = 0; i < 7; i++)
            board.moveSnake(); // sets snake head at (0,7)
        board.resize(Dimension2D.create(20, 5));
        GamePoint snakeHead = new GamePoint(0, 2);
        GamePoint snakeMiddle = new GamePoint(0, 1);
        GamePoint snakeTail = new GamePoint(0, 0);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testCutHeightSnakeMiddle(){
        for(int i = 0; i < 5; i++)
            board.moveSnake(); // sets snake head at (0,5)
        board.resize(Dimension2D.create(20, 5));
        GamePoint snakeHead = new GamePoint(0, 2);
        GamePoint snakeMiddle = new GamePoint(0, 1);
        GamePoint snakeTail = new GamePoint(0, 0);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testIncreaseHeightSlightly(){
        board.resize(Dimension2D.create(20, 12));
        GamePoint snakeHead = new GamePoint(0, 2);
        GamePoint snakeMiddle = new GamePoint(1, 2);
        GamePoint snakeTail = new GamePoint(2, 2);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testIncreaseHeightMassively(){
        board.resize(Dimension2D.create(20, 30));
        GamePoint snakeHead = new GamePoint(0, 15);
        GamePoint snakeMiddle = new GamePoint(1, 15);
        GamePoint snakeTail = new GamePoint(2, 15);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testCutWidthSnakeLeft(){
        board.resize(Dimension2D.create(10, 10));
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testCutWidthSnakeRight(){
        board.moveSnake();
        board.changeSnakeDirection(Direction.RIGHT);
        for(int i = 0; i < 15; i++)
            board.moveSnake(); // sets snake head at (15, 1)
        board.resize(Dimension2D.create(10, 10));
        GamePoint snakeHead = new GamePoint(5, 1);
        GamePoint snakeMiddle = new GamePoint(4, 1);
        GamePoint snakeTail = new GamePoint(3, 1);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testCutWidthSnakeMiddle(){
        board.moveSnake();
        board.changeSnakeDirection(Direction.RIGHT);
        for(int i = 0; i < 10; i++)
            board.moveSnake(); // sets snake head at (10, 1)
        board.resize(Dimension2D.create(10, 10));
        GamePoint snakeHead = new GamePoint(5, 1);
        GamePoint snakeMiddle = new GamePoint(4, 1);
        GamePoint snakeTail = new GamePoint(3, 1);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testIncreaseWidthSlightly(){
        board.resize(Dimension2D.create(22, 10));
        GamePoint snakeHead = new GamePoint(2, 0);
        GamePoint snakeMiddle = new GamePoint(3, 0);
        GamePoint snakeTail = new GamePoint(4, 0);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testIncreaseWidthMassively(){
        board.resize(Dimension2D.create(50, 10));
        GamePoint snakeHead = new GamePoint(25, 0);
        GamePoint snakeMiddle = new GamePoint(26, 0);
        GamePoint snakeTail = new GamePoint(27, 0);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void testResizeLosePartOfSnake(){
        board.resize(Dimension2D.create(2, 10));
        GamePoint snakeHead = new GamePoint(0, 0);
        GamePoint snakeMiddle = new GamePoint(1, 0);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle)));
    }

    @Test
    public void resizeBothDimensionsPartly(){
        board.resize(Dimension2D.create(30, 15));
        GamePoint snakeHead = new GamePoint(10, 5);
        GamePoint snakeMiddle = new GamePoint(11, 5);
        GamePoint snakeTail = new GamePoint(12, 5);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void resizeBothDimensionsImmensely(){
        board.resize(Dimension2D.create(70, 50));
        GamePoint snakeHead = new GamePoint(35, 25);
        GamePoint snakeMiddle = new GamePoint(36, 25);
        GamePoint snakeTail = new GamePoint(37, 25);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));
    }

    @Test
    public void resizeBothDimensionsExtra(){
        snake.move();
        snake.changeDirection(Direction.RIGHT);
        for(int i = 0; i < 13; i++)
            snake.move(); // head at (13, 1)
        board.resize(Dimension2D.create(10, 20)); // was (20, 10)
        GamePoint snakeHead = new GamePoint(5, 10);
        GamePoint snakeMiddle = new GamePoint(4, 10);
        GamePoint snakeTail = new GamePoint(3, 10);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead, snakeMiddle, snakeTail)));

        board.resize(Dimension2D.create(20, 10));
        GamePoint snakeHead2 = new GamePoint(10, 5);
        GamePoint snakeMiddle2 = new GamePoint(9, 5);
        GamePoint snakeTail2 = new GamePoint(8, 5);
        assertEquals(board.getSnakePoints(), new ArrayList<>(List.of(snakeHead2, snakeMiddle2, snakeTail2)));
    }

    @Test
    public void testEquals(){
        assertEquals(board, board3);
        assertNotEquals(board, board2);
        assertNotEquals(board, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(board.hashCode(), board3.hashCode());
        assertNotEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "GameBoard[dimensions=Dimension2D[width = 20, height = 10], snake=Snake[body=[GamePoint[x=0, y=0], GamePoint[x=1, y=0], GamePoint[x=2, y=0]], headDirection=UP, atePoints=[]], foodManager=FoodManager[foods=[GamePoint[x=0, y=2]]], score=0]";
        assertEquals(board.toString(), expectedString);
    }
}
