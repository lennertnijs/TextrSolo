package FileBufferRepo;

import com.Textr.FileBuffer.*;
import com.Textr.FileBufferRepo.AllFileBuffersRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class AllFileBuffersRepoTest {

    private FileBuffer buffer1;
    private FileBuffer buffer2;
    private AllFileBuffersRepo repo;
    @BeforeEach
    public void initialise(){
        FileBufferIdGenerator.resetGenerator();
        InsertionPoint insertionPoint = InsertionPoint.create(5,5);
        buffer1 = FileBuffer.builder().fileId(1).bufferText(Text.create("text"))
                .insertionPosition(insertionPoint).state(BufferState.CLEAN).build();
        buffer2 = FileBuffer.builder().fileId(2).bufferText(Text.create("text"))
                .insertionPosition(insertionPoint).state(BufferState.CLEAN).build();
        repo = new AllFileBuffersRepo();
    }

    @Test
    public void testAddRemoveAndGet(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(buffer1),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.get(0), buffer1),
                () -> Assertions.assertEquals(repo.getAll().get(0),buffer1),

                () -> repo.add(buffer2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.get(0), buffer1),
                () -> Assertions.assertEquals(repo.get(1), buffer2),
                () -> Assertions.assertEquals(repo.getAll().get(0),buffer1),
                () -> Assertions.assertEquals(repo.getAll().get(1),buffer2),

                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.get(1), buffer2),
                () -> Assertions.assertEquals(repo.getAll().get(0),buffer2),

                () -> repo.remove(1),
                () -> Assertions.assertEquals(repo.getSize(), 0)
        );
    }

    @Test
    public void testIllegalAddRemoveAndGet(){
        Assertions.assertAll(
                () -> repo.remove(-1),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null)),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> repo.get(1))
        );
    }

    @Test
    public void testNextBuffer(){
        repo.add(buffer1);
        repo.add(buffer2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getNext(0), buffer2),
                () -> Assertions.assertEquals(repo.getNext(1), buffer1),
                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getNext(1), buffer2),
                () -> Assertions.assertThrows(IllegalStateException.class, () -> repo.getNext(2))

        );
    }


    @Test
    public void testPreviousBuffer(){
        repo.add(buffer1);
        repo.add(buffer2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getPrevious(0), buffer2),
                () -> Assertions.assertEquals(repo.getPrevious(1), buffer1),
                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getPrevious(1), buffer2),
                () -> Assertions.assertThrows(IllegalStateException.class, () -> repo.getPrevious(2))

        );
    }
}
