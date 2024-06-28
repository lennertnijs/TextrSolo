package com.textr.filebuffer;

import com.textr.filebufferV2.LineText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineTextTest {

    private LineText linetext;

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
    public void testGetContent(){
        assertEquals("Line 1\nLine 2\nLine 3\n", linetext.getContent());
    }

    @Test
    public void testGetLines(){
        assertArrayEquals(new String[]{"Line 1", "Line 2", "Line 3", ""}, linetext.getLines());
    }

    @Test
    public void testGetAmountOfLines(){
        assertEquals(4, linetext.getLineAmount());
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
        linetext.insert(0, 's');
        assertEquals(linetext.getContent(), "sLine 1\nLine 2\nLine 3\n");
        linetext.insert(1, 'b');
        assertEquals(linetext.getContent(), "sbLine 1\nLine 2\nLine 3\n");
        linetext.insert(8, 'y');
        assertEquals(linetext.getContent(), "sbLine 1y\nLine 2\nLine 3\n");
        linetext.insert(10, 'p');
        assertEquals(linetext.getContent(), "sbLine 1y\npLine 2\nLine 3\n");
        linetext.insert(25, 'l');
        assertEquals(linetext.getContent(), "sbLine 1y\npLine 2\nLine 3\nl");
    }

    @Test
    public void testInsertWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.insert(-1, 's'));
    }

    @Test
    public void testInsertWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.insert(22, 's'));
    }

    @Test
    public void testRemove(){
        linetext.remove(0);
        assertEquals("ine 1\nLine 2\nLine 3\n", linetext.getContent());
        linetext.remove(5);
        assertEquals("ine 1Line 2\nLine 3\n", linetext.getContent());
        linetext.remove(18);
        assertEquals("ine 1Line 2\nLine 3", linetext.getContent());
    }

    @Test
    public void testRemoveWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.remove(-1));
    }

    @Test
    public void testRemoveWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> linetext.remove(21));
    }

    @Test
    public void testToString(){
        String expected = """
                LineText[content=Line 1
                Line 2
                Line 3
                ]""";
        assertEquals(expected, linetext.toString());
    }
}
