package com.textr.filebuffer;

import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BufferEditorTest {

    private final File file = Mockito.mock(File.class);
    private final IFileReader fileReader = new MockFileReader();
    private final IFileWriter fileWriter = new MockFileWriter();
    private FileBuffer fileBuffer;
    private BufferEditor bufferEditor;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(fileReader);
        FileBuffer.setFileWriter(fileWriter);
        fileBuffer = new FileBuffer(file);
        bufferEditor = new BufferEditor(fileBuffer);
    }

    @Test
    public void testConstructionWithNullFileBuffer(){
        assertThrows(NullPointerException.class,
                () -> new BufferEditor(null));
    }

    @Test
    public void testGetFileBuffer(){
        assertEquals(fileBuffer, bufferEditor.getFileBuffer());
    }

    @Test
    public void testGetInsertPoint(){
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testMoveCursor(){
        bufferEditor.moveCursor(Direction.RIGHT);
        assertEquals(new Point(1, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testMoveCursorWithNull(){
        assertThrows(NullPointerException.class,
                () -> bufferEditor.moveCursor(null));
    }

    @Test
    public void testInsert(){
        bufferEditor.insert('c');
        assertEquals("cThis is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(1, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testDeleteBefore(){
        bufferEditor.moveCursor(Direction.RIGHT);
        bufferEditor.deleteBefore();
        assertEquals("his is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testDeleteBeforeAtStart(){
        bufferEditor.deleteBefore();
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testDeleteAfter(){
        bufferEditor.deleteAfter();
        assertEquals("his is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }
    @Test
    public void testDeleteAfterAtEnd(){
        for(int i = 0; i < 20; i++){
            bufferEditor.moveCursor(Direction.RIGHT);
        }
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(20, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testUndo(){
        bufferEditor.insert('c');
        assertEquals("cThis is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(1, 0), bufferEditor.getInsertPoint());
        bufferEditor.undo();
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testUndoNothingToUndo(){
        bufferEditor.undo();
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testRedo(){
        bufferEditor.insert('c');
        assertEquals("cThis is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(1, 0), bufferEditor.getInsertPoint());
        bufferEditor.undo();
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
        bufferEditor.redo();
        assertEquals("cThis is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(1, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testRedoNothingToRedo(){
        bufferEditor.redo();
        assertEquals("This is a mock text.", bufferEditor.getFileBuffer().getText().getContent());
        assertEquals(new Point(0, 0), bufferEditor.getInsertPoint());
    }

    @Test
    public void testCopy(){
        BufferEditor copy = bufferEditor.copy();
        assertEquals(bufferEditor.getFileBuffer(), copy.getFileBuffer());

        copy.insert('c');
        assertNotEquals(bufferEditor.getFileBuffer(), copy.getFileBuffer());
    }
}
