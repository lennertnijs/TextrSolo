package FileBufferRepo;

import com.Textr.FileBuffer.*;
import com.Textr.FileBufferRepo.FileBufferRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FileBufferRepoTest {


    private FileBuffer buffer1;
    private FileBuffer buffer2;
    private FileBufferRepo repo;
    @BeforeEach
    public void initialise(){
        FileBufferIdGenerator.resetGenerator();
        Point point = Point.create(5,5);
        buffer1 = FileBuffer.builder().fileId(1).bufferText(Text.create("text"))
                .insertionPosition(point).state(BufferState.CLEAN).build();
        buffer2 = FileBuffer.builder().fileId(2).bufferText(Text.create("text"))
                .insertionPosition(point).state(BufferState.CLEAN).build();
        repo = new FileBufferRepo();
    }

    @Test
    public void testMethodsValid(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),
                () -> Assertions.assertEquals(repo.getAllBuffers(), new ArrayList<>()),

                () -> repo.addBuffer(buffer1),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getBuffer(0), buffer1),
                () -> Assertions.assertEquals(repo.getAllBuffers().get(0), buffer1),

                () -> Assertions.assertFalse(repo.hasActiveBuffer()),
                () -> repo.setActiveBuffer(buffer1),
                () -> Assertions.assertTrue(repo.hasActiveBuffer()),
                () -> Assertions.assertEquals(repo.getActiveBufferId(), 0),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer1),

                () -> repo.addBuffer(buffer2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.getBuffer(0), buffer1),
                () -> Assertions.assertEquals(repo.getBuffer(1), buffer2),
                () -> Assertions.assertEquals(repo.getAllBuffers().get(0), buffer1),
                () -> Assertions.assertEquals(repo.getAllBuffers().get(1), buffer2),

                () -> repo.setActiveBuffer(buffer2),
                () -> Assertions.assertEquals(repo.getActiveBufferId(), 1),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer2),

                () -> repo.removeBuffer(0),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getBuffer(1), buffer2),
                () -> Assertions.assertEquals(repo.getAllBuffers().get(0), buffer2),

                () -> repo.removeActiveBuffer(),
                () -> repo.removeBuffer(1),

                () -> Assertions.assertEquals(repo.getSize(), 0),
                () -> Assertions.assertEquals(repo.getAllBuffers(), new ArrayList<>())
        );
    }

    @Test
    public void testNextAndPrev(){
        Assertions.assertAll(
                () -> repo.addBuffer(buffer1),
                () -> repo.setActiveBuffer(buffer1),

                () -> repo.setActiveToNext(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer1),
                () -> repo.setActiveToPrevious(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer1),

                () -> repo.addBuffer(buffer2),
                () -> repo.setActiveToNext(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer2),
                () -> repo.setActiveToNext(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer1),
                () -> repo.setActiveToPrevious(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer2),
                () -> repo.setActiveToPrevious(),
                () -> Assertions.assertEquals(repo.getActiveBuffer(), buffer1)
        );
    }

    @Test
    public void testInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalStateException.class, () -> repo.setActiveBuffer(buffer1)),
                () -> repo.addBuffer(buffer1),
                () -> repo.setActiveBuffer(buffer1),
                () -> Assertions.assertThrows(IllegalStateException.class, () -> repo.removeBuffer(buffer1.getId()))
        );
    }
}
