package com.textr.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Dimension2DTest {

    private final Dimension2D dimensions = new Dimension2D(5, 15);

    @Test
    public void testConstructorWithNegativeWidth(){
        assertThrows(IllegalArgumentException.class,
                () -> new Dimension2D(-1, 15));
    }

    @Test
    public void testConstructorWithZeroWidth(){
        assertThrows(IllegalArgumentException.class,
                () -> new Dimension2D(0, 15));
    }

    @Test
    public void testConstructorWithNegativeHeight(){
        assertThrows(IllegalArgumentException.class,
                () -> new Dimension2D(5, -1));
    }

    @Test
    public void testConstructorWithZeroHeight(){
        assertThrows(IllegalArgumentException.class,
                () -> new Dimension2D(5, 0));
    }

    @Test
    public void testGetWidth(){
        assertEquals(5, dimensions.width());
    }

    @Test
    public void testGetHeight(){
        assertEquals(15, dimensions.height());
    }

    @Test
    public void testEquals(){
        Dimension2D dimensions1 = new Dimension2D(5, 15);
        Dimension2D dimensions2 = new Dimension2D(5, 15);
        Dimension2D dimensions3 = new Dimension2D(5, 15);
        // reflexive
        assertEquals(dimensions1, dimensions1);
        // symmetrical
        assertEquals(dimensions1, dimensions2);
        assertEquals(dimensions2, dimensions1);
        // transitive
        assertEquals(dimensions1, dimensions2);
        assertEquals(dimensions2, dimensions3);
        assertEquals(dimensions1, dimensions3);

        // not equals
        Dimension2D diffWidth = new Dimension2D(25, 15);
        Dimension2D diffHeight = new Dimension2D(5, 35);
        assertNotEquals(dimensions1, diffWidth);
        assertNotEquals(dimensions1, diffHeight);
        assertNotEquals(dimensions1, new Object());
        assertNotEquals(dimensions1, null);
    }

    @Test
    public void testHashCode(){
        Dimension2D dimensions1 = new Dimension2D(5, 15);
        Dimension2D dimensions2 = new Dimension2D(5, 15);
        Dimension2D dimensions3 = new Dimension2D(5, 15);
        // reflexive
        assertEquals(dimensions1.hashCode(), dimensions1.hashCode());
        // symmetrical
        assertEquals(dimensions1.hashCode(), dimensions2.hashCode());
        assertEquals(dimensions2.hashCode(), dimensions1.hashCode());
        // transitive
        assertEquals(dimensions1.hashCode(), dimensions2.hashCode());
        assertEquals(dimensions2.hashCode(), dimensions3.hashCode());
        assertEquals(dimensions1.hashCode(), dimensions3.hashCode());

        // not equals
        Dimension2D diffWidth = new Dimension2D(25, 15);
        Dimension2D diffHeight = new Dimension2D(5, 35);
        assertNotEquals(dimensions1.hashCode(), diffWidth.hashCode());
        assertNotEquals(dimensions1.hashCode(), diffHeight.hashCode());
    }

    @Test
    public void testToString(){
        String expected = "Dimension2D[width=5, height=15]";
        assertEquals(expected, dimensions.toString());
    }
}
