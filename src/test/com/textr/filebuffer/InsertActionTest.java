package com.textr.filebuffer;

import com.textr.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InsertActionTest {

    private IText text;
    private ICursor cursor;
    private InsertAction insertAction;

    @BeforeEach
    public void initialise(){
        text = LineText.createFromString("String testing");
        cursor = Cursor.createNew();
        cursor.move(Direction.RIGHT, text.getSkeleton());

        insertAction = new InsertAction(6, 's', text);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IllegalArgumentException.class, () -> new InsertAction(-1, 's', text));
        assertThrows(IllegalArgumentException.class, () -> new InsertAction(text.getCharAmount() + 1, 's', text));
        assertThrows(NullPointerException.class, () -> new InsertAction(5, 's', null));
    }

    @Test
    public void testExecuteWithNullCursor(){
        assertThrows(NullPointerException.class, () -> insertAction.execute(null));
    }

    @Test
    public void testExecute(){
        String expected = "Strings testing";
        insertAction.execute(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(7, cursor.getInsertIndex());
    }

    @Test
    public void testUndoWithNullCursor(){
        assertThrows(NullPointerException.class, () -> insertAction.undo(null));
    }


    @Test
    public void testUndo(){
        String expected = "String testing";
        insertAction.execute(cursor);
        insertAction.undo(cursor);
        assertEquals(expected, text.getContent());
        assertEquals(6, cursor.getInsertIndex());
    }
}
