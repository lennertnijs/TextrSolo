package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InsertActionTest {

    private IText text;
    private InsertAction insertAction;

    @BeforeEach
    public void initialise(){
        text = LineText.createFromString("String testing");

        insertAction = new InsertAction(6, 's', text);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IndexOutOfBoundsException.class, () -> new InsertAction(-1, 's', text));
        assertThrows(IndexOutOfBoundsException.class, () -> new InsertAction(text.getCharAmount() + 1, 's', text));
        assertThrows(NullPointerException.class, () -> new InsertAction(5, 's', null));
    }

    @Test
    public void testExecute(){
        String expected = "Strings testing";
        insertAction.execute();
        assertEquals(expected, text.getContent());
    }



    @Test
    public void testUndo(){
        String expected = "String testing";
        insertAction.execute();
        insertAction.undo();
        assertEquals(expected, text.getContent());
    }
}
