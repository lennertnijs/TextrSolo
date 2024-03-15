package com.Textr.Util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointTest {

    private Point point1;
    private Point point2;
    private Point point3;

    @BeforeEach
    public void initialise(){
        point1 = Point.create(1,2);
        point2 = Point.create(5,5);
        point3 = Point.create(1,2);
    }

    @Test
    public void testGetters(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1.getX(), 1),
                () -> Assertions.assertEquals(point1.getY(), 2)
        );
    }

    @Test
    public void testIllegalCreation(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point.create(-1, 1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point.create(1, -1))
        );
    }

    @Test
    public void testSetters(){
        point1.setX(5);
        point1.setY(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1, Point.create(5, 7))
        );
    }

    @Test
    public void testIllegalSetters(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> point1.setX(-1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> point1.setY(-1))
        );
    }

    @Test
    public void testIncrements(){
        point1.incrementX();
        point1.incrementY();
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1, Point.create(2, 3))
        );
    }

    @Test
    public void testDecrements(){
        point1.decrementX();
        point1.decrementX();
        point1.decrementY();
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1, Point.create(0, 1))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1, point1),
                () -> Assertions.assertEquals(point1, point3),
                () -> Assertions.assertNotEquals(point1, point2),
                () -> Assertions.assertNotEquals(point1, new Object()),
                () -> Assertions.assertEquals(point1.hashCode(), point1.hashCode()),
                () -> Assertions.assertEquals(point1.hashCode(), point3.hashCode()),
                () -> Assertions.assertNotEquals(point1.hashCode(), point2.hashCode())
        );
    }

    @Test
    public void testToString(){
        String expected = "Point[x = 1, y = 2]";
        Assertions.assertEquals(expected, point1.toString());
    }
}
