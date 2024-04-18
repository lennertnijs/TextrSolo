package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineTextTest {

    private String string1;
    private String string2;
    private String string3;
    private LineText text1;
    private LineText text2;
    private LineText text3;
    private ICursor cursor;

    @BeforeEach
    public void initialise(){
        string1 = "Line 1\nLine 2\nLine 3";
        string2 = "Line 1\r\n";
        string3 = "Line 1\nLine 2\nLine 3";
        text1 = LineText.createFromString(string1);
        text2 = LineText.createFromString(string2);
        text3 = LineText.createFromString(string3);
        cursor = Cursor.createNew();
    }

    @Test
    public void testConstructor(){
        String string = "Line 1\n";
        assertEquals(LineText.createFromString(string2).getContent(), string);
    }

    @Test
    public void testGetContent(){
        assertEquals(text1.getContent(), string1);
    }

    @Test
    public void testGetLines(){
        assertArrayEquals(text1.getLines(), new String[]{"Line 1", "Line 2", "Line 3"});
        assertArrayEquals(text2.getLines(), new String[]{"Line 1", ""});
    }

    @Test
    public void testGetLine(){
        assertEquals(text1.getLine(0), "Line 1");
        assertEquals(text1.getLine(1), "Line 2");
        assertEquals(text2.getLine(1), "");
    }

    @Test
    public void testGetLineIllegal(){
        assertThrows(IndexOutOfBoundsException.class, () -> text1.getLine(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> text1.getLine(3));
    }

    @Test
    public void testGetAmountOfLines(){
        assertEquals(text1.getLineAmount(), 3);
        assertEquals(text2.getLineAmount(), 2);
    }

    @Test
    public void testGetAmountOfCharacters(){
        assertEquals(text1.getCharAmount(), 20);
        assertEquals(text2.getCharAmount(), 7);
    }

    @Test
    public void testGetCharacter(){
        assertEquals(text1.getCharacter(0), 'L');
        assertEquals(text1.getCharacter(19), '3');
        assertEquals(text1.getCharacter(6), '\n');
    }

    @Test
    public void testGetCharacterIllegal(){
        assertThrows(IllegalArgumentException.class, () -> text1.getCharacter(-1));
        assertThrows(IllegalArgumentException.class, () -> text1.getCharacter(20));
    }

    @Test
    public void testInsert(){
        text1.insert(0, 's');
        assertEquals(text1.getCharAmount(), 21);
        assertEquals(text1.getLine(0), "sLine 1");
        text1.insert(1, 'b');
        assertEquals(text1.getCharAmount(), 22);
        assertEquals(text1.getLine(0), "sbLine 1");
        text1.insert(8, 'y');
        assertEquals(text1.getCharAmount(), 23);
        assertEquals(text1.getLine(0), "sbLine 1y");
        text1.insert(10, 'p');
        assertEquals(text1.getCharAmount(), 24);
        assertEquals(text1.getLine(1), "pLine 2");
        text1.insert(24, 'l');
        assertEquals(text1.getCharAmount(), 25);
        assertEquals(text1.getLine(2), "Line 3l");
    }

    @Test
    public void testInsertIllegal(){
        assertThrows(IllegalArgumentException.class, () -> text1.insert(-1, 's'));
        assertThrows(IllegalArgumentException.class, () -> text1.insert(21, 's'));
    }

    @Test
    public void testRemove(){
        text1.remove(0);
        assertEquals(text1.getCharAmount(), 19);
        assertEquals(text1.getLine(0), "ine 1");
        text1.remove(0);
        assertEquals(text1.getCharAmount(), 18);
        assertEquals(text1.getLine(0), "ne 1");
    }

    @Test
    public void testRemoveLineBreak(){
        text1.remove(6);
        assertEquals(text1.getCharAmount(), 19);
        assertEquals(text1.getLine(0), "Line 1Line 2");
    }

    @Test
    public void testRemoveIllegal(){
        assertThrows(IllegalArgumentException.class, () -> text1.remove(-1));
        assertThrows(IllegalArgumentException.class, () -> text1.remove(20));
    }

    @Test
    public void insertLineBreak(){
        text1.insertLineBreak(2);
        assertEquals(text1.getLineAmount(), 4);
        assertEquals(text1.getCharAmount(), 21);
        assertEquals(text1.getLine(0), "Li");
        assertEquals(text1.getLine(1), "ne 1");
    }

    @Test
    public void insertLineBreakAtStart(){
        text1.insertLineBreak(0);
        assertEquals(text1.getLineAmount(), 4);
        assertEquals(text1.getCharAmount(), 21);
        assertEquals(text1.getLine(0), "");
        assertEquals(text1.getLine(1), "Line 1");
    }

    @Test
    public void insertLineBreakAtEnd(){
        text1.insertLineBreak(20);
        assertEquals(text1.getLineAmount(), 4);
        assertEquals(text1.getCharAmount(), 21);
        assertEquals(text1.getLine(3), "");
        assertEquals(text1.getLine(2), "Line 3");
    }

    @Test
    public void insertLineBreakIllegal(){
        assertThrows(IllegalArgumentException.class, () -> text1.insertLineBreak(-1));
        assertThrows(IllegalArgumentException.class, () -> text1.insertLineBreak(21));
    }

    @Test
    public void getSkeleton(){
        assertEquals(text1.getSkeleton(), new TextSkeleton(new ArrayList<>(Arrays.asList(7, 7, 6))));
        assertEquals(text2.getSkeleton(), new TextSkeleton(new ArrayList<>(List.of(7, 0))));
    }

    @Test
    public void testEquals(){
        assertEquals(text1, text3);
        assertNotEquals(text1, text2);
        assertNotEquals(text1, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(text1.hashCode(), text3.hashCode());
        assertNotEquals(text1.hashCode(), text2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "LineText[Text=Line 1\nLine 2\nLine 3]";
        assertEquals(text1.toString(), expectedString);
    }
}
