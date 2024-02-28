package FileBuffer;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferRepo;
import com.Textr.FileBuffer.BufferState;
import com.Textr.View.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileBufferRepoTest {

    @Test
    public void testFileBufferRepo(){
        FileBufferRepo repo = new FileBufferRepo();
        Point point = Point.create(1,1);
        FileBuffer buffer1 = FileBuffer.builder().id(0).fileId(1).bufferText("text").insertionPosition(point).state(BufferState.CLEAN).build();
        FileBuffer buffer2 = FileBuffer.builder().id(1).fileId(1).bufferText("text").insertionPosition(point).state(BufferState.CLEAN).build();
        FileBuffer buffer3 = FileBuffer.builder().id(2).fileId(1).bufferText("text").insertionPosition(point).state(BufferState.CLEAN).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(buffer1),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getAll().size(), 1),
                () -> Assertions.assertEquals(repo.get(0).get(), buffer1),

                () -> repo.add(buffer2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.getAll().size(), 2),
                () -> Assertions.assertEquals(repo.get(0).get(), buffer1),
                () -> Assertions.assertEquals(repo.get(1).get(), buffer2),

                () -> repo.add(buffer3),
                () -> Assertions.assertEquals(repo.getSize(), 3),
                () -> Assertions.assertEquals(repo.getAll().size(), 3),
                () -> Assertions.assertEquals(repo.get(0).get(), buffer1),
                () -> Assertions.assertEquals(repo.get(1).get(), buffer2),
                () -> Assertions.assertEquals(repo.get(2).get(), buffer3),

                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.getAll().size(), 2),
                () -> Assertions.assertEquals(repo.get(1).get(), buffer3),

                () -> repo.remove(2),
                () -> repo.remove(1),
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(buffer1),
                () -> repo.add(buffer2),
                () -> repo.add(buffer3),
                () -> Assertions.assertEquals(repo.getSize(), 3),
                () -> repo.removeAll(),
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null))
        );
    }
}
