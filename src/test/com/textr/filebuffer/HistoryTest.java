package com.textr.filebuffer;

import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
import com.textr.filebufferV2.BufferState;
import com.textr.filebufferV2.FileBuffer;
import com.textr.filebufferV2.InsertAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class HistoryTest {

    private final File file = Mockito.mock(File.class);
    private final IFileReader fileReader = new MockFileReader();
    private final IFileWriter fileWriter = new MockFileWriter();
    private FileBuffer fileBuffer;
    private InsertAction action1;
    private InsertAction action2;
    private final String text = "This is a mock text.";
    private History history;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(fileReader);
        FileBuffer.setFileWriter(fileWriter);
        fileBuffer = new FileBuffer(file);
        action1 = new InsertAction('c', 15, fileBuffer);
        history = new History();
    }

    @Test
    public void testExecuteAndAdd(){
        history.executeAndAddAction(action1);
        assertEquals("This is a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());

        action2 = new InsertAction('d', 5, fileBuffer);
        history.executeAndAddAction(action2);
        assertEquals("This dis a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndo(){
        history.executeAndAddAction(action1);
        action2 = new InsertAction('d', 5, fileBuffer);
        history.executeAndAddAction(action2);

        history.undo(3);
        assertEquals("This is a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testUndoReturnValues(){
        assertEquals(15 + 1, history.executeAndAddAction(action1));
        action2 = new InsertAction('d', 5, fileBuffer);
        assertEquals(5 + 1, history.executeAndAddAction(action2));

        assertEquals(5, history.undo(3));
    }

    @Test
    public void testUndoWithNothing(){
        assertEquals(3, history.undo(3));
    }

    @Test
    public void testRedo(){
        history.executeAndAddAction(action1);
        action2 = new InsertAction('d', 5, fileBuffer);
        history.executeAndAddAction(action2);

        history.undo(3);

        history.redo(3);
        assertEquals("This dis a mock ctext.", fileBuffer.getText().getContent());
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testRedoReturnValues(){
        assertEquals(15 + 1, history.executeAndAddAction(action1));
        action2 = new InsertAction('d', 5, fileBuffer);
        assertEquals(5 + 1, history.executeAndAddAction(action2));

        assertEquals(5, history.undo(3));

        assertEquals(6, history.redo(3));
    }

    @Test
    public void testRedoWithNothing(){
        assertEquals(3, history.redo(3));
    }

    @Test
    public void testExecuteAndStoreClearsRedoAbles(){
        history.executeAndAddAction(action1);
        action2 = new InsertAction('d', 5, fileBuffer);

        history.undo(3);
        history.executeAndAddAction(action2);
        assertEquals(3, history.redo(3));
    }
}

