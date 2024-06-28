package com.textr.filebuffer;

import com.textr.filebufferV2.IText;
import com.textr.filebufferV2.LineText;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CursorTest {

    private final IText text = new LineText("te\ntes\nte");
    private Cursor cursor;

    @BeforeEach
    public void initialise(){
        cursor = Cursor.createNew();
    }

    @Test
    public void testGetInsertIndex(){
        assertEquals(cursor.getInsertIndex(), 0);
    }

    @Test
    public void testGetInsertPoint(){
        assertEquals(cursor.getInsertPoint(), Point.create(0,0));
    }

    @Test
    public void testSetInsertIndex(){
        cursor.setInsertIndex(7, text);
        assertEquals(cursor.getInsertIndex(), 7);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 2));
        cursor.setInsertIndex(8, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.setInsertIndex(4, text);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
    }

    @Test
    public void testSetInsertIndexIllegal(){
        assertThrows(IndexOutOfBoundsException.class, () -> cursor.setInsertIndex(-1, text));
        assertThrows(IndexOutOfBoundsException.class, () -> cursor.setInsertIndex(12, text));
    }

    @Test
    public void testMoveCursorRight(){
        cursor.move(Direction.RIGHT, text);
        assertEquals(cursor.getInsertIndex(), 1);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 0));
        cursor.move(Direction.RIGHT, text);
        cursor.move(Direction.RIGHT, text);
        assertEquals(cursor.getInsertIndex(), 3);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 1));
        cursor.move(Direction.RIGHT, text);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
        cursor.move(Direction.RIGHT, text);
        cursor.move(Direction.RIGHT, text);
        cursor.move(Direction.RIGHT, text);
        cursor.move(Direction.RIGHT, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.move(Direction.RIGHT, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
    }

    @Test
    public void testMoveCursorLeft(){
        cursor.setInsertIndex(8, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 7);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 2));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 6);
        assertEquals(cursor.getInsertPoint(), Point.create(3, 1));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 5);
        assertEquals(cursor.getInsertPoint(), Point.create(2, 1));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 3);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 1));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 2);
        assertEquals(cursor.getInsertPoint(), Point.create(2, 0));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 1);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 0));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 0);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 0));
        cursor.move(Direction.LEFT, text);
        assertEquals(cursor.getInsertIndex(), 0);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 0));
    }

    @Test
    public void testMoveCursorDown(){
        cursor.setInsertIndex(2, text);
        cursor.move(Direction.DOWN, text);
        assertEquals(cursor.getInsertIndex(), 5);
        assertEquals(cursor.getInsertPoint(), Point.create(2, 1));
        cursor.move(Direction.DOWN, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.move(Direction.DOWN, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
    }

    @Test
    public void testMoveCursorUp(){
        cursor.setInsertIndex(8, text);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.move(Direction.UP, text);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
        cursor.move(Direction.UP, text);
        assertEquals(cursor.getInsertIndex(), 1);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 0));
        cursor.move(Direction.UP, text);
        assertEquals(cursor.getInsertIndex(), 1);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 0));
        cursor.setInsertIndex(6, text);
        cursor.move(Direction.UP, text);
        assertEquals(cursor.getInsertIndex(), 2);
        assertEquals(cursor.getInsertPoint(), Point.create(2, 0));
    }
}
