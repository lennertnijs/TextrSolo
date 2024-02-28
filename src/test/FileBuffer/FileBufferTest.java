package FileBuffer;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.InsertionPoint;
import com.Textr.View.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileBufferTest {

    @Test
    public void testConstructorAndGetters(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer = FileBuffer.builder().id(0).fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
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
        FileBuffer.Builder invalidId = FileBuffer.builder().id(-1).fileId(0).bufferText("text".split(""))
                .insertionPosition(point).state(BufferState.CLEAN);
        FileBuffer.Builder invalidActiveFileId = FileBuffer.builder().id(0).fileId(-1).bufferText("text".split(""))
                .insertionPosition(point).state(BufferState.CLEAN);
        FileBuffer.Builder invalidBufferText = FileBuffer.builder().id(0).fileId(0).bufferText(null)
                .insertionPosition(point).state(BufferState.CLEAN);
        FileBuffer.Builder invalidInsertionIndex = FileBuffer.builder().id(0).fileId(0).bufferText("text".split(""))
                .insertionPosition(null).state(BufferState.CLEAN);
        FileBuffer.Builder invalidState = FileBuffer.builder().id(0).fileId(0).bufferText("text".split(""))
                .insertionPosition(point).state(null);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidActiveFileId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidBufferText::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidInsertionIndex::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidState::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer1 = FileBuffer.builder().id(0).fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        FileBuffer buffer2 = FileBuffer.builder().id(1).fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        FileBuffer buffer3 = FileBuffer.builder().id(0).fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer1, buffer1),
                () -> Assertions.assertEquals(buffer1, buffer3),
                () -> Assertions.assertNotEquals(buffer1, buffer2),
                () -> Assertions.assertNotEquals(buffer1, new Object()),
                () -> Assertions.assertEquals(buffer1.hashCode(), buffer1.hashCode()),
                () -> Assertions.assertEquals(buffer1.hashCode(), buffer3.hashCode()),
                () -> Assertions.assertNotEquals(buffer1.hashCode(), buffer2.hashCode())
        );
    }

    @Test
    public void testToString(){
        InsertionPoint point = InsertionPoint.create(1,1);
        FileBuffer buffer = FileBuffer.builder().id(0).fileId(0).bufferText("text".split("")).insertionPosition(point).state(BufferState.CLEAN).build();
        String expected = "FileBuffer[id = 0, activeFileId = 0, bufferText = text, insertionPosition = InsertionPoint[x = 1, y = 1], state = CLEAN]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer.toString(), expected)
        );
    }
}
