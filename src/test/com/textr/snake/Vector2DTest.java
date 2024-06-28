package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Vector2DTest {

    private Vector2D vector1;
    private Vector2D vector2;
    private Vector2D vector3;
    @BeforeEach
    public void initialise(){
        vector1 = new Vector2D(1, 2);
        vector2 = new Vector2D(-5, -10);
        vector3 = new Vector2D(1, 2);
    }

    @Test
    public void testGetX(){
        assertEquals(vector1.x(), 1);
        assertEquals(vector2.x(), -5);
        assertEquals(vector3.x(), 1);
    }

    @Test
    public void testGetY(){
        assertEquals(vector1.y(), 2);
        assertEquals(vector2.y(), -10);
        assertEquals(vector3.y(), 2);
    }

    @Test
    public void testEquals(){
        assertEquals(vector1, vector3);
        assertNotEquals(vector1, vector2);
        assertNotEquals(vector1, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(vector1.hashCode(), vector3.hashCode());
        assertNotEquals(vector1.hashCode(), vector2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "Vector2D[x=1, y=2]";
        assertEquals(vector1.toString(), expectedString);
    }
}
