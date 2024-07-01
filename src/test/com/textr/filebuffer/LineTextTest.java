package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineTextTest {

    private IText linetext;

    @BeforeEach
    public void initialise(){
        String string = "Line 1\nLine 2\r\nLine 3\r";
        linetext = new LineText(string);
    }

    @Test
    public void testConstructorWithNullString(){
        assertThrows(NullPointerException.class,
                () -> new LineText(null));
    }

    @Test
    public void testGetInsertPoint(){
        assertEquals(new Point(0, 0), linetext.getInsertPoint(0));
        assertEquals(new Point(6, 0), linetext.getInsertPoint(6));
        assertEquals(new Point(1, 1), linetext.getInsertPoint(8));
        assertEquals(new Point(0, 3), linetext.getInsertPoint(21));
    }

    @Test
    public void testGetInsertPointWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getInsertPoint(-1));
    }

    @Test
    public void testGetInsertPointWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getInsertPoint(21 + 1));
    }

    @Test
    public void testGetContent(){
        assertEquals("Line 1\nLine 2\nLine 3\n", linetext.getContent());
    }

    @Test
    public void testGetLines(){
        assertArrayEquals(new String[]{"Line 1", "Line 2", "Line 3", ""}, linetext.getLines());
    }

    @Test
    public void testGetLineAmount(){
        assertEquals(4, linetext.getLineAmount());
    }

    @Test
    public void testGetLineLength(){ // does not count \n
        assertEquals(6, linetext.getLineLength(0));
        assertEquals(6, linetext.getLineLength(1));
        assertEquals(6, linetext.getLineLength(2));
        assertEquals(0, linetext.getLineLength(3));
    }

    @Test
    public void testGetLineLengthWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getLineLength(-1));
    }

    @Test
    public void testGetLineLengthWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getLineLength(4));
    }

    @Test
    public void testGetAmountOfCharacters(){
        assertEquals(linetext.getCharAmount(), 21);
    }

    @Test
    public void testGetCharacter(){
        assertEquals('L', linetext.getCharacter(0));
        assertEquals('\n', linetext.getCharacter(6));
        assertEquals('3', linetext.getCharacter(19));
        assertEquals('\n', linetext.getCharacter(20));
    }

    @Test
    public void testGetCharacterWithIndexNegative(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getCharacter(-1));
    }

    @Test
    public void testGetCharacterWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.getCharacter(20 + 1));
    }

    @Test
    public void testInsert(){
        linetext.insert('s', 0);
        assertEquals(linetext.getContent(), "sLine 1\nLine 2\nLine 3\n");
        linetext.insert('b', 1);
        assertEquals(linetext.getContent(), "sbLine 1\nLine 2\nLine 3\n");
        linetext.insert('y', 8);
        assertEquals(linetext.getContent(), "sbLine 1y\nLine 2\nLine 3\n");
        linetext.insert( 'p', 10);
        assertEquals(linetext.getContent(), "sbLine 1y\npLine 2\nLine 3\n");
        linetext.insert('l', 25);
        assertEquals(linetext.getContent(), "sbLine 1y\npLine 2\nLine 3\nl");
        linetext.insert('\n', 26);
        assertEquals(linetext.getContent(), "sbLine 1y\npLine 2\nLine 3\nl\n");
    }

    @Test
    public void testInsertWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.insert( 's', -1));
    }

    @Test
    public void testInsertWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.insert('s', 21 + 1));
    }

    @Test
    public void testRemove(){
        linetext.delete(0);
        assertEquals("ine 1\nLine 2\nLine 3\n", linetext.getContent());
        linetext.delete(5);
        assertEquals("ine 1Line 2\nLine 3\n", linetext.getContent());
        linetext.delete(18);
        assertEquals("ine 1Line 2\nLine 3", linetext.getContent());
    }

    @Test
    public void testRemoveWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.delete(-1));
    }

    @Test
    public void testRemoveWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.delete(21));
    }

    @Test
    public void testMoveRight(){
        for(int i = 0; i < 21; i++){
            assertEquals(i + 1, linetext.move(Direction.RIGHT, i));
        }
        // arrived at end -> don't move further
        assertEquals(21, linetext.move(Direction.RIGHT, 21));
    }

    @Test
    public void testMoveLeft(){
        for(int i = 21; i > 0; i--){
            assertEquals(i - 1, linetext.move(Direction.LEFT, i));
        }
        // arrived at the beginning -> don't move left more
        assertEquals(0, linetext.move(Direction.LEFT, 0));
    }

    @Test
    public void testMoveUpLineLengthsEqual(){
        LineText text = new LineText("Line 1\nLine 2\nLine 3");

        assertEquals(13, text.move(Direction.UP, 20));
        assertEquals(6, text.move(Direction.UP, 13));
        assertEquals(6, text.move(Direction.UP, 6));
    }

    @Test
    public void testMoveUpLineLengthsDontMatch(){
        LineText text = new LineText("Line 1\nL\nLine 3");

        assertEquals(8, text.move(Direction.UP, 15));
        assertEquals(1, text.move(Direction.UP, 8));
        assertEquals(1, text.move(Direction.UP, 1));
    }

    @Test
    public void testMoveDownLineLengthsMatch(){
        LineText text = new LineText("Line 1\nLine 2\nLine 3");

        assertEquals(7, text.move(Direction.DOWN, 0));
        assertEquals(14, text.move(Direction.DOWN, 7));
        assertEquals(14, text.move(Direction.DOWN, 14));
    }


    @Test
    public void testMoveDownLineLengthsDiffer(){
        LineText text = new LineText("Line 1\nL\nLine 3");


        assertEquals(8, text.move(Direction.DOWN, 6));
        assertEquals(10, text.move(Direction.DOWN, 8));
        assertEquals(10, text.move(Direction.DOWN, 10));
    }

    @Test
    public void testMoveWithNullDirection(){
        assertThrows(NullPointerException.class,
                () -> linetext.move(null, 0));
    }

    @Test
    public void testCopy(){
        IText copy = linetext.copy();
        assertEquals(linetext.getContent(), copy.getContent());
        copy.insert('c', 1);
        assertNotEquals(linetext.getContent(), copy.getContent());
    }

    @Test
    public void testToString(){
        String expected = """
                LineText[content={Line 1
                Line 2
                Line 3
                }]""";
        assertEquals(expected, linetext.toString());
    }
}
