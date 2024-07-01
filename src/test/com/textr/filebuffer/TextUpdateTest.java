package com.textr.filebuffer;

import com.textr.filebufferV2.OperationType;
import com.textr.filebufferV2.TextUpdate;
import com.textr.util.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextUpdateTest {

    private final Point insertPoint = new Point(5, 15);
    private final OperationType operationType = OperationType.INSERT_CHARACTER;
    private final TextUpdate textUpdate = new TextUpdate(insertPoint, operationType);

    @Test
    public void testConstructorWithNullInsertPoint(){
        assertThrows(NullPointerException.class,
                ()-> new TextUpdate(null, operationType));
    }

    @Test
    public void testConstructorWithNullOperationType(){
        assertThrows(NullPointerException.class,
                ()-> new TextUpdate(insertPoint, null));
    }
    @Test
    public void getInsertPoint(){
        assertEquals(insertPoint, textUpdate.insertPoint());
    }

    @Test
    public void testGetOperationType(){
        assertEquals(operationType, textUpdate.operationType());
    }

    @Test
    public void testToString(){
        String expected = "TextUpdate[" +
                "insertPoint=Point[x = 5, y = 15], " +
                "operationType=INSERT_CHARACTER" +
                "]";
        assertEquals(expected, textUpdate.toString());
    }
}
