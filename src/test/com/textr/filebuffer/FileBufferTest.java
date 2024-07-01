package com.textr.filebuffer;

import com.textr.file.IFileReader;
import com.textr.file.IFileWriter;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileBufferTest {

    private final File file = Mockito.mock(File.class);
    private final IFileReader fileReader = new MockFileReader();
    private final IFileWriter fileWriter = new MockFileWriter();
    private FileBuffer fileBuffer;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(fileReader);
        FileBuffer.setFileWriter(fileWriter);
        fileBuffer = new FileBuffer(file);
    }

    @Test
    public void testConstructorWithNullUrl(){
        assertThrows(NullPointerException.class,
                () -> new FileBuffer(null));
    }

    @Test
    public void testGetFile(){
        assertEquals(file, fileBuffer.getFile());
    }

    @Test
    public void testGetText(){
        // 20 chars, 21 insert indices
        String text = "This is a mock text.";
        assertEquals(text, fileBuffer.getText().getContent());
    }

    @Test
    public void testGetInsertPoint(){
        assertEquals(new Point(7, 0), fileBuffer.getInsertPoint(7));
    }

    @Test
    public void testGetInsertPointWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> fileBuffer.getInsertPoint(-1));
    }

    @Test
    public void testGetState(){
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testSetState(){
        fileBuffer.setState(BufferState.DIRTY);
        assertEquals(BufferState.DIRTY, fileBuffer.getState());
    }

    @Test
    public void testSetStateWithNull(){
        assertThrows(NullPointerException.class,
                () -> fileBuffer.setState(null));
    }

    @Test
    public void testMove(){
        assertEquals(8, fileBuffer.move(7, Direction.RIGHT));
        assertEquals(20, fileBuffer.move(20, Direction.RIGHT));

        assertEquals(6, fileBuffer.move(7, Direction.LEFT));
        assertEquals(0, fileBuffer.move(0, Direction.LEFT));

        assertEquals(6, fileBuffer.move(6, Direction.UP));

        assertEquals(6, fileBuffer.move(6, Direction.DOWN));
    }

    @Test
    public void testMoveWithNullDirection(){
        assertThrows(NullPointerException.class,
                () -> fileBuffer.move(6, null));
    }

    @Test
    public void testMoveWithNegativeIndex(){
        assertThrows(IndexOutOfBoundsException.class,
                () -> fileBuffer.move(-1, Direction.RIGHT));
    }

    @Test
    public void testInsert(){
        fileBuffer.insert('c', 0);
        assertEquals("cThis is a mock text.", fileBuffer.getText().getContent());
    }

    @Test
    public void testDelete(){
        fileBuffer.delete(2);
        assertEquals("Ths is a mock text.", fileBuffer.getText().getContent());
    }

    @Test
    public void testWriteToDisk() throws IOException {
        fileBuffer.setState(BufferState.DIRTY);
        fileBuffer.writeToDisk();
        assertEquals(BufferState.CLEAN, fileBuffer.getState());
    }

    @Test
    public void testGetListenerCount(){
        assertEquals(0, fileBuffer.getListenerCount());
    }

    @Test
    public void testAddListener(){
        MockTextListener mockTextListener = new MockTextListener();
        fileBuffer.addTextListener(mockTextListener);
        assertEquals(1, fileBuffer.getListenerCount());
        // no doubles
        fileBuffer.addTextListener(mockTextListener);
        assertEquals(1, fileBuffer.getListenerCount());
    }

    @Test
    public void testAddListenerWithNull(){
        assertThrows(NullPointerException.class,
                () -> fileBuffer.addTextListener(null));
    }

    @Test
    public void testRemoveListener(){
        MockTextListener mockTextListener = new MockTextListener();
        fileBuffer.addTextListener(mockTextListener);
        assertEquals(1, fileBuffer.getListenerCount());
        fileBuffer.removeTextListener(mockTextListener);
        assertEquals(0, fileBuffer.getListenerCount());
    }

    @Test
    public void testRemoveListenerWithNull(){
        assertThrows(NullPointerException.class,
                () -> fileBuffer.removeTextListener(null));
    }

    @Test
    public void testInsertWithListener(){
        MockTextListener mockTextListener = new MockTextListener();
        fileBuffer.addTextListener(mockTextListener);
        fileBuffer.insert('c', 17);
        assertEquals(new TextUpdate(new Point(17, 0), OperationType.INSERT_CHARACTER),
                mockTextListener.getTextUpdate());

        fileBuffer.insert('\n', 17);
        assertEquals(new TextUpdate(new Point(17, 0), OperationType.INSERT_NEWLINE),
                mockTextListener.getTextUpdate());
    }

    @Test
    public void testDeleteWithListener(){
        MockTextListener mockTextListener = new MockTextListener();
        fileBuffer.addTextListener(mockTextListener);
        fileBuffer.delete(17);
        assertEquals(new TextUpdate(new Point(17, 0), OperationType.DELETE_CHARACTER),
                mockTextListener.getTextUpdate());

        fileBuffer.insert('\n', 17);
        fileBuffer.delete(17);
        assertEquals(new TextUpdate(new Point(17, 0), OperationType.DELETE_NEWLINE),
                mockTextListener.getTextUpdate());
    }

    @Test
    public void testSetFileReaderWithNull(){
        assertThrows(NullPointerException.class,
                () -> FileBuffer.setFileReader(null));
    }

    @Test
    public void testSetFileWriterWithNull(){
        assertThrows(NullPointerException.class,
                () -> FileBuffer.setFileWriter(null));
    }

    @Test
    public void testToString(){
        String expected = "FileBuffer[" +
                "file=" + file.toString() + ", " +
                "text=LineText[content={This is a mock text.}], " +
                "state=CLEAN" +
                "]";
        assertEquals(expected, fileBuffer.toString());
    }
}
