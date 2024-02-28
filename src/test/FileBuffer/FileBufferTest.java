package FileBuffer;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.BufferState;
import com.Textr.View.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileBufferTest {

    @Test
    public void testConstructorAndGetters(){
        Position position = Position.create(1,1);
        FileBuffer buffer = FileBuffer.builder().id(0).fileId(0).bufferText("text").insertionPosition(position).state(BufferState.CLEAN).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer.getId(), 0),
                () -> Assertions.assertEquals(buffer.getFileId(), 0),
                () -> Assertions.assertEquals(buffer.getBufferText(), "text"),
                () -> Assertions.assertEquals(buffer.getInsertionPosition(), position),
                () -> Assertions.assertEquals(buffer.getState(), BufferState.CLEAN)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Position position = Position.create(1,1);
        FileBuffer.Builder invalidId = FileBuffer.builder().id(-1).fileId(0).bufferText("text")
                .insertionPosition(position).state(BufferState.CLEAN);
        FileBuffer.Builder invalidActiveFileId = FileBuffer.builder().id(0).fileId(-1).bufferText("text")
                .insertionPosition(position).state(BufferState.CLEAN);
        FileBuffer.Builder invalidBufferText = FileBuffer.builder().id(0).fileId(0).bufferText(null)
                .insertionPosition(position).state(BufferState.CLEAN);
        FileBuffer.Builder invalidInsertionIndex = FileBuffer.builder().id(0).fileId(0).bufferText("text")
                .insertionPosition(null).state(BufferState.CLEAN);
        FileBuffer.Builder invalidState = FileBuffer.builder().id(0).fileId(0).bufferText("text")
                .insertionPosition(position).state(null);
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
        Position position = Position.create(1,1);
        FileBuffer buffer1 = FileBuffer.builder().id(0).fileId(0).bufferText("text").insertionPosition(position).state(BufferState.CLEAN).build();
        FileBuffer buffer2 = FileBuffer.builder().id(1).fileId(0).bufferText("text").insertionPosition(position).state(BufferState.CLEAN).build();
        FileBuffer buffer3 = FileBuffer.builder().id(0).fileId(0).bufferText("text").insertionPosition(position).state(BufferState.CLEAN).build();
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
        Position position = Position.create(1,1);
        FileBuffer buffer = FileBuffer.builder().id(0).fileId(0).bufferText("text").insertionPosition(position).state(BufferState.CLEAN).build();
        String expected = "FileBuffer[id = 0, activeFileId = 0, bufferText = text, insertionPosition = Position[x = 1, y = 1], state = CLEAN]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(buffer.toString(), expected)
        );
    }
}
