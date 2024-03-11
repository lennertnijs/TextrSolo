package FileBuffer;

import com.Textr.File.File;
import com.Textr.FileBuffer.*;
import com.Textr.Util.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileBufferTest {


//    @BeforeEach
//    public void initialise(){
//    }

//
//    @Test
//    public void testConstructorAndGetters(){
//        Point point = Point.create(1,1);
//        File file = File.create("dummyUrl");
//        FileBuffer buffer = FileBuffer.builder().file(file).text(Text.create("text")).cursor(point).state(BufferState.CLEAN).build();
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(buffer.getFile(), file),
//                () -> Assertions.assertEquals(buffer.getText(), Text.create("text")),
//                () -> Assertions.assertEquals(buffer.getCursor(), point),
//                () -> Assertions.assertEquals(buffer.getState(), BufferState.CLEAN)
//        );
//    }
//
//    @Test
//    public void testConstructorInvalid(){
//        Point point = Point.create(1,1);
//        File file = File.create("dummyUrl");
//        FileBuffer.Builder invalidActiveFileId = FileBuffer.builder().file(null).text(Text.create("text"))
//                .cursor(point).state(BufferState.CLEAN);
//        FileBuffer.Builder invalidBufferText = FileBuffer.builder().file(file).text(null)
//                .cursor(point).state(BufferState.CLEAN);
//        FileBuffer.Builder invalidInsertionIndex = FileBuffer.builder().file(file).text(Text.create("text"))
//                .cursor(null).state(BufferState.CLEAN);
//        FileBuffer.Builder invalidState = FileBuffer.builder().file(file).text(Text.create("text"))
//                .cursor(point).state(null);
//        Assertions.assertAll(
//                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidActiveFileId::build),
//                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidBufferText::build),
//                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidInsertionIndex::build),
//                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidState::build)
//        );
//    }
//
//    @Test
//    public void testEqualsAndHashCode(){
//        Point point = Point.create(1,1);
//        File file = File.create("dummyUrl");
//        FileBuffer buffer1 = FileBuffer.builder().file(file).text(Text.create("text")).cursor(point).state(BufferState.CLEAN).build();
//        FileBuffer buffer2 = FileBuffer.builder().file(file).text(Text.create("text2")).cursor(point).state(BufferState.CLEAN).build();
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(buffer1, buffer1),
//                () -> Assertions.assertNotEquals(buffer1, buffer2),
//                () -> Assertions.assertNotEquals(buffer1, new Object()),
//                () -> Assertions.assertEquals(buffer1.hashCode(), buffer1.hashCode()),
//                () -> Assertions.assertNotEquals(buffer1.hashCode(), buffer2.hashCode())
//        );
//    }
//
//    @Test
//    public void testToString(){
//        Point point = Point.create(1,1);
//        File file = File.create("dummyUrl");
//        FileBuffer buffer = FileBuffer.builder().file(file).text(Text.create("text")).cursor(point).state(BufferState.CLEAN).build();
//        String expected = "FileBuffer[fileId = File[id = 10, url = dummyUrl], text = Text[Lines: text], cursor = Point[x = 1, y = 1], state = CLEAN]";
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(buffer.toString(), expected)
//        );
//    }
}
