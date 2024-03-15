package com.Textr.Util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Dimension2DTest {

    private Dimension2D dimensions1;
    private Dimension2D dimensions2;
    private Dimension2D dimensions3;

    @BeforeEach
    public void initialise(){
        dimensions1 = Dimension2D.create(15, 10);
        dimensions2 = Dimension2D.create(10, 15);
        dimensions3 = Dimension2D.create(15, 10);
    }


    @Test
    public void testConstructorAndGetters(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions1.getWidth(), 15),
                () -> Assertions.assertEquals(dimensions1.getHeight(), 10)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Dimension2D.create(0, 10)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Dimension2D.create(10, 0))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions1, dimensions3),
                () -> Assertions.assertEquals(dimensions1, dimensions1),
                () -> Assertions.assertNotEquals(dimensions1, dimensions2),
                () -> Assertions.assertNotEquals(dimensions1, new Object()),
                () -> Assertions.assertEquals(dimensions1.hashCode(), dimensions3.hashCode()),
                () -> Assertions.assertEquals(dimensions1.hashCode(), dimensions1.hashCode()),
                () -> Assertions.assertNotEquals(dimensions1.hashCode(), dimensions2.hashCode())
        );
    }

    @Test
    public void testToString(){
        String expected = "Dimension2D[width = 15, height = 10]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions1.toString(), expected)
        );
    }
}
