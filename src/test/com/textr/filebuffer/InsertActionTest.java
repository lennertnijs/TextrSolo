package com.textr.filebuffer;

import com.textr.bufferEditor.InsertAction;
import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
import com.textr.filebufferV2.BufferState;
import com.textr.filebufferV2.FileBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class InsertActionTest {

    private final File file = Mockito.mock(File.class);
    private final IFileReader fileReader = new MockFileReader();
    private final IFileWriter fileWriter = new MockFileWriter();
    private FileBuffer fileBuffer;
    private InsertAction insertAction;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(fileReader);
        FileBuffer.setFileWriter(fileWriter);
        fileBuffer = new FileBuffer(file);
        insertAction = new InsertAction('c', 15, fileBuffer);
    }

    @Test
    public void testConstructorWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> new InsertAction('c', -1, fileBuffer));
    }

    @Test
    public void testConstructorWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> new InsertAction('c', 21, fileBuffer));
    }

    @Test
    public void testConstructorWithNullFileBuffer(){
        assertThrows(NullPointerException.class,
                () -> new InsertAction('c', 15, null));
    }

    @Test
    public void testExecute(){
        insertAction.execute();
        assertEquals("This is a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndo(){
        insertAction.execute();
        insertAction.undo();
        assertEquals("This is a mock text.", fileBuffer.getText().getContent());
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testExecuteMultiple(){
        insertAction.execute();
        assertEquals("This is a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());

        InsertAction insertAction1 = new InsertAction('y', 7, fileBuffer);
        insertAction1.execute();
        assertEquals("This isy a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndoMultiple(){
        insertAction.execute();
        InsertAction insertAction1 = new InsertAction('y', 7, fileBuffer);
        insertAction1.execute();

        insertAction1.undo();
        assertEquals("This is a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());

        insertAction.undo();
        assertEquals("This is a mock text.", fileBuffer.getText().getContent());
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testExecuteReturnIndex(){
        assertEquals(15 + 1, insertAction.execute());

        InsertAction insertAction1 = new InsertAction('y', 7, fileBuffer);
        assertEquals(7 + 1, insertAction1.execute());
    }

    @Test
    public void testUndoReturnIndex(){
        insertAction.execute();
        InsertAction insertAction1 = new InsertAction('y', 7, fileBuffer);
        insertAction1.execute();

        assertEquals(7, insertAction1.undo());
        assertEquals(15, insertAction.undo());
    }

    @Test
    public void testDifferentUndoOrder(){
        insertAction.execute();
        InsertAction insertAction1 = new InsertAction('y', 7, fileBuffer);
        insertAction1.execute();

        assertEquals(15, insertAction.undo());
        assertEquals(7, insertAction1.undo());
    }
}
