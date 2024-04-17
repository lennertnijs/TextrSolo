package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamePointTest {


    private GamePoint point1;
    private GamePoint point2;
    private GamePoint point3;
    @BeforeEach
    public void initialise(){
        point1 = new GamePoint(1,2);
        point2 = new GamePoint(-5, -4);
        point3 = new GamePoint(1, 2);
    }

    @Test
    public void testGetX(){
        assertEquals(point1.x(), 1);
        assertEquals(point2.x(), -5);
        assertEquals(point3.x(), 1);
    }

    @Test
    public void testGetY(){
        assertEquals(point1.y(), 2);
        assertEquals(point2.y(), -4);
        assertEquals(point3.y(), 2);
    }

    @Test
    public void testTranslate(){
        Vector v1 = new Vector(15, 10);
        Vector v2 = new Vector(-5, -10);
        assertEquals(point1.translate(v1), new GamePoint(1 + 15, 2 + 10));
        assertEquals(point1.translate(v2), new GamePoint(1 - 5, 2 - 10));
    }

    @Test
    public void testTranslateWithNullVector(){
        assertThrows(NullPointerException.class, () -> point1.translate(null));
    }

    @Test
    public void testEquals(){
        assertEquals(point1, point3);
        assertNotEquals(point1, point2);
        assertNotEquals(point1, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(point1.hashCode(), point3.hashCode());
        assertNotEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "GamePoint[x=1, y=2]";
        assertEquals(point1.toString(), expectedString);
    }
}
