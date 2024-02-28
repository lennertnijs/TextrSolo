package FileBuffer;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBufferIdGenerator;
import com.Textr.FileBuffer.InsertionPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileBufferTest {


    @BeforeEach
    public void initialise(){
        FileBufferIdGenerator.resetGenerator();
    }


    @Test
    public void testConstructorAndGetters(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer = FileBuffer.builder().fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer.getId(), 0),
                () -> Assertions.assertEquals(buffer.getFileId(), 0),
                () -> Assertions.assertEquals(String.join("", buffer.getBufferText()), "text"),
                () -> Assertions.assertEquals(buffer.getInsertionPosition(), point),
                () -> Assertions.assertEquals(buffer.getState(), BufferState.CLEAN)
        );
    }

    @Test
    public void testConstructorInvalid(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer.Builder invalidActiveFileId = FileBuffer.builder().fileId(-1).bufferText("text".split(""))
                .insertionPosition(point).state(BufferState.CLEAN);
        FileBuffer.Builder invalidBufferText = FileBuffer.builder().fileId(0).bufferText(null)
                .insertionPosition(point).state(BufferState.CLEAN);
        FileBuffer.Builder invalidInsertionIndex = FileBuffer.builder().fileId(0).bufferText("text".split(""))
                .insertionPosition(null).state(BufferState.CLEAN);
        FileBuffer.Builder invalidState = FileBuffer.builder().fileId(0).bufferText("text".split(""))
                .insertionPosition(point).state(null);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidActiveFileId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidBufferText::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidInsertionIndex::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidState::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer1 = FileBuffer.builder().fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        FileBuffer buffer2 = FileBuffer.builder().fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer1, buffer1),
                () -> Assertions.assertNotEquals(buffer1, buffer2),
                () -> Assertions.assertNotEquals(buffer1, new Object()),
                () -> Assertions.assertEquals(buffer1.hashCode(), buffer1.hashCode()),
                () -> Assertions.assertNotEquals(buffer1.hashCode(), buffer2.hashCode())
        );
    }

    @Test
    public void testToString(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer = FileBuffer.builder().fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        String expected = "FileBuffer[id = 0, activeFileId = 0, bufferText = text, insertionPosition = InsertionPoint[x = 1, y = 1], state = CLEAN]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer.toString(), expected)
        );
    }
}
