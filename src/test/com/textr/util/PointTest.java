package com.textr.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    private Point point;

    @BeforeEach
    public void initialise(){
        point = new Point(5,15);
    }

    @Test
    public void testConstructorWithNegativeX(){
        assertThrows(IllegalArgumentException.class,
                () -> new Point(-1, 15));
    }

    @Test
    public void testConstructorWithZeroX(){ // allowed
        new Point(0, 15);
    }

    @Test
    public void testConstructorWithNegativeY(){
        assertThrows(IllegalArgumentException.class,
                () -> new Point(5, -1));
    }

    @Test
    public void testConstructorWithZeroY(){ // allowed
        new Point(5, 0);
    }

    @Test
    public void testGetX(){
        assertEquals(5, point.getX());
    }

    @Test
    public void testGetY(){
        assertEquals(15, point.getY());
    }

    @Test
    public void testSetX(){
        point.setX(25);
        assertEquals(25, point.getX());
    }

    @Test
    public void testSetXToNegative(){
        assertThrows(IllegalArgumentException.class,
                () -> point.setX(-1));
    }


    @Test
    public void testSetXToZero(){
        point.setX(0); // allowed
    }

    @Test
    public void testSetYToNegative(){
        assertThrows(IllegalArgumentException.class,
                () -> point.setY(-1));
    }

    @Test
    public void testSetYToZero(){
        point.setY(0); // allowed
    }

    @Test
    public void testCopy(){
        Point copy = point.copy();
        assertEquals(point, copy);

        copy.setX(500);
        assertNotEquals(point, copy);
    }

    @Test
    public void testEquals(){
        Point point1 = new Point(5, 15);
        Point point2 = new Point(5, 15);
        Point point3 = new Point(5, 15);
        // reflexive
        assertEquals(point1, point1);
        // symmetrical
        assertEquals(point1, point2);
        assertEquals(point2, point1);
        // transitive
        assertEquals(point1, point2);
        assertEquals(point2, point3);
        assertEquals(point1, point3);

        // not equals
        Point diffX = new Point(25, 15);
        Point diffY = new Point(5, 35);
        assertNotEquals(point1, diffX);
        assertNotEquals(point1, diffY);
        assertNotEquals(point1, new Object());
        assertNotEquals(point1, null);
    }

    @Test
    public void testHashCode(){
        Point point1 = new Point(5, 15);
        Point point2 = new Point(5, 15);
        Point point3 = new Point(5, 15);
        // reflexive
        assertEquals(point1.hashCode(), point1.hashCode());
        // symmetrical
        assertEquals(point1.hashCode(), point2.hashCode());
        assertEquals(point2.hashCode(), point1.hashCode());
        // transitive
        assertEquals(point1.hashCode(), point2.hashCode());
        assertEquals(point2.hashCode(), point3.hashCode());
        assertEquals(point1.hashCode(), point3.hashCode());

        // not equals
        Point diffX = new Point(25, 15);
        Point diffY = new Point(5, 35);
        assertNotEquals(point1.hashCode(), diffX.hashCode());
        assertNotEquals(point1.hashCode(), diffY.hashCode());
    }

    @Test
    public void testToString(){
        String expected = "(5, 15)";
        assertEquals(expected, point.toString());
    }
}
