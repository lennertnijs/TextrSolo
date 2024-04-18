package com.textr.filebuffer;

import com.textr.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDeleteAction {

    private IText text;
    private ICursor cursor;
    private DeleteAction deleteBefore;
    private DeleteAction deleteAfter;

    @BeforeEach
    public void initialise(){
        text = LineText.createFromString("String testing");
        cursor = Cursor.createNew();
        cursor.move(Direction.RIGHT, text.getSkeleton());

        deleteBefore = new DeleteAction(6, 'g', text, Side.BEFORE);
        deleteAfter = new DeleteAction(6, ' ', text, Side.AFTER);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IllegalArgumentException.class, () -> new DeleteAction(-1, 's', text, Side.BEFORE));
        assertThrows(IllegalArgumentException.class, () -> new DeleteAction(text.getCharAmount() + 1, 's', text, Side.BEFORE));
        assertThrows(NullPointerException.class, () -> new DeleteAction(5, 's', null, Side.BEFORE));
        assertThrows(NullPointerException.class, () -> new DeleteAction(5, 's', text, null));
    }

    @Test
    public void testExecuteWithNullCursor(){
        assertThrows(NullPointerException.class, () -> deleteBefore.execute(null));
    }

    @Test
    public void testUndoWithNullCursor(){
        assertThrows(NullPointerException.class, () -> deleteBefore.undo(null));
    }

    @Test
    public void testExecuteBefore(){
        String expected = "Strin testing";
        deleteBefore.execute(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(6 - 1, cursor.getInsertIndex());
    }

    @Test
    public void testExecuteAfter(){
        String expected = "Stringtesting";
        deleteAfter.execute(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(6, cursor.getInsertIndex());
    }

    @Test
    public void testUndoBefore(){
        deleteBefore.execute(cursor);
        assertEquals("Strin testing", text.getContent());
        assertEquals(6 - 1, cursor.getInsertIndex());

        String expected = "String testing";
        deleteBefore.undo(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(6, cursor.getInsertIndex());
    }

    @Test
    public void testUndoAfter(){
        deleteAfter.execute(cursor);
        assertEquals("Stringtesting", text.getContent());
        assertEquals(6, cursor.getInsertIndex());

        String expected = "String testing";
        deleteAfter.undo(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(7, cursor.getInsertIndex());
    }

    @Test
    public void testExecuteBeforeAtIndex0(){
        assertThrows(IllegalArgumentException.class, () -> new DeleteAction(0, 'S', text, Side.BEFORE));
    }

    @Test
    public void testExecuteAfterLastIndex(){
        assertThrows(IllegalArgumentException.class, () -> new DeleteAction(text.getCharAmount() - 1, 'S', text, Side.AFTER));
    }
}
