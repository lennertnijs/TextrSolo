package com.textr.filebuffer;

import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LineTextTest {

    private LineText text1;
    @BeforeEach
    public void initialise(){
        text1 = LineText.createFromString("ABC\nDEF\nG");
    }

    @Test
    public void testConvert1DTo2D(){
        assertEquals(text1.translate1DCursorTo2D(0), Point.create(0, 0));
        assertEquals(text1.translate1DCursorTo2D(1), Point.create(0, 1));
        assertEquals(text1.translate1DCursorTo2D(2), Point.create(0, 2));
        assertEquals(text1.translate1DCursorTo2D(3), Point.create(0, 3));
        assertEquals(text1.translate1DCursorTo2D(4), Point.create(1, 0));
        assertEquals(text1.translate1DCursorTo2D(5), Point.create(1, 1));
        assertEquals(text1.translate1DCursorTo2D(6), Point.create(1, 2));
        assertEquals(text1.translate1DCursorTo2D(7), Point.create(1, 3));
        assertEquals(text1.translate1DCursorTo2D(8), Point.create(2, 0));
        assertEquals(text1.translate1DCursorTo2D(9), Point.create(2, 1));
        assertThrows(IllegalArgumentException.class, () -> text1.translate1DCursorTo2D(10));
    }

    @Test
    public void testConvert2DTo1D(){
        assertEquals(text1.translate2DCursorTo1D(Point.create(0, 0)), 0);
        assertEquals(text1.translate2DCursorTo1D(Point.create(0, 1)), 1);
        assertEquals(text1.translate2DCursorTo1D(Point.create(0, 2)), 2);
        assertEquals(text1.translate2DCursorTo1D(Point.create(0, 3)), 3);
        assertEquals(text1.translate2DCursorTo1D(Point.create(1, 0)), 4);
        assertEquals(text1.translate2DCursorTo1D(Point.create(1, 1)), 5);
        assertEquals(text1.translate2DCursorTo1D(Point.create(1, 2)), 6);
        assertEquals(text1.translate2DCursorTo1D(Point.create(1, 3)), 7);
        assertEquals(text1.translate2DCursorTo1D(Point.create(2, 0)), 8);
        assertEquals(text1.translate2DCursorTo1D(Point.create(2, 1)), 9);
        assertThrows(NullPointerException.class, () -> text1.translate2DCursorTo1D(null));
        assertThrows(IllegalArgumentException.class, () -> text1.translate2DCursorTo1D(Point.create(0, 4)));
        assertThrows(IllegalArgumentException.class, () -> text1.translate2DCursorTo1D(Point.create(3, 0)));
    }
}
