package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDeleteAction {

    private IText text;
    private DeleteAction deleteAction;

    @BeforeEach
    public void initialise(){
        text = LineText.createFromString("String testing");

        deleteAction = new DeleteAction(6, ' ', text);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IndexOutOfBoundsException.class, () -> new DeleteAction(-1, 's', text));
        assertThrows(IndexOutOfBoundsException.class, () -> new DeleteAction(text.getCharAmount() + 1, 's', text));
        assertThrows(NullPointerException.class, () -> new DeleteAction(5, 's', null));
    }


    @Test
    public void testExecute(){
        String expected = "Stringtesting";
        deleteAction.execute();
        assertEquals(expected, text.getContent());
    }

    @Test
    public void testUndo(){
        deleteAction.execute();
        assertEquals("Stringtesting", text.getContent());

        String expected = "String testing";
        deleteAction.undo();
        assertEquals(expected, text.getContent());
    }
}
