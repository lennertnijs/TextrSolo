package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.snake.SnakeGameInitializer;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class SnakeViewTest {

    private final Point position = new Point(5, 15);
    private final Dimension2D dimensions = new Dimension2D(100, 50);
    private final SnakeGameInitializer initializer = new SnakeGameInitializer(6, 12);
    private SnakeView snakeView;

    @BeforeEach
    public void initialise(){
        snakeView = new SnakeView(position, dimensions, initializer);
    }

    @Test
    public void testGetPosition(){
        assertEquals(position, snakeView.getPosition());
    }

    @Test
    public void testGetDimensions(){
        assertEquals(dimensions, snakeView.getDimensions());
    }

    @Test
    public void testSetPosition(){
        snakeView.setPosition(new Point(500, 500));
        assertEquals(new Point(500, 500), snakeView.getPosition());
    }

    @Test
    public void testSetPositionToNull(){
        assertThrows(NullPointerException.class,
                () -> snakeView.setPosition(null));
    }

    @Test
    public void testSetDimensions(){
        snakeView.setDimensions(new Dimension2D(500, 500));
        assertEquals(new Dimension2D(500, 500), snakeView.getDimensions());
    }

    @Test
    public void testSetDimensionsToNull(){
        assertThrows(NullPointerException.class,
                () -> snakeView.setDimensions(null));
    }

    @Test
    public void testCanBeClosed(){
        assertTrue(snakeView.canClose());
    }

    @Test
    public void testDuplicate(){
        assertThrows(UnsupportedOperationException.class,
                () -> snakeView.duplicate());
    }

    @Test
    public void testGameIsRunning(){
        assertTrue(snakeView.gameIsRunning());
    }

    @Test
    public void testGetGameBoard(){
        assertNotNull(snakeView.getGameBoard());
    }


    @Test
    public void testRestartGame(){
        snakeView.getGameBoard().changeSnakeDirection(Direction.DOWN);
        snakeView.restartGame();
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.RIGHT);
    }

    @Test
    public void testPrepareToClose(){
        // does nothing
        snakeView.prepareToClose();
    }

    @Test
    public void testHandleInputENTER(){
        Input input = Input.createSpecialInput(InputType.ENTER);
        snakeView.handleInput(input);
        assertTrue(snakeView.gameIsRunning());
    }

    @Test
    public void testHandleInputKeyUp(){
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.RIGHT);

        Input input = Input.createSpecialInput(InputType.ARROW_UP);
        snakeView.handleInput(input);
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.UP);
    }

    @Test
    public void testHandleInputKeyRight(){
        Input input = Input.createSpecialInput(InputType.ARROW_UP);
        snakeView.handleInput(input);
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.UP);

        Input input2 = Input.createSpecialInput(InputType.ARROW_RIGHT);
        snakeView.handleInput(input2);
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.RIGHT);
    }

    @Test
    public void testHandleInputKeyDown(){
        Input input = Input.createSpecialInput(InputType.ARROW_DOWN);
        snakeView.handleInput(input);
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.DOWN);
    }

    @Test
    public void testHandleInputKeyLeft(){ // does nothing!
        Input input = Input.createSpecialInput(InputType.ARROW_LEFT);
        snakeView.handleInput(input);
        assertEquals(snakeView.getGameBoard().getSnakeDirection(), Direction.RIGHT); // does not change, not possible
    }

    @Test
    public void testHandleInputTICK(){
        assertFalse(snakeView.handleInput(Input.createSpecialInput(InputType.TICK)));
    }

    @Test
    public void testHandleInputDEFAULT(){
        assertFalse(snakeView.handleInput(Input.createSpecialInput(InputType.ESCAPE)));
    }

    @Test
    public void testGenerateStatusBar(){
        String statusBar = "Score: 0";
        assertEquals(statusBar, snakeView.getStatusBar());
    }
}
