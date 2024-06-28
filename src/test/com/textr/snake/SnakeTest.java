package com.textr.snake;

import com.textr.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    private GamePoint point1;
    private GamePoint point2;
    private GamePoint point3;
    private GamePoint duplicatePoint;
    private Snake snake;
    private Snake snake2;
    private Snake snake3;
    @BeforeEach
    public void initialise(){
        point1 = new GamePoint(0,0);
        point2 = new GamePoint(1, 0);
        point3 = new GamePoint(2, 0);
        duplicatePoint = new GamePoint(0, 0);
        snake = new Snake(Direction.UP);
        snake2 = new Snake(Direction.RIGHT);
        snake3 = new Snake(Direction.UP);
    }

    @Test
    public void testCreateSnakeWithNull(){
        assertThrows(NullPointerException.class, () -> new Snake(null));
    }

    @Test
    public void testGetSnake(){
        assertEquals(snake.getBody(), new ArrayList<>());
    }

    @Test
    public void testAdd(){
        snake.add(point1);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(point1)));

        snake.add(point2);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(point1, point2)));

        snake.add(point3);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(point1, point2, point3)));

        assertThrows(IllegalStateException.class, () -> snake.add(duplicatePoint));
    }

    @Test
    public void testAddNotNextTo(){
        snake.add(point1);

        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(1,1)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(-1,1)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(-1,-1)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(1,-1)));

        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(2,0)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(0,2)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(-2,0)));
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(0,-2)));
    }

    @Test
    public void testAddIsNextHeadPosition(){
        snake.add(point1);
        snake.changeDirection(Direction.UP);
        assertThrows(IllegalStateException.class, () -> snake.add(new GamePoint(0, 1)));
    }

    @Test
    public void testGetLength(){
        assertEquals(snake.getLength(), 0);

        snake.add(point1);
        assertEquals(snake.getLength(), 1);

        snake.add(point2);
        assertEquals(snake.getLength(), 2);

        snake.add(point3);
        assertEquals(snake.getLength(), 3);
    }

    @Test
    public void testIsSnake(){
        snake.add(point1);
        assertTrue(snake.isSnake(point1));
        assertFalse(snake.isSnake(point2));
        assertTrue(snake.isSnake(duplicatePoint));
    }

    @Test
    public void testGetHead(){
        snake.add(point1);
        assertEquals(snake.getHead(), point1);
        snake.add(point2);
        assertEquals(snake.getHead(), point1);
    }

    @Test
    public void testGetHeadEmptySnake(){
        assertThrows(NoSuchElementException.class, () -> snake.getHead());
    }

    @Test
    public void testGetTail(){
        snake.add(point1);
        assertEquals(snake.getTail(), point1);
        snake.add(point2);
        assertEquals(snake.getTail(), point2);
    }

    @Test
    public void testGetTailEmptySnake(){
        assertThrows(NoSuchElementException.class, () -> snake.getTail());
    }

    @Test
    public void testClear(){
        snake.add(point1);
        snake.add(point2);
        snake.add(point3);
        snake.clear();
        assertEquals(snake.getBody(), new ArrayList<>());
    }

    @Test
    public void testGetDirection(){
        assertEquals(snake.getDirection(), Direction.UP);
    }

    @Test
    public void testChangeDirection(){
        snake.changeDirection(Direction.UP);
        assertEquals(snake.getDirection(), Direction.UP);

        snake.changeDirection(Direction.RIGHT);
        assertEquals(snake.getDirection(), Direction.RIGHT);

        snake.changeDirection(Direction.DOWN);
        assertEquals(snake.getDirection(), Direction.DOWN);

        snake.changeDirection(Direction.LEFT);
        assertEquals(snake.getDirection(), Direction.LEFT);
    }

    @Test
    public void testChangeDirectionWithBody(){
        snake.add(point1);
        snake.add(point2);

        snake.changeDirection(Direction.UP);
        assertEquals(snake.getDirection(), Direction.UP);

        snake.changeDirection(Direction.DOWN);
        assertEquals(snake.getDirection(), Direction.DOWN);

        snake.changeDirection(Direction.LEFT);
        assertEquals(snake.getDirection(), Direction.LEFT);

        // should not happen -> move into its own second segment
        snake.changeDirection(Direction.RIGHT);
        assertEquals(snake.getDirection(), Direction.LEFT);
    }

    /**
     * This test checks that changing the direction into itself, just not the second segment, is allowed.
     */
    @Test
    public void testChangeDirectionIntoSelf(){
        snake.changeDirection(Direction.DOWN);
        snake.add(point1);
        snake.add(point2);
        snake.add(new GamePoint(1, 1));
        snake.add(new GamePoint(0, 1));
        // should be allowed
        snake.changeDirection(Direction.UP);
        assertEquals(snake.getDirection(), Direction.UP);
    }

    @Test
    public void testChangeDirectionWithNull(){
        assertThrows(NullPointerException.class, () -> snake.changeDirection(null));
    }

    @Test
    public void testGetNextHeadPositionUP(){
        snake.add(point1);
        snake.add(point2);
        snake.add(point3);
        assertEquals(snake.getNextHeadPosition(), new GamePoint(0, 1));
        assertEquals(snake.getNextHeadPosition(), new GamePoint(0, 1));
    }

    @Test
    public void testGetNextHeadPositionRIGHT(){
        snake.add(point1);
        snake.changeDirection(Direction.RIGHT);
        assertEquals(snake.getNextHeadPosition(), new GamePoint(1, 0));
    }

    @Test
    public void testGetNextHeadPositionDOWN(){
        snake.add(point1);
        snake.add(point2);
        snake.add(point3);
        snake.changeDirection(Direction.DOWN);
        assertEquals(snake.getNextHeadPosition(), new GamePoint(0, -1));
    }

    @Test
    public void testGetNextHeadPositionLEFT(){
        snake.add(point1);
        snake.add(point2);
        snake.add(point3);
        snake.changeDirection(Direction.LEFT);
        assertEquals(snake.getNextHeadPosition(), new GamePoint(-1, 0));
    }

    @Test
    public void testMove(){
        snake.add(point1);
        snake.add(point2);
        snake.add(point3);

        snake.move(false);
        GamePoint head1 = new GamePoint(0, 1);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(head1, point1, point2)));

        snake.changeDirection(Direction.RIGHT);
        snake.move(false);
        GamePoint head2 = new GamePoint(1, 1);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(head2, head1, point1)));

        snake.move(false);
        GamePoint head3 = new GamePoint(2, 1);
        assertEquals(snake.getBody(), new ArrayList<>(List.of(head3, head2, head1)));
    }

    @Test
    public void testEquals(){
        snake.add(point1);
        snake3.add(point1);
        assertEquals(snake, snake3);
        assertNotEquals(snake, snake2);
        assertNotEquals(snake, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(snake.hashCode(), snake3.hashCode());
        assertNotEquals(snake.hashCode(), snake2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "Snake[body=[], headDirection=UP, toGrow=0]";
        assertEquals(snake.toString(), expectedString);
    }
}
