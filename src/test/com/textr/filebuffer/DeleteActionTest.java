package com.textr.filebuffer;

import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteActionTest {

    private final File file = Mockito.mock(File.class);
    private final IFileReader fileReader = new MockFileReader();
    private final IFileWriter fileWriter = new MockFileWriter();
    private FileBuffer fileBuffer;
    private DeleteAction deleteAction;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(fileReader);
        FileBuffer.setFileWriter(fileWriter);
        fileBuffer = new FileBuffer(file);
        deleteAction = new DeleteAction(15, fileBuffer);
    }

    @Test
    public void testConstructorWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> new DeleteAction( -1, fileBuffer));
    }

    @Test
    public void testConstructorWithIndexTooBig(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> new DeleteAction(20, fileBuffer));
    }

    @Test
    public void testConstructorWithNullFileBuffer(){
        assertThrows(NullPointerException.class,
                () -> new DeleteAction(15, null));
    }

    @Test
    public void testExecute(){
        deleteAction.execute();
        assertEquals("This is a mock ext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndo(){
        deleteAction.execute();
        deleteAction.undo();
        assertEquals("This is a mock text.", fileBuffer.getText().getContent());
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testExecuteMultiple(){
        deleteAction.execute();
        assertEquals("This is a mock ext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());

        DeleteAction deleteAction1 = new DeleteAction(7, fileBuffer);
        deleteAction1.execute();
        assertEquals("This isa mock ext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndoMultiple(){
        deleteAction.execute();
        DeleteAction deleteAction1 = new DeleteAction(7, fileBuffer);
        deleteAction1.execute();

        deleteAction1.undo();
        assertEquals("This is a mock ext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());

        deleteAction.undo();
        assertEquals("This is a mock text.", fileBuffer.getText().getContent());
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testExecuteReturnIndex(){
        assertEquals(15 , deleteAction.execute());

        DeleteAction deleteAction1 = new DeleteAction(7, fileBuffer);
        assertEquals(7 , deleteAction1.execute());
    }

    @Test
    public void testUndoReturnIndex(){
        deleteAction.execute();
        DeleteAction deleteAction1 = new DeleteAction(7, fileBuffer);
        deleteAction1.execute();

        assertEquals(8, deleteAction1.undo());
        assertEquals(16, deleteAction.undo());
    }

    @Test
    public void testDifferentUndoOrder(){
        deleteAction.execute();
        DeleteAction deleteAction1 = new DeleteAction(7, fileBuffer);
        deleteAction1.execute();

        assertEquals(16, deleteAction.undo());
        assertEquals(8, deleteAction1.undo());
    }
}
