package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CursorTest {

    private ITextSkeleton skeleton;
    private Cursor cursor;

    @BeforeEach
    public void initialise(){
        skeleton = new TextSkeleton(new ArrayList<>(Arrays.asList(3,4,2)));
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
        cursor.setInsertIndex(7, skeleton);
        assertEquals(cursor.getInsertIndex(), 7);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 2));
        cursor.setInsertIndex(8, skeleton);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.setInsertIndex(4, skeleton);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
    }

    @Test
    public void testSetInsertIndexIllegal(){
        assertThrows(IllegalArgumentException.class, () -> cursor.setInsertIndex(-1, skeleton));
        assertThrows(IllegalArgumentException.class, () -> cursor.setInsertIndex(12, skeleton));
    }

    @Test
    public void testMoveCursorRight(){
        cursor.move(Direction.RIGHT, skeleton);
        assertEquals(cursor.getInsertIndex(), 1);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 0));
        cursor.move(Direction.RIGHT, skeleton);
        cursor.move(Direction.RIGHT, skeleton);
        assertEquals(cursor.getInsertIndex(), 3);
        assertEquals(cursor.getInsertPoint(), Point.create(0, 1));
        cursor.move(Direction.RIGHT, skeleton);
        assertEquals(cursor.getInsertIndex(), 4);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 1));
        cursor.move(Direction.RIGHT, skeleton);
        cursor.move(Direction.RIGHT, skeleton);
        cursor.move(Direction.RIGHT, skeleton);
        cursor.move(Direction.RIGHT, skeleton);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
        cursor.move(Direction.RIGHT, skeleton);
        assertEquals(cursor.getInsertIndex(), 8);
        assertEquals(cursor.getInsertPoint(), Point.create(1, 2));
    }
}
